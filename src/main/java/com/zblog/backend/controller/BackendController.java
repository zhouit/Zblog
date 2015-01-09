package com.zblog.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.backend.form.LoginForm;
import com.zblog.backend.form.validator.LoginFormValidator;
import com.zblog.common.dal.entity.User;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.CookieUtil;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.Constants;
import com.zblog.service.UserService;

@Controller
@RequestMapping("/backend")
public class BackendController{
  @Autowired
  private UserService userService;

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Model model){
    model.addAttribute("osname", System.getProperty("os.name"));
    model.addAttribute("javaVersion", System.getProperty("java.version"));
    model.addAttribute("memory", Runtime.getRuntime().totalMemory() / 1024 / 1024);
    return "backend/index";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(String msg, Model model){
    if("logout".equals(msg))
      model.addAttribute("msg", "您已登出。");
    return "backend/login";
  }

  @RequestMapping(value = "/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response){
    CookieUtil cookieUtil = new CookieUtil(request, response);
    cookieUtil.removeCokie(Constants.COOKIE_CONTEXT_ID);
    cookieUtil.removeCokie(Constants.COOKIE_CSRF_TOKEN);
    return "redirect:/backend/login?msg=logout";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String dashboard(LoginForm form, HttpServletRequest request, HttpServletResponse response){
    MapContainer result = LoginFormValidator.validateLogin(form);
    if(!result.isEmpty()){
      request.setAttribute("msg", result.get("msg"));
      return "backend/login";
    }

    User user = userService.login(form.getUsername(), form.getPassword());
    if(user == null){
      request.setAttribute("msg", "用户名密码错误");
      return "backend/login";
    }

    CookieUtil cookieUtil = new CookieUtil(request, response);
    cookieUtil.setCookie(Constants.COOKIE_USER_NAME, form.getUsername(), false, 7 * 24 * 3600);
    if(form.isRemeber()){
      cookieUtil.setCookie(Constants.COOKIE_CONTEXT_ID, user.getId(), true, 7 * 24 * 3600);
    }else{
      cookieUtil.setCookie(Constants.COOKIE_CONTEXT_ID, user.getId(), true);
    }

    return "redirect:" + StringUtils.emptyDefault(form.getRedirectURL(), "/backend/index");
  }

}

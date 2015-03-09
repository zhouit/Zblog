package com.zblog.web.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.biz.PostManager;
import com.zblog.core.dal.entity.User;
import com.zblog.core.filter.CookieRemberManager;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.CookieUtil;
import com.zblog.core.util.StringUtils;
import com.zblog.core.util.constants.Constants;
import com.zblog.core.util.web.WebContextFactory;
import com.zblog.service.CommentService;
import com.zblog.service.UserService;
import com.zblog.service.shiro.StatelessToken;
import com.zblog.web.backend.form.LoginForm;
import com.zblog.web.backend.form.validator.LoginFormValidator;

@Controller
@RequestMapping("/backend")
public class BackendController{
  @Autowired
  private UserService userService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private CommentService commentService;

  @RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Model model){
    model.addAttribute("osname", System.getProperty("os.name"));
    model.addAttribute("javaVersion", System.getProperty("java.version"));
    model.addAttribute("memory", Runtime.getRuntime().totalMemory() / 1024 / 1024);

    model.addAttribute("posts", postManager.listRecent(10));
    model.addAttribute("comments", commentService.listRecent());
    return "backend/index";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(String msg, Model model){
    if(WebContextFactory.get().isLogon())
      return "redirect:/backend/index";

    if("logout".equals(msg))
      model.addAttribute("msg", "您已登出。");
    return "backend/login";
  }

  @RequestMapping(value = "/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response){
    CookieRemberManager.logout(request, response);
    SecurityUtils.getSubject().logout();
    CookieUtil cookieUtil = new CookieUtil(request, response);
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

    SecurityUtils.getSubject().login(new StatelessToken(user.getId(), user.getPassword()));
    CookieUtil cookieUtil = new CookieUtil(request, response);
    cookieUtil.setCookie(Constants.COOKIE_USER_NAME, form.getUsername(), false, 7 * 24 * 3600);
    CookieRemberManager.loginSuccess(request, response, user.getId(), user.getPassword(), form.isRemeber());

    return "redirect:" + StringUtils.emptyDefault(form.getRedirectURL(), "/backend/index");
  }

}

package com.zblog.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.common.dal.entity.User;
import com.zblog.common.util.CookieUtil;
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
  public String login(){
    return "backend/login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String dashboard(String username, String pass, String code, HttpServletRequest request,
      HttpServletResponse response){
    User user = userService.login(username, pass);
    CookieUtil cookieUtil = new CookieUtil(request, response);
    cookieUtil.setCookie(Constants.COOKIE_CONTEXT_ID, user.getId() + ":" + user.getId());

    return "redirect:/backend/index";
  }

}

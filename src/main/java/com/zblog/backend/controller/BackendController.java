package com.zblog.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/backend")
public class BackendController{

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(){
    return "backend/index";
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(){
    return "backend/login";
  }
  
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String dashboard(){
    return "redirect:/backend/index";
  }



}

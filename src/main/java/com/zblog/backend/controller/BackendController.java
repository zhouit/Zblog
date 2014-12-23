package com.zblog.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/backend")
public class BackendController{

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Model model){
    model.addAttribute("osname", System.getProperty("os.name"));
    model.addAttribute("javaVersion", System.getProperty("java.version"));
    model.addAttribute("memory", Runtime.getRuntime().totalMemory()/1024/1024);
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

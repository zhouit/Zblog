package com.zblog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController{
  
  @RequestMapping("/about")
  public String about(){
    return "about";
  }

}

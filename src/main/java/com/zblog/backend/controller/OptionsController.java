package com.zblog.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.service.CategoryService;

@Controller
@RequestMapping("/backend/options")
public class OptionsController{
  @Autowired
  private CategoryService categoryService;
  
  @RequestMapping(value="/general",method=RequestMethod.GET)
  public String general(){
    return "backend/options/general";
  }
  
  @RequestMapping(value="/post",method=RequestMethod.GET)
  public String post(Model model){
    model.addAttribute("categorys", categoryService.list());
    return "backend/options/post";
  }

}

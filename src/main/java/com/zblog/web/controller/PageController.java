package com.zblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zblog.service.PostService;

@Controller
@RequestMapping("/pages")
public class PageController{
  @Autowired
  private PostService postService;
  
  @RequestMapping("/{pageid}")
  public String page(@PathVariable("pageid") String pageid, Model model){
    model.addAttribute("post", postService.loadById(pageid));
    return "post";
  }

}

package com.zblog.web.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.PostService;

@Controller
@RequestMapping("/tags")
public class TagsController{
  @Autowired
  private PostService postService;

  @RequestMapping(value = "/tagName", method = RequestMethod.GET)
  public String post(@PathVariable("categoryName") String tagName,
      @RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", postService.listByTag(tagName, page, 10));
    model.addAttribute("categoryOrTag", tagName);
    model.addAttribute(WebConstants.PRE_TITLE_KEY, tagName);
    return "index";
  }

}

package com.zblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.PostService;

@Controller
@RequestMapping("/categorys")
public class CategoryController{
  @Autowired
  private PostService postService;

  @RequestMapping(value = "/{categoryName}", method = RequestMethod.GET)
  public String post(@PathVariable("categoryName") String categoryName,
      @RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", postService.listByCategory(categoryName, page, 12));
    model.addAttribute("categoryName", categoryName);
    model.addAttribute(WebConstants.PRE_TITLE_KEY, categoryName);
    return "index";
  }

}

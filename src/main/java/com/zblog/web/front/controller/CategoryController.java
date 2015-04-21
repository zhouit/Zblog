package com.zblog.web.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.CategoryService;
import com.zblog.service.PostService;

@Controller
@RequestMapping("/categorys")
public class CategoryController{
  @Autowired
  private PostService postService;
  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/{categoryName}", method = RequestMethod.GET)
  public String post(@PathVariable("categoryName") String categoryName,
      @RequestParam(value = "page", defaultValue = "1") int page, Model model){
    Category category = categoryService.loadByName(categoryName);
    if(category != null){
      model.addAttribute("page", postService.listByCategory(category, page, 12));
    }
    
    model.addAttribute("categoryName", categoryName);
    model.addAttribute(WebConstants.PRE_TITLE_KEY, categoryName);
    return "index";
  }

}

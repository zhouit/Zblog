package com.zblog.web.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.biz.PostManager;
import com.zblog.core.WebConstants;
import com.zblog.core.dal.entity.Category;
import com.zblog.service.CategoryService;

@Controller
@RequestMapping("/categorys")
public class CategoryController{
  @Autowired
  private PostManager postManager;
  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/{categoryName}", method = RequestMethod.GET)
  public String post(@PathVariable("categoryName") String categoryName,
      @RequestParam(value = "page", defaultValue = "1") int page, Model model){
    Category category = categoryService.loadByName(categoryName);
    if(category != null){
      model.addAttribute("page", postManager.listByCategory(category, page, 10));
    }

    model.addAttribute("category", categoryName);
    model.addAttribute(WebConstants.PRE_TITLE_KEY, categoryName);
    return "index";
  }

}

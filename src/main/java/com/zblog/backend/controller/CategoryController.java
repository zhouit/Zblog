package com.zblog.backend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.biz.CategoryManager;
import com.zblog.common.dal.entity.Category;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.IdGenarater;
import com.zblog.service.CategoryService;

@Controller("bCategoryController")
@RequestMapping("/backend/categorys")
public class CategoryController{
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private CategoryManager categoryManager;

  @RequestMapping(method = RequestMethod.GET)
  public String index(){
    return "backend/post/category";
  }

  @ResponseBody
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public Object data(){
    return categoryService.listAsTree();
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Category category, String parent){
    category.setId(IdGenarater.uuid19());
    category.setCreateTime(new Date());
    category.setLastUpdate(new Date());
    return new MapContainer("success", categoryService.insertChildren(category, parent));
  }

  @ResponseBody
  @RequestMapping(value = "/{categoryName}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable String categoryName){
    categoryManager.remove(categoryName);
    return new MapContainer("success", true);
  }

}

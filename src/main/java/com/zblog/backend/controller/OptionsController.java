package com.zblog.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.backend.form.GeneralOption;
import com.zblog.backend.form.PostOption;
import com.zblog.backend.form.validator.OptionFormValidator;
import com.zblog.biz.OptionManager;
import com.zblog.common.plugin.MapContainer;
import com.zblog.service.CategoryService;

@Controller
@RequestMapping("/backend/options")
public class OptionsController{
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private OptionManager optionManager;

  @RequestMapping(value = "/general", method = RequestMethod.GET)
  public String general(Model model){
    model.addAttribute("form", optionManager.getGeneralOption());
    return "backend/options/general";
  }

  @RequestMapping(value = "/general", method = RequestMethod.POST)
  public String updateGeneral(GeneralOption form, Model model){
    model.addAttribute("form", form);
    MapContainer result=OptionFormValidator.validateGeneral(form);
    if(!result.isEmpty()){
      model.addAllAttributes(result);
      return "backend/options/general";
    }
    
    optionManager.updateGeneralOption(form);
    model.addAttribute("success", true);
    return "backend/options/general";
  }

  @RequestMapping(value = "/post", method = RequestMethod.GET)
  public String post(Model model){
    model.addAttribute("categorys", categoryService.list());
    model.addAttribute("form", optionManager.getPostOption());

    return "backend/options/post";
  }

  @RequestMapping(value = "/post", method = RequestMethod.POST)
  public String updatePost(PostOption form, Model model){
    model.addAttribute("categorys", categoryService.list());
    model.addAttribute("form", form);
    
    MapContainer result=OptionFormValidator.validatePost(form);
    if(!result.isEmpty()){
      model.addAllAttributes(result);
      return "backend/options/post";
    }
    
    model.addAttribute("success", true);
    optionManager.updatePostOption(form);
    return "backend/options/post";
  }

}

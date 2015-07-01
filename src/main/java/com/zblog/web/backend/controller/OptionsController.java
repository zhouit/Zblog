package com.zblog.web.backend.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.biz.OptionManager;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.CollectionUtils;
import com.zblog.service.CategoryService;
import com.zblog.service.email.MailSenderService;
import com.zblog.web.backend.form.GeneralOption;
import com.zblog.web.backend.form.MailOption;
import com.zblog.web.backend.form.PostOption;
import com.zblog.web.backend.form.validator.MailFormValidator;
import com.zblog.web.backend.form.validator.OptionFormValidator;

@Controller
@RequestMapping("/backend/options")
@RequiresRoles("admin")
public class OptionsController{
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private OptionManager optionManager;
  @Autowired
  private MailSenderService mailSenderService;

  @RequestMapping(value = "/general", method = RequestMethod.GET)
  public String general(Model model){
    model.addAttribute("form", optionManager.getGeneralOption());
    return "backend/options/general";
  }

  @RequestMapping(value = "/general", method = RequestMethod.POST)
  public String updateGeneral(GeneralOption form, Model model){
    model.addAttribute("form", form);
    MapContainer result = OptionFormValidator.validateGeneral(form);
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

    MapContainer result = OptionFormValidator.validatePost(form);
    if(!result.isEmpty()){
      model.addAllAttributes(result);
      return "backend/options/post";
    }

    model.addAttribute("success", true);
    optionManager.updatePostOption(form);
    return "backend/options/post";
  }

  @RequestMapping(value = "/email", method = RequestMethod.GET)
  public String mail(Model model){
    model.addAttribute("form", optionManager.getMailOption());
    return "backend/options/email";
  }

  @RequestMapping(value = "/email", method = RequestMethod.POST)
  public String updateMail(MailOption form, Model model){
    MapContainer result = MailFormValidator.validate(form);
    if(!CollectionUtils.isEmpty(result)){
      model.addAllAttributes(result);
      return "backend/options/email";
    }

    optionManager.updateMailOption(form);
    mailSenderService.setServerInfo(form);
    return "backend/options/email";
  }

}

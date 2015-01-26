package com.zblog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.biz.aop.PostIndexManager;
import com.zblog.common.dal.entity.Option;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.PostService;

@Controller
public class IndexController{
  @Autowired
  private PostService postService;
  @Autowired
  private PostIndexManager postIndexManager;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, String word, Model model){
    if(!StringUtils.isBlank(word)){
      model.addAttribute("page", postIndexManager.search(word, page));
      model.addAttribute(WebConstants.PRE_TITLE_KEY, word);
    }else{
      model.addAttribute("page", postService.listPost(page, 10));
    }
    return "index";
  }

  @RequestMapping("/init.json")
  public void init(@ModelAttribute Option option){
    option.setName("此为测试页");
    option.setValue("hello");
  }

}

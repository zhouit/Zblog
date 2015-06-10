package com.zblog.web.front.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.biz.PostManager;
import com.zblog.core.util.DateUtils;
import com.zblog.core.util.constants.WebConstants;

@Controller
@RequestMapping("/archives")
public class ArchiveController{
  @Autowired
  private PostManager postManager;

  @RequestMapping(value = "/{yearMonth}", method = RequestMethod.GET)
  public String post(@PathVariable("yearMonth") int yearMonth,
      @RequestParam(value = "page", defaultValue = "1") int page, Model model){
    Date date = DateUtils.parse("yyyyMM", yearMonth + "");
    String result = "404";
    if(date != null){
      model.addAttribute("page", postManager.listByMonth(date, page, 10));
      model.addAttribute(WebConstants.PRE_TITLE_KEY, DateUtils.formatDate("MMM,yyyy", date));

      model.addAttribute("archive", date);
      result = "index";
    }

    return result;
  }

}

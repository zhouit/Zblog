package com.zblog.web.front.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.biz.PostManager;
import com.zblog.core.WebConstants;
import com.zblog.core.util.DateUtils;

@Controller
@RequestMapping("/archives")
public class ArchiveController{
  @Autowired
  private PostManager postManager;

  @RequestMapping(value = "/{yearMonth}", method = RequestMethod.GET)
  public String post(@PathVariable("yearMonth") Date yearMonth,
      @RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", postManager.listByMonth(yearMonth, page, 10));
    model.addAttribute(WebConstants.PRE_TITLE_KEY, DateUtils.formatDate("MMM,yyyy", yearMonth));

    model.addAttribute("archive", yearMonth);
    return "index";
  }

  @InitBinder
  public void initBinder(WebDataBinder binder){
    DateFormat format = new SimpleDateFormat("yyyyMM");
    format.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));
  }

}

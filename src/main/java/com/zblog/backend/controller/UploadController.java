package com.zblog.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.biz.editor.Ueditor;
import com.zblog.service.UploadService;

@Controller
@RequestMapping("/backend/uploads")
public class UploadController{
  @Autowired
  private Ueditor ueditor;
  @Autowired
  private UploadService postmetaService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", postmetaService.list(page, 15));
    return "backend/upload/list";
  }

  @ResponseBody
  @RequestMapping(value = "/ueditor")
  public Object ueditor(HttpServletRequest request){
    return ueditor.server(request);
  }

}

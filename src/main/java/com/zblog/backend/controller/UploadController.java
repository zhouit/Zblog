package com.zblog.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.backend.Ueditor;

@Controller
@RequestMapping("/backend/uploads")
public class UploadController{

  @RequestMapping(method = RequestMethod.GET)
  public String index(){
    return "backend/uploads/list";
  }

  @ResponseBody
  @RequestMapping(value = "/ueditor")
  public Object ueditor(HttpServletRequest request){
    return Ueditor.server(request);
  }

}

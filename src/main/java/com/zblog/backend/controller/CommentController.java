package com.zblog.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.common.plugin.MapContainer;

@Controller("adminCommentController")
@RequestMapping("/backend/comments")
public class CommentController{

  @RequestMapping(method = RequestMethod.GET)
  public String index(){
    return "backend/comments";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.DELETE)
  public Object remove(){
    return new MapContainer("success", true);
  }

}

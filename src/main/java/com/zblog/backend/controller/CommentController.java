package com.zblog.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.common.plugin.MapContainer;

@Controller("adminCommentController")
@RequestMapping("/backend/comments")
public class CommentController{

  @RequestMapping(method = RequestMethod.GET)
  public String index(Model model){
    return "backend/comment/list";
  }

  @ResponseBody
  @RequestMapping(value="/{commentid}",method = RequestMethod.DELETE)
  public Object remove(@PathVariable("commentid") String commentid){
    return new MapContainer("success", true);
  }
  
}

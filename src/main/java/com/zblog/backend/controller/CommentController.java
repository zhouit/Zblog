package com.zblog.backend.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;
import com.zblog.service.CommentService;

@Controller("adminCommentController")
@RequestMapping("/backend/comments")
@RequiresRoles("admin")
public class CommentController{
  @Autowired
  private CommentService commentService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    PageModel pageModel = commentService.list(page, 15);
    model.addAttribute("page", pageModel);
    return "backend/comment/list";
  }

  @ResponseBody
  @RequestMapping(value = "/{commentid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("commentid") String commentid){
    return new MapContainer("success", true);
  }

  /**
   * 批准留言
   * 
   * @param commentid
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/{commentid}", method = RequestMethod.PUT)
  public Object approve(@PathVariable("commentid") String commentid){
    commentService.approve(commentid);
    return new MapContainer("success", true);
  }

}

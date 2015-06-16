package com.zblog.web.backend.controller;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.biz.CommentManager;
import com.zblog.core.dal.constants.CommentConstants;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;
import com.zblog.service.CommentService;

@Controller("adminCommentController")
@RequestMapping("/backend/comments")
@RequiresRoles("admin")
public class CommentController{
  @Autowired
  private CommentService commentService;
  @Autowired
  private CommentManager commentManager;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "type", defaultValue = "all") String type, Model model){
    Collection<String> status = "all".equals(type) ? Arrays.asList(CommentConstants.TYPE_APPROVE,
        CommentConstants.TYPE_WAIT) : Arrays.asList(type);
    PageModel<MapContainer> pageModel = commentService.listByStatus(page, 15, status);
    model.addAttribute("page", pageModel);
    model.addAttribute("type", type);
    model.addAttribute("stat", commentService.listCountByGroupStatus());
    return "backend/comment/list";
  }

  @ResponseBody
  @RequestMapping(value = "/{commentid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("commentid") String commentid){
    commentManager.deleteComment(commentid);
    return new MapContainer("success", true);
  }

  /**
   * 修改评论状态
   * 
   * @param commentid
   * @param status
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/{commentid}", method = RequestMethod.PUT)
  public Object approve(@PathVariable("commentid") String commentid, String status){
    commentManager.setStatus(commentid, status);
    return new MapContainer("success", true);
  }

}

package com.zblog.web.front.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.core.dal.entity.Post;
import com.zblog.core.util.CookieUtil;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.CommentService;
import com.zblog.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController{
  @Autowired
  private PostService postService;
  @Autowired
  private CommentService commentService;

  @RequestMapping(value = "/{postid}", method = RequestMethod.GET)
  public String post(@PathVariable("postid") String id, HttpServletRequest request, Model model){
    Post post = postService.loadById(id);
    if(post != null){
      model.addAttribute(WebConstants.PRE_TITLE_KEY, post.getTitle());
      model.addAttribute("post", post);
      model.addAttribute("comments",
          commentService.listByPost(id, new CookieUtil(request, null).getCookie("comment_author")));
    }
    return "post";
  }

}

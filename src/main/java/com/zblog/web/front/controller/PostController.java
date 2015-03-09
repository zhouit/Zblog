package com.zblog.web.front.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zblog.biz.CommentManager;
import com.zblog.biz.VisitStatManager;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.CookieUtil;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController{
  @Autowired
  private PostService postService;
  @Autowired
  private CommentManager commentManager;
  @Autowired
  private VisitStatManager visitStatManager;

  @RequestMapping(value = "/{postid}", method = RequestMethod.GET)
  public String post(@PathVariable("postid") String id, HttpServletRequest request, Model model){
    MapContainer post = postService.loadReadById(id);
    if(post != null){
      visitStatManager.record(id);
      model.addAttribute(WebConstants.PRE_TITLE_KEY, post.getAsString("title"));
      model.addAttribute("post", post);
      model.addAttribute("comments",
          commentManager.getAsTree(id, new CookieUtil(request, null).getCookie("comment_author")));
      /* 上一篇，下一篇 */
      model.addAttribute("next", postService.getNextPost(id));
      model.addAttribute("prev", postService.getPrevPost(id));
    }

    return post != null ? "post" : "404";
  }

}

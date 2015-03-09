package com.zblog.web.front.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zblog.biz.CommentManager;
import com.zblog.biz.VisitStatManager;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.CookieUtil;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.PostService;

@Controller
@RequestMapping("/pages")
public class PageController{
  @Autowired
  private PostService postService;
  @Autowired
  private CommentManager commentManager;
  @Autowired
  private VisitStatManager visitStatManager;

  @RequestMapping("/{pageid}")
  public String page(@PathVariable("pageid") String pageid, HttpServletRequest request, Model model){
    MapContainer post = postService.loadReadById(pageid);
    if(post != null){
      visitStatManager.record(pageid);
      model.addAttribute(WebConstants.PRE_TITLE_KEY, post.getAsString("title"));
      model.addAttribute("post", post);
      model.addAttribute("comments",
          commentManager.getAsTree(pageid, new CookieUtil(request, null).getCookie("comment_author")));
    }

    return post != null ? "post" : "404";
  }

}

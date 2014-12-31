package com.zblog.backend.controller;

import java.util.Date;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.zblog.biz.PostManager;
import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;
import com.zblog.service.CategoryService;
import com.zblog.service.PostService;

@Controller(value = "bPostController")
@RequestMapping("/backend/posts")
public class PostController{
  @Autowired
  private PostService postService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private CategoryService categoryService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    PageModel pageModel = postService.listPost(page, 15);
    model.addAttribute("page", pageModel);
    return "backend/post/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Post post, String uploadToken){
    post.setId(postService.createPostid());
    post.setLastUpdate(new Date());
    post.setTitle(HtmlUtils.htmlEscape(post.getTitle().trim()));
    post.setContent(post.getContent());
    String cleanTxt = Jsoup.parse(post.getContent()).text();
    post.setExcerpt(cleanTxt.length() > 350 ? cleanTxt.substring(0, 350) : cleanTxt);
    post.setCreator("admin");
    postManager.insertPost(post, uploadToken);
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(value = "/{postid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("postid") String postid){
    postManager.removePost(postid);
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(Model model){
    model.addAttribute("categorys", categoryService.list());
    return "backend/post/edit";
  }

}

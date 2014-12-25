package com.zblog.backend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.biz.PostManager;
import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
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
    model.addAttribute("page", postService.listPost(page, 15));
    return "backend/post/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Post post, String txt, String uploadToken){
    post.setId(postService.createPostid());
    post.setLastUpdate(new Date());
    post.setCreator("admin");
    postManager.insertPost(post, uploadToken);
    return new MapContainer("success", true);
  }
  
  @ResponseBody
  @RequestMapping(value="/{postid}",method = RequestMethod.DELETE)
  public Object remove(@PathVariable("postid")String postid){
    postManager.removePost(postid);
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(Model model){
    model.addAttribute("categorys", categoryService.list());

    return "backend/post/edit";
  }

  // @InitBinder
  // public void initBinder(WebDataBinder binder){
  // binder.registerCustomEditor(Reader.class, new PropertyEditorSupport(){
  //
  // @Override
  // public void setAsText(String text) throws IllegalArgumentException{
  // setValue(new StringReader(text));
  // }
  //
  // });
  // }

}

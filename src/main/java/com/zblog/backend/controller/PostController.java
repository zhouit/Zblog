package com.zblog.backend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
  private CategoryService categoryService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(){
    return "backend/post/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Post post, String txt){
    post.setId(postService.createId());
    post.setCreateTime(new Date());
    post.setLastUpdate(new Date());
    post.setCreator("admin");
    postService.add(post);
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

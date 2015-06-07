package com.zblog.web.backend.controller;

import java.util.Date;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.zblog.biz.OptionManager;
import com.zblog.biz.PostManager;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;
import com.zblog.core.util.JsoupUtils;
import com.zblog.core.util.PostTagHelper;
import com.zblog.core.util.StringUtils;
import com.zblog.core.util.constants.PostConstants;
import com.zblog.core.util.web.WebContextFactory;
import com.zblog.service.CategoryService;
import com.zblog.service.PostService;
import com.zblog.service.TagService;
import com.zblog.web.backend.form.validator.PostFormValidator;

@Controller(value = "adminPostController")
@RequestMapping("/backend/posts")
@RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
public class PostController{
  @Autowired
  private PostService postService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private OptionManager optionManager;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private TagService tagService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    PageModel pageModel = postService.listPost(page, 15);
    model.addAttribute("page", pageModel);
    return "backend/post/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Post post, String tags){
    MapContainer form = PostFormValidator.validatePublish(post);
    if(!form.isEmpty()){
      return form.put("success", false);
    }

    post.setId(optionManager.getNextPostid());
    post.setCreator(WebContextFactory.get().getUser().getId());
    post.setCreateTime(new Date());
    post.setLastUpdate(post.getCreateTime());

    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
    String content = HtmlUtils.htmlUnescape(post.getContent());
    post.setContent(JsoupUtils.filter(content));
    String cleanTxt = JsoupUtils.plainText(content);
    post.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0,
        PostConstants.EXCERPT_LENGTH) : cleanTxt);

    postManager.insertPost(post, PostTagHelper.from(post, tags, post.getCreator()));
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.PUT)
  public Object update(Post post, String tags){
    MapContainer form = PostFormValidator.validateUpdate(post);
    if(!form.isEmpty()){
      return form.put("success", false);
    }
    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
    String content = HtmlUtils.htmlUnescape(post.getContent());
    post.setContent(JsoupUtils.filter(content));
    String cleanTxt = JsoupUtils.plainText(content);
    post.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0,
        PostConstants.EXCERPT_LENGTH) : cleanTxt);

    post.setLastUpdate(new Date());
    postManager.updatePost(post, PostTagHelper.from(post, tags, WebContextFactory.get().getUser().getId()));
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(value = "/{postid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("postid") String postid){
    postManager.removePost(postid, PostConstants.TYPE_POST);
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(String pid, Model model){
    if(!StringUtils.isBlank(pid)){
      model.addAttribute("post", postService.loadById(pid));
      model.addAttribute("tags", StringUtils.join(tagService.listTagsByPost(pid), ","));
    }

    model.addAttribute("categorys", categoryService.list());
    return "backend/post/edit";
  }

}

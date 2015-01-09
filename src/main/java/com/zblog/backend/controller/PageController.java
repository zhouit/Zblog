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
import org.springframework.web.util.HtmlUtils;

import com.zblog.backend.form.validator.PostFormValidator;
import com.zblog.biz.OptionManager;
import com.zblog.biz.PostManager;
import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.JsoupUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.PostConstants;
import com.zblog.common.util.web.WebContextHolder;
import com.zblog.service.PostService;

@Controller(value = "adminPageController")
@RequestMapping("/backend/pages")
public class PageController{
  @Autowired
  private PostService postService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private OptionManager optionManager;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    PageModel pageModel = postService.listPage(page, 15);
    model.addAttribute("page", pageModel);
    return "backend/page/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Post post, String uploadToken){
    post.setType(PostConstants.TYPE_PAGE);
    MapContainer form = PostFormValidator.validatePublish(post);
    if(!form.isEmpty()){
      return form.put("success", false);
    }

    post.setId(optionManager.getNextPostid());
    post.setCreator(WebContextHolder.get().getUser().getNickName());
    post.setLastUpdate(new Date());

    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
    String content = HtmlUtils.htmlUnescape(post.getContent());
    post.setContent(JsoupUtils.filter(content));

    postManager.insertPost(post, uploadToken);
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.PUT)
  public Object update(Post post, String uploadToken){
    post.setType(PostConstants.TYPE_PAGE);
    MapContainer form = PostFormValidator.validateUpdate(post);
    if(!form.isEmpty()){
      return form.put("success", false);
    }
    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
    String content = HtmlUtils.htmlUnescape(post.getContent());
    post.setContent(JsoupUtils.filter(content));

    postManager.updatePost(post, uploadToken);
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(value = "/{postid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("postid") String postid){
    postManager.removePost(postid, PostConstants.TYPE_PAGE);
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(String pid, Model model){
    /* 是否可设置父页面 */
    boolean showparent = true;
    if(!StringUtils.isBlank(pid)){
      Post page = postService.loadEditById(pid);
      model.addAttribute("post", page);
      showparent = PostConstants.DEFAULT_PARENT.equals(page.getParent());
    }

    if(showparent)
      model.addAttribute("parent", postService.listPage(true));

    return "backend/page/edit";
  }

}

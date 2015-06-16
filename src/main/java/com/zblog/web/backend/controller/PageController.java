package com.zblog.web.backend.controller;

import java.util.Collections;
import java.util.Date;

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
import com.zblog.core.dal.constants.PostConstants;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.entity.Tag;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;
import com.zblog.core.util.JsoupUtils;
import com.zblog.core.util.StringUtils;
import com.zblog.service.PostService;
import com.zblog.service.vo.PostVO;
import com.zblog.web.backend.form.validator.PostFormValidator;
import com.zblog.web.support.WebContextFactory;

@Controller(value = "adminPageController")
@RequestMapping("/backend/pages")
@RequiresRoles("admin")
public class PageController{
  @Autowired
  private PostService postService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private OptionManager optionManager;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    PageModel<PostVO> pageModel = postManager.listPage(page, 15);
    model.addAttribute("page", pageModel);
    return "backend/page/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Post post){
    post.setType(PostConstants.TYPE_PAGE);
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

    postManager.insertPost(post, Collections.<Tag> emptyList());
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.PUT)
  public Object update(Post post){
    post.setType(PostConstants.TYPE_PAGE);
    MapContainer form = PostFormValidator.validateUpdate(post);
    if(!form.isEmpty()){
      return form.put("success", false);
    }
    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
    String content = HtmlUtils.htmlUnescape(post.getContent());
    post.setContent(JsoupUtils.filter(content));
    post.setLastUpdate(new Date());

    postManager.updatePost(post, Collections.<Tag> emptyList());
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
      Post page = postService.loadById(pid);
      model.addAttribute("post", page);
      showparent = PostConstants.DEFAULT_PARENT.equals(page.getParent());
    }

    if(showparent)
      model.addAttribute("parents", postService.listPage(true));

    return "backend/page/edit";
  }

}

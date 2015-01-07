package com.zblog.biz.aop;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zblog.biz.PostManager;
import com.zblog.common.dal.entity.Category;
import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.constants.PostConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.CategoryService;
import com.zblog.service.LinkService;
import com.zblog.service.PostService;
import com.zblog.template.FreeMarkerUtils;

@Component
public class StaticTemplate{
  private static final Logger logger = LoggerFactory.getLogger(StaticTemplate.class);
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private PostService postService;
  @Autowired
  private LinkService linksService;

  /**
   * 静态化导航栏
   */
  public void staticHeader(){
    MapContainer map = new MapContainer();
    map.put("domain", WebConstants.getDomain());
    map.put("title", WebConstants.TITLE);
    map.put("subtitle", WebConstants.SUBTITLE);
    map.put("categorys", categoryService.listAsTree());
    map.put("pages", postManager.listPageAsTree());

    FreeMarkerUtils.genHtml("/common/header.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/header.html"), map);

    logger.info("staticHeader");
  }

  /**
   * 静态化友情链接
   */
  public void staticLinks(){
    MapContainer map = new MapContainer();
    map.put("links", linksService.list());

    FreeMarkerUtils.genHtml("/common/link.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/link.html"), map);
    logger.info("staticLinks");
  }

  /**
   * 静态化文章,同时静态化最近发表or顶部导航页面栏
   * 
   * @param post
   */
  public void staticPost(Post post){
    MapContainer param = new MapContainer("domain", WebConstants.getDomain()).put("post", post);
    if(PostConstants.TYPE_POST.equals(post.getType())){
      Category category = categoryService.loadById(post.getCategoryid());
      param.put("categoryName", category.getName());
    }

    FreeMarkerUtils.genHtml("/post.html",
        new File(WebConstants.APPLICATION_PATH, "post/post-" + post.getId() + ".html"), param);
    logger.info("staticPost");

    staticRecentOrHeader(PostConstants.TYPE_POST.equals(post.getType()));
  }

  public void removePost(String postid, boolean ispost){
    String path = "post/post-" + postid + ".html";
    File postFile = new File(WebConstants.APPLICATION_PATH, path);
    postFile.delete();
    logger.info("removePost");

    staticRecentOrHeader(ispost);
  }

  /**
   * 静态化最近发表或者静态还顶部导航
   * 
   * @param ispost
   */
  private void staticRecentOrHeader(boolean ispost){
    if(ispost){
      MapContainer param = new MapContainer("domain", WebConstants.getDomain());
      param.put("posts", postService.listRecent());
      FreeMarkerUtils.genHtml("/common/recent.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
          + "/common/recent.html"), param);
      logger.info("staticRecent");
    }else{
      staticLinks();
    }
  }

}

package com.zblog.biz.aop;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.CategoryService;
import com.zblog.service.LinkService;
import com.zblog.service.PostService;
import com.zblog.template.FreeMarkerUtils;

@Component
public class StaticTemplate{
  private static final Logger logger = LoggerFactory.getLogger(StaticTemplate.class);

  /**
   * 静态化导航栏
   */
  public void staticHeader(){
    CategoryService categoryService = ApplicationContextUtil.getBean(CategoryService.class);

    MapContainer map = new MapContainer();
    map.put("domain", Constants.DOMAIN);
    map.put("categorys", categoryService.listAsTree());

    FreeMarkerUtils.genHtml("/common/header.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/header.html"), map);

    logger.info("staticHeader");
  }

  /**
   * 静态化友情链接
   */
  public void staticLinks(){
    LinkService linksService = ApplicationContextUtil.getBean(LinkService.class);

    MapContainer map = new MapContainer();
    map.put("links", linksService.list());

    FreeMarkerUtils.genHtml("/common/link.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/link.html"), map);
    logger.info("staticLinks");
  }

  /**
   * 静态化文章
   * 
   * @param post
   */
  public void staticPost(Post post){
    File file = new File(WebConstants.APPLICATION_PATH, DateUtils.formatDate("yyyy/MM", post.getCreateTime()));
    if(!file.exists())
      file.mkdirs();
    FreeMarkerUtils.genHtml("/post.html", new File(file, "post-" + post.getId() + ".html"), post);
    logger.info("staticPost");

    PostService postService = ApplicationContextUtil.getBean(PostService.class);
    MapContainer param = new MapContainer("domain", Constants.DOMAIN);
    param.put("posts", postService.listRecent());
    FreeMarkerUtils.genHtml("/common/recent.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/recent.html"), param);
    logger.info("staticRecent");
  }

  public void removeStaticPost(String postid){
    String path = "post/" + postid.substring(0, 4) + "/" + postid.substring(4, 6) + "/post-" + postid + ".html";
    File postFile = new File(WebConstants.APPLICATION_PATH, path);
    postFile.delete();
    logger.info("removeStaticPost");
  }

}

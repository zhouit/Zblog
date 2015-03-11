package com.zblog.biz.aop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zblog.biz.PostManager;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.constants.Constants;
import com.zblog.core.util.constants.PostConstants;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.CategoryService;
import com.zblog.service.LinkService;
import com.zblog.service.freemarker.FreeMarkerUtils;

/**
 * 静态化组件
 * 
 * @author zhou
 * 
 */
@Component
public class StaticTemplate{
  private static final Logger logger = LoggerFactory.getLogger(StaticTemplate.class);
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private LinkService linksService;

  public boolean staticHtml(String url, File file){
    boolean result = true;
    Writer writer = null;
    try{
      String text = Jsoup.connect(url).execute().body();
      writer = new OutputStreamWriter(new FileOutputStream(file), Charset.forName(Constants.ENCODING_UTF_8));
      IOUtils.write(text, writer);
    }catch(IOException e){
      logger.error("staticHtml error-->" + url, e);
      result = true;
    }finally{
      IOUtils.closeQuietly(writer);
    }

    return result;
  }

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
  public void postInsertOrUpdate(Post post){
    staticRecentOrHeader(PostConstants.TYPE_POST.equals(post.getType()));
  }

  public void postRemove(String postid, String postType){
    staticRecentOrHeader(PostConstants.TYPE_POST.equals(postType));
  }

  /**
   * 静态化最近发表或者静态还顶部导航
   * 
   * @param ispost
   */
  private void staticRecentOrHeader(boolean ispost){
    if(ispost){
      MapContainer param = new MapContainer("domain", WebConstants.getDomain());
      param.put("posts", postManager.listRecent(10));
      FreeMarkerUtils.genHtml("/common/recent.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
          + "/common/recent.html"), param);
      logger.info("staticRecent");
    }else{
      staticHeader();
    }
  }

}

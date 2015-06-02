package com.zblog.biz;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.entity.Upload;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.JsoupUtils;
import com.zblog.core.util.constants.PostConstants;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.core.util.web.WebContextFactory;
import com.zblog.core.wordpress.WordPressReader;
import com.zblog.service.CategoryService;

/**
 * wordpress站点导入，只会导入文章、附件
 * 
 * @author zhou
 *
 */
@Component
public class WordPressManager{
  @Autowired
  private UploadManager uploadManager;
  @Autowired
  private PostManager postManager;
  @Autowired
  private OptionManager optionManager;
  @Autowired
  private CategoryService categoryService;

  public void importData(InputStream wordpressXml){
    List<MapContainer> list = WordPressReader.load(wordpressXml);
    Map<String, Upload> links = new HashMap<>();
    /* wordpress站点域名 */
    String wpdomain = null;
    for(MapContainer mc : list){
      String itemType = mc.get("itemType");
      if("domain".equals(itemType)){
        wpdomain = mc.get("domain");
      }else if("attachment".equals(itemType)){
        Upload upload = importAttach(mc);
        links.put(mc.getAsString("attachUrl"), upload);
      }else if("post".equals(itemType)){
        importPost(mc, wpdomain, links);
      }
    }
  }

  /**
   * 导入指定附件
   * 
   * @param attach
   */
  private Upload importAttach(MapContainer attach){
    String attachUrl = attach.get("attachUrl");
    Date pubDate = attach.get("pubDate");
    InputStream in = null;
    Upload upload = null;
    try{
      UrlResource resource = new UrlResource(URI.create(attachUrl));
      in = resource.getInputStream();
      upload = uploadManager.insertUpload(in, pubDate, resource.getFilename(), WebContextFactory.get().getUser()
          .getId());
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      IOUtils.closeQuietly(in);
    }

    return upload;
  }

  /**
   * 导入指定文章
   * 
   * @param post
   */
  private void importPost(MapContainer post, String wpdoamin, Map<String, Upload> links){
    Post p = new Post();
    p.setType(PostConstants.TYPE_POST);
    p.setId(optionManager.getNextPostid());
    p.setCreator(WebContextFactory.get().getUser().getId());
    p.setTitle(post.getAsString("title"));
    Category category = categoryService.loadByName(post.getAsString("category"));
    if(category != null){
      p.setCategoryid(category.getId());
    }
    p.setCreateTime((Date) post.get("pubDate"));
    p.setLastUpdate(p.getCreateTime());
    String content = post.get("content");
    content = htmlImageOrLinkAdjust(content, wpdoamin, links);
    String cleanTxt = JsoupUtils.plainText(content);
    p.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0, PostConstants.EXCERPT_LENGTH)
        : cleanTxt);
    p.setContent(content);

    postManager.insertPost(p);
  }

  /**
   * 这里使用jsoup,因为jaxb必须要求well-formed格式的xml文档
   * 
   * @param html
   * @param wpdomain
   *          原始wordpress域名
   * @param links
   * @return
   */
  private static String htmlImageOrLinkAdjust(String html, String wpdomain, Map<String, Upload> links){
    Document doc = Jsoup.parse(html);
    OutputSettings settings = new OutputSettings();
    settings.prettyPrint(false);
    doc.outputSettings(settings);
    Elements eles = doc.select("img,a");
    for(int i = 0; i < eles.size(); i++){
      Element ele = eles.get(i);
      String tagName = ele.nodeName();
      String linkAttr = "img".equals(tagName) ? "src" : "href";
      String oldLink = ele.attr(linkAttr);
      if(!oldLink.startsWith("http://") && !oldLink.startsWith("https://")){
        oldLink = wpdomain + oldLink;
      }
      ele.attr(linkAttr, WebConstants.getDomainLink(links.get(oldLink).getPath()));
    }

    return doc.body().html();
  }
}

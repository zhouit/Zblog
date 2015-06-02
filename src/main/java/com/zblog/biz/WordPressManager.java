package com.zblog.biz;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.JsoupUtils;
import com.zblog.core.util.constants.PostConstants;
import com.zblog.core.util.web.WebContextFactory;
import com.zblog.core.wordpress.WordPressReader;

/**
 * wordpress文章导入
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

  public void importData(InputStream wordpressXml){
    List<MapContainer> list = WordPressReader.load(wordpressXml);
    for(MapContainer mc : list){
      String itemType = mc.get("itemType");
      if("attachment".equals(itemType)){
        importAttach(mc);
      }else if("post".equals(itemType)){
        importPost(mc);
      }
    }
  }

  private void importAttach(MapContainer attach){
    String attachUrl = attach.get("attachUrl");
    Date pubDate = attach.get("pubDate");
    InputStream in = null;
    try{
      UrlResource resource = new UrlResource(URI.create(attachUrl));
      in = resource.getInputStream();
      uploadManager.insertUpload(in, pubDate, resource.getFilename(), WebContextFactory.get().getUser().getId());
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      IOUtils.closeQuietly(in);
    }
  }

  private void importPost(MapContainer post){
    Post p = new Post();
    p.setType(PostConstants.TYPE_POST);
    p.setId(optionManager.getNextPostid());
    p.setCreator(WebContextFactory.get().getUser().getId());
    p.setCreateTime((Date) post.get("pubDate"));
    p.setLastUpdate(p.getCreateTime());
    String cleanTxt = JsoupUtils.plainText(post.getAsString("content"));
    p.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0, PostConstants.EXCERPT_LENGTH)
        : cleanTxt);

    postManager.insertPost(p);
  }

}

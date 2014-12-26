package com.zblog.biz;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.lucene.QueryBuilder;
import com.zblog.common.lucene.SearchEnginer;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.PostService;
import com.zblog.service.UploadService;

@Component
public class PostManager{
  @Autowired
  private PostService postService;
  @Autowired
  private UploadService uploadService;

  /**
   * 插入文章，同时更新上传文件的postid
   * 
   * @param post
   * @param uploadToken
   */
  @Transactional
  public void insertPost(Post post, String uploadToken){
    uploadService.updatePostid(post.getId(), uploadToken);
    postService.insert(post);
  }

  /**
   * 删除文章,同时删除文章对应的上传记录,及其文件
   * 
   * @param postid
   */
  @Transactional
  public void removePost(String postid){
    List<MapContainer> list = uploadService.listByPostid(postid);
    for(MapContainer mc : list){
      File file = new File(WebConstants.APPLICATION_PATH, mc.getAsString("path"));
      file.delete();
    }
    uploadService.deleteByPostid(postid);
    postService.deleteById(postid);
  }
  
  public PageModel search(String word,int pageIndex){
    PageModel result = new PageModel(pageIndex, 15);
    QueryBuilder builder = new QueryBuilder(SearchEnginer.postEnginer().getAnalyzer());
    builder.addShould("title", word).addShould("content", word);
    SearchEnginer.postEnginer().searchHighlight(builder, result);
    
    return result;
  }

}

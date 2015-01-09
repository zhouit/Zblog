package com.zblog.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.biz.aop.StaticTemplate;
import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.PostConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.OptionsService;
import com.zblog.service.PostService;
import com.zblog.service.UploadService;

@Component
public class PostManager{
  @Autowired
  private PostService postService;
  @Autowired
  private UploadService uploadService;
  @Autowired
  private OptionsService optionsService;
  @Autowired
  private StaticTemplate staticTemplate;

  /**
   * 插入文章，同时更新上传文件的postid
   * 
   * @param post
   * @param uploadToken
   */
  @Transactional
  public void insertPost(Post post, String uploadToken){
    if(!StringUtils.isBlank(uploadToken))
      uploadService.updatePostid(post.getId(), uploadToken);
    postService.insert(post);

    staticTemplate.staticPost(post);
  }

  /**
   * 更新文章,同时更新上传文件的postid
   * 
   * @param post
   * @param uploadToken
   */
  @Transactional
  public void updatePost(Post post, String uploadToken){
    if(!StringUtils.isBlank(uploadToken))
      uploadService.updatePostid(post.getId(), uploadToken);
    postService.update(post);

    staticTemplate.staticPost(post);
  }

  /**
   * 删除文章,同时删除文章对应的上传记录,及其文件
   * 
   * @param postid
   * @param postType
   *          post类型(文章or页面)
   */
  @Transactional
  public void removePost(String postid, String postType){
    uploadService.deleteByPostid(postid);
    postService.deleteById(postid);

    staticTemplate.removePost(postid, PostConstants.TYPE_POST.equals(postType));

    List<MapContainer> list = uploadService.listByPostid(postid);
    for(MapContainer mc : list){
      File file = new File(WebConstants.APPLICATION_PATH, mc.getAsString("path"));
      file.delete();
    }
  }

  public Collection<MapContainer> listPageAsTree(){
    List<MapContainer> list = postService.listPage(false);
    List<MapContainer> tree = new ArrayList<>();
    Iterator<MapContainer> it = list.iterator();
    while(it.hasNext()){
      MapContainer page = it.next();
      if(PostConstants.DEFAULT_PARENT.equals(page.getAsString("parent"))){
        tree.add(page);
        it.remove();
      }
    }

    it = list.iterator();
    while(it.hasNext()){
      MapContainer child = it.next();
      String parent = child.getAsString("parent");
      for(MapContainer pc : tree){
        if(parent.equals(pc.getAsString("id"))){
          pc.getAsList("children", MapContainer.class).add(child);
          it.remove();
          break;
        }
      }
    }

    return tree;
  }

}

package com.zblog.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.CollectionUtils;
import com.zblog.common.util.JsoupUtils;
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

  /**
   * 插入文章，同时更新上传文件的postid
   * 
   * @param post
   */
  @Transactional
  public void insertPost(Post post){
    postService.insert(post);
    /* 查找当前html中所有图片链接 */
    List<String> imgs = extractImagepath(JsoupUtils.getImages(post.getContent()));
    if(!CollectionUtils.isEmpty(imgs)){
      uploadService.updatePostid(post.getId(), imgs);
    }
  }

  /**
   * 更新文章,先重置以前文件对应的附件的postid,再更新文章对应的postid
   * 
   * @param post
   */
  @Transactional
  public void updatePost(Post post){
    uploadService.setNullPostid(post.getId());
    List<String> imgs = extractImagepath(JsoupUtils.getImages(post.getContent()));
    if(CollectionUtils.isEmpty(imgs))
      uploadService.updatePostid(post.getId(), imgs);

    postService.update(post);
  }

  /**
   * 删除文章,同时删除文章对应的上传记录,及其文件
   * 
   * @param postid
   * @param postType
   *          post类型(文章or页面),注：此参数为给aop使用
   */
  @Transactional
  public void removePost(String postid, String postType){
    List<MapContainer> list = uploadService.listByPostid(postid);
    uploadService.deleteByPostid(postid);
    postService.deleteById(postid);

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

  /**
   * 去掉图片中src地址的http域名前缀
   * 
   * @param imgs
   * @return
   */
  private static List<String> extractImagepath(List<String> imgs){
    List<String> imgpaths = new ArrayList<>(imgs.size());
    String domain = WebConstants.getDomain();
    for(String imgUrl : imgs){
      if(imgUrl.startsWith(domain)){
        imgpaths.add(imgUrl.substring(domain.length()));
      }
    }

    return imgpaths;
  }

}

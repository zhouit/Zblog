package com.zblog.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.zblog.core.dal.entity.Comment;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.TreeItem;

/**
 * 评论业务对象
 * 
 * @author zhou
 *
 */
public class CommentVO extends Comment implements TreeItem<CommentVO>{
  private Post post;
  private List<CommentVO> children;

  public Post getPost(){
    return post;
  }

  public void setPost(Post post){
    this.post = post;
  }

  public void setChildren(List<CommentVO> children){
    this.children = children;
  }

  @Override
  public List<CommentVO> getChildren(){
    return children;
  }

  @Override
  public void addChild(CommentVO comment){
    if(children == null)
      setChildren(new ArrayList<CommentVO>());

    getChildren().add(comment);
  }

}

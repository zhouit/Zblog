package com.zblog.service.vo;

import java.util.List;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.entity.User;

/**
 * 文章业务对象
 * 
 * @author zhou
 *
 */
public class PostVO extends Post{
  private User user;
  private Category category;
  private List<String> tags;

  public User getUser(){
    return user;
  }

  public void setUser(User user){
    this.user = user;
  }

  public Category getCategory(){
    return category;
  }

  public void setCategory(Category category){
    this.category = category;
  }

  public List<String> getTags(){
    return tags;
  }

  public void setTags(List<String> tags){
    this.tags = tags;
  }

}

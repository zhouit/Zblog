package com.zblog.core.dal.entity;

public class Tag extends BaseEntity{
  private String name;
  private String postid;

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getPostid(){
    return postid;
  }

  public void setPostid(String postid){
    this.postid = postid;
  }

}

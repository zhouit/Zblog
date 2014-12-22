package com.zblog.common.dal.entity;

/**
 * 文章、页面的元数据,如文章包含的图片等待
 * 
 * @author zhou
 * 
 */
public class Postmeta extends BaseEntity{
  private String postid;
  private String metakey;
  private String metavalue;

  public String getPostid(){
    return postid;
  }

  public void setPostid(String postid){
    this.postid = postid;
  }

  public String getMetakey(){
    return metakey;
  }

  public void setMetakey(String metakey){
    this.metakey = metakey;
  }

  public String getMetavalue(){
    return metavalue;
  }

  public void setMetavalue(String metavalue){
    this.metavalue = metavalue;
  }

}

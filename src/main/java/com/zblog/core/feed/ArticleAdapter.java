package com.zblog.core.feed;

import java.util.Date;

import com.zblog.core.feed.Channel.Article;
import com.zblog.core.plugin.MapContainer;

public class ArticleAdapter implements Article{
  private MapContainer post;

  public ArticleAdapter(MapContainer post){
    this.post = post;
  }

  @Override
  public String getTitle(){
    return post.getAsString("title");
  }

  @Override
  public String getLink(){
    return "/post/" + post.getAsString("id");
  }

  @Override
  public String getCategory(){
    return post.getAsString("category");
  }
  
  @Override
  public String getAuthor(){
    return post.getAsString("creator");
  }

  @Override
  public String getDescription(){
    return post.getAsString("excerpt");
  }

  @Override
  public Date getPubDate(){
    return post.getAsDate("createTime");
  }

  @Override
  public String getGuid(){
    return getLink();
  }

}

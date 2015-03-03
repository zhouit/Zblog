package com.zblog.core.feed;

import java.util.Date;
import java.util.List;

public class Channel{
  private String domain;
  private String logoUrl;
  private List<Article> items;

  public Channel(){
  }

  public Channel(String domain, String logoUrl){
    this.domain = domain;
    this.logoUrl = logoUrl;
  }

  public String getDomain(){
    return domain;
  }

  public void setDomain(String domain){
    this.domain = domain;
  }

  public String getLogoUrl(){
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl){
    this.logoUrl = logoUrl;
  }

  public void setItems(List<Article> items){
    this.items = items;
  }

  public List<Article> getItems(){
    return items;
  }

  public static interface Article{
    String getTitle();

    String getLink();

    String getCategory();
    
    String getAuthor();

    String getDescription();

    Date getPubDate();

    String getGuid();
  }

}

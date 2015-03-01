package com.zblog.web.backend.form;

/**
 * 默认设置表单
 * 
 * @author zhou
 * 
 */
public class GeneralOption{
  private String title;
  private String subtitle;
  private String weburl;

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getSubtitle(){
    return subtitle;
  }

  public void setSubtitle(String subtitle){
    this.subtitle = subtitle;
  }

  public String getWeburl(){
    return weburl;
  }

  public void setWeburl(String weburl){
    this.weburl = weburl;
  }

}

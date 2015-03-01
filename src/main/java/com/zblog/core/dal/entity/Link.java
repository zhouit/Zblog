package com.zblog.core.dal.entity;

public class Link extends BaseEntity{
  private String name;
  private String url;
  /* 注释 */
  private String notes;
  /* 可见性 */
  private boolean visible = true;

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public String getNotes(){
    return notes;
  }

  public void setNotes(String notes){
    this.notes = notes;
  }

  public boolean isVisible(){
    return visible;
  }

  public void setVisible(boolean visible){
    this.visible = visible;
  }

}

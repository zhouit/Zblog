package com.zblog.core.dal.entity;

/**
 * 站点选项,id和name为一致
 * 
 * @author zhou
 * 
 */
public class Option extends BaseEntity{
  private String id;
  private String name;
  private String value;

  public Option(){
  }

  public Option(String name, String value){
    this.name = name;
    this.value = value;
  }

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getValue(){
    return value;
  }

  public void setValue(String value){
    this.value = value;
  }

}

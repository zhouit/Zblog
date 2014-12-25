package com.zblog.common.dal.entity;

public class Option{
  private String id;
  private String name;
  private String value;

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

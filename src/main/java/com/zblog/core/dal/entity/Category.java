package com.zblog.core.dal.entity;

public class Category extends BaseEntity{
  private String name;
  private int leftv;
  private int rightv;
  /* 是否显示 */
  private boolean visible = true;

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public int getLeftv(){
    return leftv;
  }

  public void setLeftv(int leftv){
    this.leftv = leftv;
  }

  public int getRightv(){
    return rightv;
  }

  public void setRightv(int rightv){
    this.rightv = rightv;
  }

  public boolean isVisible(){
    return visible;
  }

  public void setVisible(boolean visible){
    this.visible = visible;
  }

}

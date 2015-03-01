package com.zblog.web.backend.form;

/**
 * 文章设置表单
 * 
 * @author zhou
 * 
 */
public class PostOption{
  /**
   * 博客页面至多显示数
   */
  private int maxshow;
  /**
   * 允许文章评论
   */
  private boolean allowComment;
  /**
   * 默认文章分类
   */
  private String defaultCategory;

  public int getMaxshow(){
    return maxshow;
  }

  public void setMaxshow(int maxshow){
    this.maxshow = maxshow;
  }

  public boolean isAllowComment(){
    return allowComment;
  }

  public void setAllowComment(boolean allowComment){
    this.allowComment= allowComment;
  }

  public String getDefaultCategory(){
    return defaultCategory;
  }

  public void setDefaultCategory(String defaultCategory){
    this.defaultCategory = defaultCategory;
  }

}

package com.zblog.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.TreeItem;

/**
 * 页面业务对象
 * 
 * @author zhou
 *
 */
public class PageVO extends Post implements TreeItem<PageVO>{
  private List<PageVO> children;

  public void setChildren(List<PageVO> children){
    this.children = children;
  }

  @Override
  public List<PageVO> getChildren(){
    return children;
  }

  @Override
  public void addChild(PageVO comment){
    if(children == null)
      setChildren(new ArrayList<PageVO>());

    getChildren().add(comment);
  }
}

package com.zblog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.common.dal.entity.Category;
import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.CategoryMapper;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.TreeUtils;
import com.zblog.common.util.IdGenarater;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.CategoryConstants;

@Service
public class CategoryService extends BaseSevice{
  @Autowired
  private CategoryMapper categoryMapper;
  
  @Transactional
  public boolean insertChildren(Category category, String parentName){
    Category parent = loadByName(StringUtils.isBlank(parentName) ? CategoryConstants.ROOT : parentName);
    category.setLeftv(parent.getRightv());
    category.setRightv(parent.getRightv() + 1);

    categoryMapper.addLeftv(parent.getRightv());
    categoryMapper.addRightv(parent.getRightv());
    add(category);

    return true;
  }
  
  @Transactional
  public boolean insertAfter(Category category, Category sbling){
    category.setLeftv(sbling.getRightv()+1);
    category.setRightv(sbling.getRightv() + 2);
    
    categoryMapper.addLeftv(sbling.getRightv());
    categoryMapper.addRightv(sbling.getRightv());
    add(category);
    
    return true;
  }

  public List<MapContainer> listAsTree(){
    List<MapContainer> list = super.list();
    if(list == null || list.isEmpty())
      return new ArrayList<>();
    return TreeUtils.buildTreefromPreOrder(list).getAsList("nodes", MapContainer.class);
  }

  public void init(){
    Category root = new Category();
    root.setId(IdGenarater.uuid19());
    root.setLeftv(1);
    root.setName(CategoryConstants.ROOT);
    root.setRightv(2);
    root.setCreateTime(new Date());
    add(root);
  }

  public Category loadByName(String name){
    return categoryMapper.loadByName(name);
  }

  @Override
  protected BaseMapper getMapper(){
    return categoryMapper;
  }

}

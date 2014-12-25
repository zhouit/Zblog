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
import com.zblog.common.dal.mapper.PostMapper;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.TreeUtils;
import com.zblog.common.util.IdGenarater;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.CategoryConstants;

@Service
public class CategoryService extends BaseService{
  @Autowired
  private CategoryMapper categoryMapper;
  @Autowired
  private PostMapper postMapper;

  @Transactional
  public boolean insertChildren(Category category, String parentName){
    Category parent = loadByName(StringUtils.isBlank(parentName) ? CategoryConstants.ROOT : parentName);
    category.setLeftv(parent.getRightv());
    category.setRightv(parent.getRightv() + 1);

    categoryMapper.updateTnsertLeftv(parent.getRightv());
    categoryMapper.updateInsertRightv(parent.getRightv());
    insert(category);

    return true;
  }

  @Transactional
  public boolean insertAfter(Category category, Category sbling){
    category.setLeftv(sbling.getRightv() + 1);
    category.setRightv(sbling.getRightv() + 2);

    categoryMapper.updateTnsertLeftv(sbling.getRightv());
    categoryMapper.updateInsertRightv(sbling.getRightv());
    insert(category);

    return true;
  }

  /**
   * 此方法只被CategoryManager调用
   * 
   * @param categoryName
   * @return
   */
  public String removeByName(String categoryName){
    Category parent = loadByName(categoryName);
    int length = parent.getRightv() - parent.getLeftv() + 1;
    categoryMapper.updateDeleteLeftv(parent.getLeftv(), length);
    categoryMapper.updateDeleteRightv(parent.getRightv(), length);
    categoryMapper.delete(parent.getLeftv(), parent.getRightv());

    return parent.getId();
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
    insert(root);
  }

  public Category loadByName(String name){
    return categoryMapper.loadByName(name);
  }

  @Override
  protected BaseMapper getMapper(){
    return categoryMapper;
  }

}

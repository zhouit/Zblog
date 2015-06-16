package com.zblog.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.core.dal.constants.OptionConstants;
import com.zblog.core.dal.entity.Category;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.CollectionUtils;
import com.zblog.service.CategoryService;
import com.zblog.service.OptionsService;
import com.zblog.service.PostService;

@Component
public class CategoryManager{
  @Autowired
  private PostService postService;
  @Autowired
  private OptionsService optionsService;
  @Autowired
  private CategoryService categoryService;

  /**
   * 删除分类同时,将该分类下的文章移动到默认分类
   * 
   * @param cname
   * @return
   */
  @Transactional
  public void remove(String cname){
    Category category = categoryService.loadByName(cname);
    List<Category> list = categoryService.loadChildren(category);
    List<String> all = new ArrayList<>(list.size() + 1);
    all.add(category.getId());
    for(Category temp : list){
      all.add(temp.getId());
    }

    /* 先更新post的categoryid，再删除category,外键约束 */
    postService.updateCategory(all, optionsService.getOptionValue(OptionConstants.DEFAULT_CATEGORY_ID));
    categoryService.remove(category);
  }

  public List<MapContainer> listAsTree(){
    List<MapContainer> preOrder = categoryService.list();
    if(CollectionUtils.isEmpty(preOrder))
      return Collections.emptyList();

    /* 根据一棵树的先序遍历集合还原成一颗树 */
    MapContainer root = preOrder.get(0).clone();
    for(int i = 1; i < preOrder.size(); i++){
      MapContainer current = preOrder.get(i).clone();
      int level = current.getAsInteger("level");
      current.put("level", level - 1);
      MapContainer parent = getLastParentByLevel(root, level - 1);
      parent.putIfAbsent("nodes", new ArrayList<MapContainer>()).add(current);
    }

    return root.get("nodes");
  }

  private static MapContainer getLastParentByLevel(MapContainer mc, int currentlevel){
    MapContainer current = mc;
    for(int i = 1; i < currentlevel; i++){
      List<MapContainer> children = current.putIfAbsent("nodes", new ArrayList<MapContainer>());
      current = children.get(children.size() - 1);
    }

    return current;
  }

}

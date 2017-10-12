package com.zblog.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.core.dal.constants.OptionConstants;
import com.zblog.core.dal.entity.Category;
import com.zblog.core.plugin.JMap;
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

  public List<JMap> listAsTree(){
    List<JMap> preOrder = categoryService.list();
    if(CollectionUtils.isEmpty(preOrder))
      return Collections.emptyList();

    /* 根据一棵树的先序遍历集合还原成一颗树 */
    JMap root = JMap.create(preOrder.get(0));
    for(int i = 1; i < preOrder.size(); i++){
      JMap current = JMap.create(preOrder.get(i));
      int level = current.getInt("level");
      current.put("level", level - 1);
      JMap parent = getLastParentByLevel(root, level - 1);
      parent.putOrGet("nodes", new ArrayList<JMap>()).add(current);
    }

    return root.getAs("nodes");
  }

  private static JMap getLastParentByLevel(JMap mc, int currentlevel){
    JMap current = mc;
    for(int i = 1; i < currentlevel; i++){
      List<JMap> children = current.putOrGet("nodes", new ArrayList<JMap>());
      current = children.get(children.size() - 1);
    }

    return current;
  }

}

package com.zblog.biz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.util.constants.OptionConstants;
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

}

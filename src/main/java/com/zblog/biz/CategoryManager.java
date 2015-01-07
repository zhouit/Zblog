package com.zblog.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.common.util.constants.OptionConstants;
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
    String categoryId = categoryService.removeByName(cname);

    postService.updateCategory(categoryId,
        optionsService.getOptionValue(OptionConstants.DEFAULT_CATEGORY_ID));
  }

}

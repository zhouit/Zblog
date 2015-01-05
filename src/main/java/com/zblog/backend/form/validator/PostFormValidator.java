package com.zblog.backend.form.validator;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.StringUtils;
import com.zblog.service.CategoryService;

public class PostFormValidator{

  public static MapContainer validatePublish(Post post){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(post.getTitle())){
      form.put("msg", "文章标题未填写");
    }else if(StringUtils.isBlank(post.getContent())){
      form.put("msg", "请填写文章内容");
    }else if(StringUtils.isBlank(post.getCategoryid())){
      form.put("msg", "请选择文章分类");
    }else{
      CategoryService categoryService = ApplicationContextUtil.getBean(CategoryService.class);
      if(categoryService.loadById(post.getCategoryid()) == null){
        form.put("msg", "文章分类不存在,请重试");
      }
    }

    return form;
  }

}

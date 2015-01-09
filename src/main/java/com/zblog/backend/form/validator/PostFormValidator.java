package com.zblog.backend.form.validator;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.PostConstants;

public class PostFormValidator{

  public static MapContainer validatePublish(Post post){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(post.getTitle())){
      form.put("msg", "文章标题未填写");
    }else if(StringUtils.isBlank(post.getContent())){
      form.put("msg", "请填写文章内容");
    }else if(PostConstants.TYPE_POST.equals(post.getType()) && StringUtils.isBlank(post.getCategoryid())){
      form.put("msg", "请选择文章分类");
    }

    return form;
  }

  public static MapContainer validateUpdate(Post post){
    MapContainer form = validatePublish(post);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

}

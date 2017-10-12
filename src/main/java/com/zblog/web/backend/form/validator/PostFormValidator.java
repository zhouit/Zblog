package com.zblog.web.backend.form.validator;

import com.zblog.core.dal.constants.PostConstants;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.JMap;
import com.zblog.core.util.StringUtils;

public class PostFormValidator{

  public static JMap validatePublish(Post post){
    return validatePost(post, true);
  }

  public static JMap validateUpdate(Post post){
    JMap form = validatePublish(post);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

  public static JMap validateFastUpdate(Post post){
    JMap form = validatePost(post, false);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

  private static JMap validatePost(Post post, boolean verifyContent){
	  JMap form = JMap.create();
    if(StringUtils.isBlank(post.getTitle())){
      form.put("msg", "文章标题未填写");
    }else if(verifyContent && StringUtils.isBlank(post.getContent())){
      form.put("msg", "请填写文章内容");
    }else if(PostConstants.TYPE_POST.equals(post.getType()) && StringUtils.isBlank(post.getCategoryid())){
      form.put("msg", "请选择文章分类");
    }

    return form;
  }

}

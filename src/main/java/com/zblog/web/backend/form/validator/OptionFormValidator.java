package com.zblog.web.backend.form.validator;

import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.StringUtils;
import com.zblog.web.backend.form.GeneralOption;
import com.zblog.web.backend.form.PostOption;

public class OptionFormValidator{

  public static MapContainer validateGeneral(GeneralOption option){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(option.getTitle())){
      form.put("title", "需填写站点名称名称");
    }
    if(StringUtils.isBlank(option.getSubtitle())){
      form.put("subtitle", "需填写副标题");
    }
    if(StringUtils.isBlank(option.getDescription())){
      form.put("description", "需填写站点描述");
    }
    if(StringUtils.isBlank(option.getKeywords())){
      form.put("keywords", "需填写站点关键字");
    }
    if(StringUtils.isBlank(option.getWeburl())){
      form.put("weburl", "需填写网站url");
    }

    return form;
  }

  public static MapContainer validatePost(PostOption option){
    MapContainer form = new MapContainer();
    if(option.getMaxshow() < 1){
      form.put("maxshow", "格式错误");
    }

    if(StringUtils.isBlank(option.getDefaultCategory())){
      form.put("defaultCategory", "默认分类格式错误");
    }

    return form;
  }

}

package com.zblog.backend.form.validator;

import com.zblog.common.dal.entity.Link;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.StringUtils;

public class LinkFormValidator{

  public static MapContainer validate(Link link){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(link.getName())){
      form.put("msg", "需填写链接名称");
    }else if(StringUtils.isBlank(link.getUrl()) || !link.getUrl().startsWith("http://")){
      form.put("msg", "链接格式不正确");
    }

    return form;
  }

}

package com.zblog.backend.form.validator;

import com.zblog.common.dal.entity.User;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.CommRegular;
import com.zblog.common.util.StringUtils;

public class UserFormValidator{

  public static MapContainer validateInsert(User user){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(user.getNickName())){
      form.put("msg", "需填写用户名称");
    }else if(StringUtils.isBlank(user.getEmail()) || !user.getEmail().matches(CommRegular.EMAIL)){
      form.put("msg", "邮箱格式不正确");
    }else if(StringUtils.isBlank(user.getRealName())){
      form.put("msg", "需填写用户真实名称");
    }

    return form;
  }

  public static MapContainer validateUpdate(User user){
    MapContainer form = validateInsert(user);
    if(StringUtils.isBlank(user.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

}

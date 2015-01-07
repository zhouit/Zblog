package com.zblog.backend.form.validator;

import com.zblog.common.dal.entity.User;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.CommRegular;
import com.zblog.common.util.StringUtils;

public class UserFormValidator{

  public static MapContainer validateInsert(User user, String repass){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(user.getNickName())){
      form.put("nickName", "需填写用户名称");
    }
    if(StringUtils.isBlank(user.getEmail()) || !user.getEmail().matches(CommRegular.EMAIL)){
      form.put("email", "邮箱格式不正确");
    }
    if(StringUtils.isBlank(user.getRealName())){
      form.put("realName", "需填写用户真实名称");
    }
    if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(repass)){
      form.put("password", "需填写用户密码");
    }
    if(!user.getPassword().equals(repass) || !user.getPassword().matches(CommRegular.PASSWD)){
      form.put("password", "两次密码不一致或者密码格式不对");
    }

    return form;
  }

  public static MapContainer validateUpdate(User user, String repass){
    MapContainer form = validateInsert(user, repass);
    if(StringUtils.isBlank(user.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

}

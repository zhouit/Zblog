package com.zblog.backend.form.validator;

import com.zblog.backend.form.LoginForm;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.CommRegular;
import com.zblog.common.util.StringUtils;

public class LoginFormValidator{

  public static MapContainer validateLogin(LoginForm form){
    MapContainer result = new MapContainer();
    if(StringUtils.isBlank(form.getUsername()) || !form.getUsername().matches(CommRegular.USERNAME)){
      result.put("msg", "请输入正确的用户名");
    }else if(StringUtils.isBlank(form.getPassword()) || !form.getPassword().matches(CommRegular.PASSWD)){
      result.put("msg", "请输入格式正确的密码");
    }

    return result;
  }

}

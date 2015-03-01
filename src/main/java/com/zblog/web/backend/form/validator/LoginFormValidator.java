package com.zblog.web.backend.form.validator;

import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.CommRegular;
import com.zblog.core.util.StringUtils;
import com.zblog.web.backend.form.LoginForm;

public class LoginFormValidator{

  public static MapContainer validateLogin(LoginForm form){
    MapContainer result = new MapContainer();
    if(StringUtils.isBlank(form.getUsername()) || !form.getUsername().matches(CommRegular.USERNAME)){
      result.put("msg", "请输入正确的用户名");
    }else if(StringUtils.isBlank(form.getPassword()) || !form.getPassword().matches(CommRegular.PASSWD)){
      result.put("msg", "密码输入有误");
    }

    return result;
  }

}

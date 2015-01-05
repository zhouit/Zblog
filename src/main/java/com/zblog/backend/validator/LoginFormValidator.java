package com.zblog.backend.validator;

import java.util.HashMap;
import java.util.Map;

import com.zblog.common.util.CommRegular;
import com.zblog.common.util.StringUtils;

public class LoginFormValidator{

  /**
   * 校验登录
   * 
   * @param name
   * @param pass
   * @return
   */
  public static Map<String, String> validateLogin(String name, String pass){
    Map<String, String> error = new HashMap<String, String>();
    if(StringUtils.isBlank(name) || !name.matches(CommRegular.USERNAME)){
      error.put("name", "请输入正确的用户名");
    }else if(StringUtils.isBlank(pass) || !pass.matches(CommRegular.PASSWD)){
      error.put("pass", "请输入格式正确的密码");
    }

    return error;

  }

}

package com.zblog.web.backend.form.validator;

import com.zblog.core.plugin.JMap;
import com.zblog.core.util.CommRegular;
import com.zblog.core.util.StringUtils;
import com.zblog.web.backend.form.LoginForm;

public class LoginFormValidator{
  private static String guard;

  /**
   * 设置防止恶意登录token,由spring注入
   *
   * @param guard
   */
  public static void setLoginGuard(String guard){
    LoginFormValidator.guard = guard;
  }

  public static JMap validateLogin(LoginForm form){
	  JMap result = JMap.create();
    /* 防止用户恶意登录 */
    if(!guard.equals(form.getGuard())){
      result.put("msg", "请不要尝试登录了!");
    }else if(StringUtils.isBlank(form.getUsername()) || !form.getUsername().matches(CommRegular.USERNAME)){
      result.put("msg", "请输入正确的用户名");
    }else if(StringUtils.isBlank(form.getPassword()) || !form.getPassword().matches(CommRegular.PASSWD)){
      result.put("msg", "密码输入有误");
    }

    return result;
  }

}

package com.zblog.service;

import org.springframework.stereotype.Service;

import com.zblog.common.dal.entity.User;

@Service
public class AuthenticationService{
  /**
   * 以/backend开头,非/login结尾
   */
  private static final String BACKEND_URL = "^/backend.*(?<!/login)$";

  /**
   * user用户是否有访问uri权限
   * 
   * @param uri
   * @param user
   * @return
   */
  public boolean isAuthentication(String uri, User user){
    return !(uri.matches(BACKEND_URL) && user == null);
  }

}

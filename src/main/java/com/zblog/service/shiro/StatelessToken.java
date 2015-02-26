package com.zblog.service.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class StatelessToken implements AuthenticationToken{
  private static final long serialVersionUID = 1L;
  
  private String id;
  private String password;

  public StatelessToken(String id, String password){
    this.id = id;
    this.password = password;
  }

  @Override
  public Object getPrincipal(){
    return id;
  }

  @Override
  public Object getCredentials(){
    return password;
  }

}

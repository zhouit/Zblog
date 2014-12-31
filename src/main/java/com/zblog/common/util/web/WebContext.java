package com.zblog.common.util.web;

import com.zblog.common.dal.entity.User;

public class WebContext{
  private String sid;
  private int loginId;
  private User user;
  private String ip;
  private String requestURI;
  private String lastLoginTime;

  public String getSid(){
    return sid;
  }

  public void setSid(String sid){
    this.sid = sid;
  }

  public int getLoginId(){
    return loginId;
  }

  public void setLoginId(int loginId){
    this.loginId = loginId;
  }

  public String getIp(){
    return ip;
  }

  public void setIp(String ip){
    this.ip = ip;
  }

  public User getUser(){
    return user;
  }

  public void setUser(User user){
    this.user = user;
  }

  public boolean isLogon(){
    return user != null;
  }

  public String getRequestURI(){
    return requestURI;
  }

  public void setRequestURI(String requestURI){
    this.requestURI = requestURI;
  }

  public String getLastLoginTime(){
    return lastLoginTime;
  }

  public void setLastLoginTime(String lastLoginTime){
    this.lastLoginTime = lastLoginTime;
  }

}

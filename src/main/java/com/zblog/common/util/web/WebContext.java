package com.zblog.common.util.web;

public class WebContext{
  private String sid;
  private int loginId;
  private String userId;
  private String userName;
  private String nickName;
  private String ip;
  private boolean logon;
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

  public String getUserId(){
    return userId;
  }

  public void setUserId(String userId){
    this.userId = userId;
  }

  public String getUserName(){
    return userName;
  }

  public void setUserName(String userName){
    this.userName = userName;
  }

  public String getNickName(){
    return nickName;
  }

  public void setNickName(String nickName){
    this.nickName = nickName;
  }

  public String getIp(){
    return ip;
  }

  public void setIp(String ip){
    this.ip = ip;
  }

  public boolean isLogon(){
    return logon;
  }

  public void setLogon(boolean logon){
    this.logon = logon;
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

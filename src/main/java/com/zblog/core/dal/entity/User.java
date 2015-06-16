package com.zblog.core.dal.entity;

import com.zblog.core.dal.constants.UserConstants;

public class User extends BaseEntity{
  private String nickName;
  private String realName;
  private String password;
  private String email;
  /* 用户账号状态 */
  private String status = UserConstants.USER_STATUS_NORMAL;
  private String role = UserConstants.USER_ROLE_CONTRIBUTOR;
  private String description;

  public String getNickName(){
    return nickName;
  }

  public void setNickName(String nickName){
    this.nickName = nickName;
  }

  public String getRealName(){
    return realName;
  }

  public void setRealName(String realName){
    this.realName = realName;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public String getStatus(){
    return status;
  }

  public void setStatus(String status){
    this.status = status;
  }

  public String getRole(){
    return role;
  }

  public void setRole(String role){
    this.role = role;
  }

  public String getDescription(){
    return description;
  }

  public void setDescription(String description){
    this.description = description;
  }

}

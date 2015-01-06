package com.zblog.backend.form;

public class LoginForm{
  private String username;
  private String password;
  private String redirectURL;

  public String getUsername(){
    return username;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String getRedirectURL(){
    return redirectURL;
  }

  public void setRedirectURL(String redirectURL){
    this.redirectURL = redirectURL;
  }

}

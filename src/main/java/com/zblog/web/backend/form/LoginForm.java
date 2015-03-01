package com.zblog.web.backend.form;

public class LoginForm{
  private String username;
  private String password;
  private boolean remeber;
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

  public boolean isRemeber(){
    return remeber;
  }

  public void setRemeber(boolean remeber){
    this.remeber = remeber;
  }

  public String getRedirectURL(){
    return redirectURL;
  }

  public void setRedirectURL(String redirectURL){
    this.redirectURL = redirectURL;
  }

}

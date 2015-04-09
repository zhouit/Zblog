package com.zblog.web.backend.form;

public class LoginForm{
  private String username;
  private String password;
  private boolean remeber;
  /* 防止后台暴力破解的字段 */
  private String guard;
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

  public String getGuard(){
    return guard;
  }

  public void setGuard(String guard){
    this.guard = guard;
  }

  public String getRedirectURL(){
    return redirectURL;
  }

  public void setRedirectURL(String redirectURL){
    this.redirectURL = redirectURL;
  }

}

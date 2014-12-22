package com.zblog.web.form;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;

public class BaseForm{
  private String id;
  private Map<String, String> errors = new LinkedHashMap<String, String>();

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return id;
  }

  public Map<String, String> getErrors(){
    return errors;
  }

  public void setErrors(Map<String, String> errors){
    this.errors = errors;
  }

  public void putError(String key, String val){
    errors.put(key, val);
  }

  public void putErrors(Map<String, String> errors){
    errors.putAll(errors);
  }

  public boolean hasError(){
    return errors.size() > 0;
  }

  public boolean hasError(String key){
    return errors.containsKey(key);
  }

  public final long getNanoTime(){
    return System.nanoTime();
  }

  public final WebContext getWebContext(){
    WebContext webContext = WebContextHolder.get();
    return webContext == null ? new WebContext() : webContext;
  }

  public final String getRequestURI(){
    try{
      return URLDecoder.decode(getWebContext().getRequestURI(), "UTF-8");
    }catch(Exception e){
      return getWebContext().getRequestURI();
    }

  }

  public final boolean isLogon(){
    return getWebContext().isLogon();
  }

  public String getDomain(){
    return Constants.DOMAIN;
  }

}

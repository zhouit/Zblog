package com.zblog.common.plugin;

/**
 * jsonp对象
 * 
 * @author zhou
 * 
 */
public class Jsonp{
  private String function;
  private Object json;

  public Jsonp(String function, Object json){
    this.function = function;
    this.json = json;
  }

  public String getFunction(){
    return function;
  }

  public Object getJson(){
    return json;
  }

}

package com.zblog.common.util;

public final class StringUtils{

  private StringUtils(){
  }

  public static boolean isBlank(String str){
    return str == null || str.trim().length() == 0;
  }

  public static String trimLeft(String source){
    if(isBlank(source)) return null;

    int index = 0;
    for(; index < source.length(); index++){
      if(source.charAt(index) > 32) break;
    }

    return source.substring(index);
  }

  public static String trimRight(String source){
    if(isBlank(source)) return null;

    int index = source.length() - 1;
    for(; index >= 0; index--){
      if(source.charAt(index) > 32) break;
    }

    return source.substring(0, index + 1);
  }

  public static String htmlTrim(String str1){
    String str = "";
    str = str1;
    // 剔出了<html>的标签
    str = str.replaceAll("</?[^>]+>", "");
    // 去除空格
    str = str.replaceAll("\\s", "");
    str = str.replaceAll("&nbsp;", "");
    str = str.replaceAll("&amp;", "&");
    str = str.replace(".", "");
    str = str.replace("\"", "‘");
    str = str.replace("'", "‘");
    return str;
  }

}

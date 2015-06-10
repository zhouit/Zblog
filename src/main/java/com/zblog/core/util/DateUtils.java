package com.zblog.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils{

  private DateUtils(){
  }

  public static String currentDate(String pattern){
    return formatDate(pattern, new Date());
  }

  public static String formatDate(String pattern, Date date){
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    return format.format(date);
  }

  /**
   * 解析日期，注:此处为严格模式解析，即20151809这样的日期会解析错误
   * 
   * @param pattern
   * @param date
   * @return
   */
  public static Date parse(String pattern, String date){
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    format.setLenient(false);
    Date result = null;
    try{
      result = format.parse(date);
    }catch(Exception e){
      e.printStackTrace();
    }

    return result;
  }

}

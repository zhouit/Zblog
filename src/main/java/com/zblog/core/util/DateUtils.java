package com.zblog.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.zblog.core.Constants;

public class DateUtils{

  private DateUtils(){
  }

  public static String currentDate(String pattern){
    return formatDate(pattern, new Date());
  }

  public static String formatDate(String pattern, Date date){
    SimpleDateFormat format = new SimpleDateFormat(pattern, Constants.LOCALE_CHINA);
    return format.format(date);
  }

  /**
   * 指定locale格式化日期
   * 
   * @param pattern
   * @param date
   * @param locale
   * @return
   */
  public static String formatDate(String pattern, Date date, Locale locale){
    SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
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
    return parse(pattern, date, Constants.LOCALE_CHINA);
  }

  /**
   * 解析日期，注:此处为严格模式解析，即20151809这样的日期会解析错误
   * 
   * @param pattern
   * @param date
   * @param locale
   * @return
   */
  public static Date parse(String pattern, String date, Locale locale){
    SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
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

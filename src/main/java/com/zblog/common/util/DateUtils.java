package com.zblog.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils{
  
  private DateUtils(){}
  
  public static String currentDate(String pattern){
    return formatDate(pattern, new Date());
  }
  
  public static String formatDate(String pattern,Date date){
    SimpleDateFormat format=new SimpleDateFormat(pattern);
    return format.format(date);
  }

}

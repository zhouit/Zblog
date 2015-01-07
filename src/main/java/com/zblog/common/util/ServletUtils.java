package com.zblog.common.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils{

  private ServletUtils(){
  }

  public static String getDomain(HttpServletRequest request){
    String result = request.getScheme() + "://" + request.getServerName();
    if(request.getServerPort() != 80){
      result += ":" + request.getServerPort();
    }
    result += request.getContextPath();

    return result;
  }

  public static String getIp(HttpServletRequest request){
    return IpUtils.getIp(request);
  }

}

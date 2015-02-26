package com.zblog.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils{

  private ServletUtils(){
  }

  public static boolean isMultipartContent(HttpServletRequest request){
    if(!"post".equals(request.getMethod().toLowerCase())){
      return false;
    }
    String contentType = request.getContentType();
    if(contentType == null){
      return false;
    }
    if(contentType.toLowerCase().startsWith("multipart/")){
      return true;
    }
    return false;
  }

  /**
   * 是否为ajax请求
   * 
   * @param request
   * @return
   */
  public static boolean isAjax(HttpServletRequest request){
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  public static String getDomain(HttpServletRequest request){
    String result = request.getScheme() + "://" + request.getServerName();
    if(request.getServerPort() != 80){
      result += ":" + request.getServerPort();
    }
    result += request.getContextPath();

    return result;
  }

  public static void sendRedirect(HttpServletResponse response, String url){
    try{
      response.sendRedirect(url);
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  public static String getIp(HttpServletRequest request){
    return IpUtils.getIp(request);
  }

}

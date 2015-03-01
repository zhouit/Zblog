package com.zblog.core.util;

import javax.servlet.http.HttpServletRequest;

class IpUtils{

  private IpUtils(){
  }

  /**
   * 获取客户端真实IP
   * 
   * @param request
   */
  public static String getIp(HttpServletRequest request){
    String ip = request.getHeader("x-forwarded-for");
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
      ip = request.getHeader("Proxy-Client-IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
      ip = request.getHeader("X-Real-IP");
    }
    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
      ip = request.getRemoteAddr();
    }

    // 多级反向代理检测
    if(ip != null && ip.indexOf(",") > 0){
      ip = ip.trim().split(",")[0];
    }

    return ip;
  }

}

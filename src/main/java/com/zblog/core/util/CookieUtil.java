package com.zblog.core.util;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ReflectionUtils;

import com.zblog.core.Constants;
import com.zblog.core.security.Base64Codec;

public class CookieUtil{
  private static Method setHttpOnlyMethod;
  private static final String PATH = "/";

  private HttpServletRequest request;
  private HttpServletResponse response;
  private String domain;

  static{
    setHttpOnlyMethod = ReflectionUtils.findMethod(Cookie.class, "setHttpOnly", boolean.class);
  }

  public CookieUtil(final HttpServletRequest request, final HttpServletResponse response){
    this(request, response, null);
  }

  public CookieUtil(final HttpServletRequest request, final HttpServletResponse response, final String domain){
    this.request = request;
    this.response = response;
    this.domain = domain;
  }

  /**
   * 获取cookie值，默认base64解码
   * 
   * @param name
   * @return
   */
  public String getCookie(String name){
    return getCookie(name, true);
  }

  public String getCookie(String name, boolean decode){
    Cookie[] cookies = request.getCookies();
    if(cookies == null || cookies.length == 0){
      return null;
    }

    for(Cookie cookie : cookies){
      if(cookie.getName().equals(name)){
        String value = cookie.getValue();
        return decode ? decode(value) : value;
      }
    }

    return null;
  }

  public void setCookie(String name, String value){
    setCookie(name, value, false);
  }

  public void setCookie(String name, String value, boolean httpOnly){
    setCookie(name, value, PATH, httpOnly);
  }

  public void setCookie(String name, String value, String path, boolean httpOnly){
    setCookie(name, value, path, httpOnly, -1);
  }

  public void setCookie(String name, String value, boolean httpOnly, int expiry){
    setCookie(name, value, PATH, httpOnly, expiry, true);
  }

  public void setCookie(String name, String value, String path, boolean httpOnly, int expiry){
    setCookie(name, value, path, httpOnly, expiry, true);
  }

  public void setCookie(String name, String value, String path, boolean httpOnly, int expiry, boolean encode){
    Cookie cookie = new Cookie(name, encode ? encode(value) : value);
    cookie.setPath(path);
    cookie.setMaxAge(expiry);
    /* 在javaee6以上,tomcat7以上支持Cookie.setHttpOnly方法,这里向下兼容 */
    if(setHttpOnlyMethod != null && httpOnly){
      ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
    }

    if(!StringUtils.isBlank(domain)){
      cookie.setDomain(domain);
    }
    response.addCookie(cookie);
  }

  public void removeCokie(String name){
    setCookie(name, null, false, 0);
  }

  private String encode(String value){
    return value == null ? null : new String(Base64Codec.encode(value.getBytes(Constants.ENCODING_UTF_8)));
  }

  private String decode(String value){
    return value == null ? null : new String(Base64Codec.decode(value), Constants.ENCODING_UTF_8);
  }
}

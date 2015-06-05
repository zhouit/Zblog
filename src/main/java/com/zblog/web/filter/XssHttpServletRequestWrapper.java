package com.zblog.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.HtmlUtils;

/**
 * xss过滤包装器
 * 
 * @author zhou
 * 
 */
class XssHttpServletRequestWrapper extends HttpServletRequestWrapper{

  public XssHttpServletRequestWrapper(HttpServletRequest request){
    super(request);
  }

  @Override
  public String getHeader(String name){
    String value = super.getHeader(name);
    return value == null ? null : cleanXSS(value);
  }

  @Override
  public String getParameter(String name){
    String value = super.getParameter(name);
    return value == null ? null : cleanXSS(value);
  }

  @Override
  public String[] getParameterValues(String name){
    String[] values = super.getParameterValues(name);
    if(values == null)
      return null;

    String[] result = new String[values.length];
    for(int i = 0; i < result.length; i++){
      result[i] = cleanXSS(values[i]);
    }

    return result;
  }

  private String cleanXSS(String value){
    return HtmlUtils.htmlEscape(value);
  }

}

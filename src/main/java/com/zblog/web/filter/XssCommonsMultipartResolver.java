package com.zblog.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

/**
 * 具有xss功能的multipart解析器
 * 
 * @author zhou
 * 
 */
public class XssCommonsMultipartResolver extends CommonsMultipartResolver{

  public XssCommonsMultipartResolver(){
  }

  @Override
  public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException{
    MultipartParsingResult parsingResult = parseRequest(request);
    return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
        parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes()){

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

      @Override
      public String getHeader(String name){
        String value = super.getHeader(name);
        return value == null ? null : cleanXSS(value);
      }

    };
  }

  private String cleanXSS(String value){
    return HtmlUtils.htmlEscape(value);
  }

}

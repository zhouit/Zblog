package com.zblog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.WebUtils;

import com.zblog.core.Constants;
import com.zblog.core.util.StringUtils;

/**
 * Xss过滤器,可处理multipart请求
 * 
 * @author zhou
 * 
 */
public class XssFilter extends OncePerRequestFilter{
  private MultipartResolver multipartResolver;
  private String encoding;

  public XssFilter(){
  }

  @Override
  protected void initFilterBean() throws ServletException{
    XssCommonsMultipartResolver resolver = new XssCommonsMultipartResolver();
    resolver.setDefaultEncoding(Constants.ENCODING_UTF_8.name());
    resolver.setMaxUploadSize(4096000);
    resolver.setServletContext(getServletContext());
    multipartResolver = resolver;
  }

  public void setEncoding(String encoding){
    this.encoding = encoding;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException{
    if(!StringUtils.isBlank(encoding)){
      request.setCharacterEncoding(encoding);
      response.setCharacterEncoding(encoding);
    }

    boolean multipartRequestParsed = false;
    HttpServletRequest req = new XssHttpServletRequestWrapper(request);
    if(multipartResolver.isMultipart(req)){
      req = multipartResolver.resolveMultipart(req);
      multipartRequestParsed = true;
    }

    try{
      filterChain.doFilter(req, response);
    }finally{
      if(multipartRequestParsed){
        multipartResolver.cleanupMultipart(WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class));
      }
    }
  }

}

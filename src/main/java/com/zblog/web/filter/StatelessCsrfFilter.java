package com.zblog.web.filter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.zblog.common.util.CookieUtil;
import com.zblog.common.util.constants.Constants;

/**
 * 处理csrf攻击,Stateless CSRF方案
 * 
 * @author zhou
 * 
 */
public class StatelessCsrfFilter extends OncePerRequestFilter{
  private List<String> excludes = new ArrayList<>();
  static List<String> METHODS = Arrays.asList("POST", "DELETE", "PUT", "PATCH");
  private Random random = new SecureRandom();

  @Override
  protected void initFilterBean() throws ServletException{
    FilterConfig config = getFilterConfig();
    excludes.add(config.getInitParameter("exclude"));
  }

  private boolean isAjaxVerificationToken(HttpServletRequest request, String csrfToken){
    String headToken = request.getHeader(Constants.CRSF_TOKEN);
    return headToken != null && headToken.equals(csrfToken);
  }

  private boolean isVerificationToken(HttpServletRequest request, String csrfToken){
    String paramToken = request.getParameter(Constants.CRSF_TOKEN);
    return paramToken != null && paramToken.equals(csrfToken);
  }

  private boolean isAjax(HttpServletRequest request){
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException{
    String url = request.getRequestURI();
    for(String match : excludes){
      if(url.matches(match)){
        filterChain.doFilter(request, response);
        return;
      }
    }
    
    CookieUtil cookieUtil = new CookieUtil(request, response);
    String csrfToken = cookieUtil.getCookie(Constants.COOKIE_CRSF_TOKEN);

    if(METHODS.contains(request.getMethod())){
      if(isAjax(request) && !isAjaxVerificationToken(request, csrfToken)){
        response.setContentType("application/json");
        response.getWriter().write("{'status':'403','success':false,'msg':'非法请求,请刷新重试'}");
        return;
      }else if(!isVerificationToken(request, csrfToken)){
        if(response.isCommitted())
          response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }
    }

    csrfToken = Long.toString(random.nextLong(), 36);
    cookieUtil.setCookie(Constants.COOKIE_CRSF_TOKEN, csrfToken, false, 1800);
    request.setAttribute(Constants.CRSF_TOKEN, csrfToken);

    filterChain.doFilter(request, response);
  }

}

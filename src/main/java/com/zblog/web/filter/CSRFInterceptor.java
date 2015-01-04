package com.zblog.web.filter;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zblog.common.util.StringUtils;
import com.zblog.common.util.UrlUtil;
import com.zblog.common.util.constants.Constants;

/**
 * 处理csrf攻击,这里不使用cookie
 * 
 * @author zhou
 * 
 */
public class CSRFInterceptor extends HandlerInterceptorAdapter{
  static List<String> METHODS = Arrays.asList("POST", "DELETE", "PUT", "PATCH");

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
    if(METHODS.contains(request.getMethod())){
      if(isAjax(request) && !isAjaxVerificationToken(request)){
        response.setContentType("application/json");
        response.getWriter().write("{'status':'403','success':false,'msg':'非法请求,请刷新重试'}");
        return false;
      }else if(!isVerificationToken(request)){
        String encodeURL = UrlUtil.encode(request.getRequestURL()
            + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString()));

        response.sendRedirect("/backend/login?redirectURL=" + encodeURL);
        return false;
      }
    }

    return true;
  }

  private boolean isAjaxVerificationToken(HttpServletRequest request){
    String headToken = request.getHeader(Constants.AJAX_CRSF_TOKEN);
    String sessionToken = request.getSession(true).getAttribute(Constants.AJAX_CRSF_TOKEN) + "";
    return headToken != null && headToken.equals(sessionToken);
  }

  private boolean isVerificationToken(HttpServletRequest request){
    String paramToken = request.getParameter(Constants.CRSF_TOKEN);
    String sessionToken = request.getSession(true).getAttribute(Constants.CRSF_TOKEN) + "";
    return paramToken != null && paramToken.equals(sessionToken);
  }

  private boolean isAjax(HttpServletRequest request){
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

}

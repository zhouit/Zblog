package com.zblog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zblog.common.util.ServletUtils;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;
import com.zblog.service.shiro.StatelessToken;

/**
 * 继承OncePerRequestFilter保证一次请求只过滤一次(以兼容不同的servlet container)
 * 
 * @author zhou
 * 
 */
public class LoginFilter extends OncePerRequestFilter{

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException{
    WebContext context = WebContextHolder.get();
    if(context != null)
      return;

    try{
      context = new WebContext(request, response);
      context.setUser(CookieRemberManager.extractValidRememberMeCookieUser(request, response));
      // 保存上下文
      WebContextHolder.set(context);
      
      accessControl();

      boolean ajax = ServletUtils.isAjax(request);
      if(!ajax)
        request.setAttribute("g", new Global(ServletUtils.getDomain(request)));

      filterChain.doFilter(request, response);
    }catch(Exception e){
      if(ServletUtils.isAjax(request)){
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.ENCODING_UTF_8);
        response.getWriter().write("{'status':'500','success':false,'msg':'操作失败,服务端出错'}");
      }else{
        handleException(e.getCause(), request, response);
      }
    }finally{
      WebContextHolder.remove();
    }
  }

  private void accessControl(){
    WebContext context = WebContextHolder.get();
    
    if(context.isLogon()){
      /* 注意：此处要委托给Realm进行登录 */
      logger.debug("logon-->"+context.getRequest().getRequestURI());
      SecurityUtils.getSubject().login(new StatelessToken(context.getUser().getId(), context.getUser().getPassword()));
    }
  }

  /**
   * 统一处理controller的异常，这里不使用springmvc的异常处理体系
   * 
   * @param t
   * @param response
   */
  private void handleException(Throwable t, HttpServletRequest request, HttpServletResponse response){
    String className = t.getClass().getName();
    if("org.apache.shiro.authz.UnauthenticatedException".equals(className)){
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//      String uri = StringUtils.emptyDefault(request.getRequestURI(), "/");
//      String encodeURL = UrlUtil.encode(uri
//          + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString()));
//
//      ServletUtils.sendRedirect(response, "/backend/login?redirectURL=" + encodeURL);
      ServletUtils.sendRedirect(response, "/resources/error/unauthenticated.html");
    }else{
      logger.error("error happend", t);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      ServletUtils.sendRedirect(response, "/resources/error/500.html");
    }
  }

}

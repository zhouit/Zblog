package com.zblog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.util.ServletUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.UrlUtil;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;
import com.zblog.service.AuthenticationService;

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

      boolean ajax = ServletUtils.isAjax(request);
      if(!ajax)
        addGloableAttr(request);

      String uri = StringUtils.emptyDefault(request.getRequestURI(), "/");
      AuthenticationService service = ApplicationContextUtil.getBean(AuthenticationService.class);
      if(service.isAuthentication(uri, context.getUser())){

        filterChain.doFilter(request, response);
        return;
      }

      if(ajax){
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.ENCODING_UTF_8);
        response.getWriter().write("{'status':'403','success':false,'msg':'没有权限'}");
      }else{
        String encodeURL = UrlUtil.encode(uri
            + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString()));

        response.sendRedirect("/backend/login?redirectURL=" + encodeURL);
      }
    }catch(Exception e){
      logger.error(e.getMessage(), e);

      if(ServletUtils.isAjax(request)){
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.ENCODING_UTF_8);
        response.getWriter().write("{'status':'500','success':false,'msg':'操作失败,服务端出错'}");
      }
    }finally{
      WebContextHolder.remove();
    }
  }

  private void addGloableAttr(HttpServletRequest request){
    String result = request.getScheme() + "://" + request.getServerName();
    if(request.getServerPort() != 80){
      result += ":" + request.getServerPort();
    }
    result += request.getContextPath();
    request.setAttribute("g", new Global(result));
  }

}

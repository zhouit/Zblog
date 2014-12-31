package com.zblog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.zblog.common.dal.entity.User;
import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.util.CookieUtil;
import com.zblog.common.util.IpUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.UrlUtil;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;
import com.zblog.service.AuthenticationService;
import com.zblog.service.UserService;

public class LoginFilter extends OncePerRequestFilter{

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException{
    WebContext context = WebContextHolder.get();
    if(context != null)
      return;

    try{
      context = getWebContext(request, response);
      String uri = request.getRequestURI();
      context.setRequestURI("".equals(uri) ? "/" : uri);
      // 保存上下文
      WebContextHolder.set(context);

      boolean ajax = isAjax(request);
      if(!ajax)
        addGloableAttr(request);

      AuthenticationService service = ApplicationContextUtil.getBean(AuthenticationService.class);
      if(service.isAuthentication(uri, context.getUser())){
        filterChain.doFilter(request, response);
        return;
      }

      if(ajax){
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"403\"}");
      }else{
        String encodeURL = UrlUtil.encode(uri
            + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString()));

        response.sendRedirect("/backend/login?redirectURL=" + encodeURL);
      }
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.getMessage(), e);

      if(isAjax(request)){
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"500\"}");
      }
    }finally{
      WebContextHolder.remove();
    }
  }

  private boolean isAjax(HttpServletRequest request){
    return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

  private void addGloableAttr(HttpServletRequest request){
    String result = request.getScheme() + "://" + request.getServerName();
    if(request.getServerPort() != 80){
      result += ":" + request.getServerPort();
    }
    result += request.getContextPath();
    request.setAttribute("domain", result);
  }

  private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response){
    WebContext context = new WebContext();
    context.setIp(IpUtils.getIp(request));

    // 该处实现登录控制
    CookieUtil cookieUtil = new CookieUtil(request, response);
    String cid = cookieUtil.getCookie(Constants.COOKIE_CONTEXT_ID);

    if(StringUtils.isBlank(cid) || !cid.matches("[0-9a-zA-Z]{19}"))
      return context;

    UserService userService = ApplicationContextUtil.getBean(UserService.class);
    User user = userService.loadById(cid);
    context.setUser(user);

    return context;
  }

}

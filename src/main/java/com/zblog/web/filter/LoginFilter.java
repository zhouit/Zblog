package com.zblog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.util.ThreadContext;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zblog.core.Constants;
import com.zblog.core.WebConstants;
import com.zblog.core.util.ServletUtils;
import com.zblog.core.util.Threads;
import com.zblog.service.shiro.StatelessToken;
import com.zblog.service.vo.Global;
import com.zblog.web.support.CookieRemberManager;
import com.zblog.web.support.WebContext;
import com.zblog.web.support.WebContextFactory;

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
    WebContext context = WebContextFactory.get();
    if(context != null)
      return;

    try{
      context = new WebContext(request, response);
      context.setUser(CookieRemberManager.extractValidRememberMeCookieUser(request, response));
      // 保存上下文
      WebContextFactory.set(context);

      accessControl();

      boolean ajax = ServletUtils.isAjax(request);
      if(!ajax){
        request.setAttribute("g", new Global(ServletUtils.getDomain(request)));
      }

      /* 设置domain */
      WebConstants.setDomain(ServletUtils.getDomain(request));

      filterChain.doFilter(request, response);
    }catch(Exception e){
      if(ServletUtils.isAjax(request)){
        logger.error("error happend", e);
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.ENCODING_UTF_8.name());
        response.getWriter().write("{'status':'500','success':false,'msg':'操作失败,服务端出错'}");
      }else{
        handleException(Threads.getRootCause(e), request, response);
      }
    }finally{
      WebContextFactory.remove();
      cleanup();
    }
  }

  private void accessControl(){
    WebContext context = WebContextFactory.get();

    if(context.isLogon()){
      /* 注意：此处要委托给Realm进行登录 */
      logger.debug("logon-->" + context.getRequest().getRequestURI());
      SecurityUtils.getSubject().login(new StatelessToken(context.getUser().getId(), context.getUser().getPassword()));
    }
  }

  /**
   * 此处需要unbind,同见
   * {@link org.apache.shiro.web.servlet.AbstractShiroFilter#doFilterInternal}中
   * subject.execute(..) ,不能Subject.logout()(并未移除ThreadContext中的Subject对象)
   * 
   * @see <a href="http://shiro.apache.org/subject.html">Subject</a>
   */
  private void cleanup(){
    ThreadContext.unbindSubject();
  }

  /**
   * 统一处理controller的异常，这里不使用springmvc的异常处理体系
   * 
   * @param t
   * @param response
   */
  private void handleException(Throwable t, HttpServletRequest request, HttpServletResponse response){
    if(t instanceof AuthorizationException){
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      // String uri = StringUtils.emptyDefault(request.getRequestURI(), "/");
      // String encodeURL = UrlUtil.encode(uri
      // + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" +
      // request.getQueryString()));
      //
      // ServletUtils.sendRedirect(response, "/backend/login?redirectURL=" +
      // encodeURL);
      ServletUtils.sendRedirect(response, "/resource/error/unauthenticated.html");
    }else{
      logger.error("error happend", t);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      ServletUtils.sendRedirect(response, "/resource/error/500.html");
    }
  }

}

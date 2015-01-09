package com.zblog.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.zblog.common.dal.entity.User;
import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.util.CookieUtil;
import com.zblog.common.util.ServletUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.UrlUtil;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;
import com.zblog.service.AuthenticationService;
import com.zblog.service.UserService;

/**
 * 继承OncePerRequestFilter保证一次请求只过滤一次(以兼容不同的servlet container)
 * 
 * @author zhou
 * 
 */
public class LoginXssFilter extends OncePerRequestFilter{
  XssCommonsMultipartResolver multipartResolver;

  @Override
  protected void initFilterBean() throws ServletException{
    multipartResolver = new XssCommonsMultipartResolver();
    multipartResolver.setDefaultEncoding(Constants.ENCODING_UTF_8);
    multipartResolver.setMaxUploadSize(4096000);
    multipartResolver.setServletContext(getServletContext());
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException{
    WebContext context = WebContextHolder.get();
    if(context != null)
      return;

    boolean multipartRequestParsed = false;
    try{
      context = getWebContext(request, response);
      // 保存上下文
      WebContextHolder.set(context);

      boolean ajax = isAjax(request);
      if(!ajax)
        addGloableAttr(request);

      String uri = StringUtils.emptyDefault(request.getRequestURI(), "/");
      AuthenticationService service = ApplicationContextUtil.getBean(AuthenticationService.class);
      if(service.isAuthentication(uri, context.getUser())){
        HttpServletRequest req = new XssHttpServletRequestWrapper(request);
        /* 使用multipart/form-data上传时,上面会获取不到值,转换即可 */
        if(ServletUtils.isMultipartContent(req)){
          req = multipartResolver.resolveMultipart(req);
          multipartRequestParsed = true;
        }

        filterChain.doFilter(req, response);
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
      e.printStackTrace();
      logger.error(e.getMessage(), e);

      if(isAjax(request)){
        response.setContentType("application/json");
        response.setCharacterEncoding(Constants.ENCODING_UTF_8);
        response.getWriter().write("{'status':'500','success':false,'msg':'操作失败,服务端出错'}");
      }
    }finally{
      WebContextHolder.remove();
      if(multipartRequestParsed){
        multipartResolver.cleanupMultipart(WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class));
      }
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
    request.setAttribute("g", new Global(result));
  }

  private WebContext getWebContext(HttpServletRequest request, HttpServletResponse response){
    WebContext context = new WebContext(request, response);

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

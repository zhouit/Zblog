<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="z" uri="/WEB-INF/tld/function.tld" %>
<!DOCTYPE Html>
<html>
 <head>
  <jsp:include page="common/bootstrap.jsp" flush="false" />
 </head>
 <body style="background-color: #fbfbfb;">
   <div id="login" class="clearfix">
     <c:if test="${msg!=null}"><p class="message">${msg}<br></p></c:if>
     <div id="logo">
       <img title="Zblog" src="../../resource/img/logo.png" />
       <p><a href="${g.domain}" title="不知道自己在哪">← 回到${g.title}</a></p>
     </div>
     <form id="loginform" method="post">
       <input type="hidden" name="CSRFToken" value="${CSRFToken}" />
       <div class="form-group">
         <div class="input-group">
           <div class="input-group-addon"><i class="icon-user"></i></div>
           <input type="text" autocomplete="off" class="form-control" name="username" value="${z:cookieValue('un')}" placeholder="用户名/邮箱" />
         </div>
       </div>
       <div class="form-group">
         <div class="input-group">
           <div class="input-group-addon"><i class="icon-key"></i></div>
           <input type="password" autocomplete="off" class="form-control" name="password" placeholder="密码" />
         </div>
       </div>
     <!--   <div class="form-group">
          <input type="password" autocomplete="off" class="form-control" placeholder="验证码"
              style="display: inline-block;width: 50%;" />
          <img alt="" />
       </div> -->
       <div class="checkbox">
         <label><input type="checkbox" name="remeber" /> 记住我的登录信息</label>
         <button type="submit" class="btn btn-primary" style="margin-left: 40px;">登录</button>
       </div>
     </form>
  </div>
  <footer class="footer">
    <div class="container">
      <p class="text-muted">Power By <a href="https://github.com/dongfangshangren/Zblog" target="_blank">Zblog</a></p>
    </div>
  </footer>
 </body>
</html>

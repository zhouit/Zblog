<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE Html>
<html>
 <head>
  <%@include file="../common/bootstrap.html" %>
 </head>
 <body style="background-color: #fbfbfb;">
   <div id="login" style="width:300px;margin:0px auto;margin-top: 100px;">
     <h1><img title="Zblog" src="../../resource/img/zblog-logo.png" /></h1>
     <c:if test="${msg!=null}"><p class="message">${msg}<br></p></c:if>
     <form id="loginform" method="post" action="login">
       <input type="hidden" name="CSRFToken" value="${CSRFToken}" />
       <div class="form-group">
         <div class="input-group">
           <div class="input-group-addon"><i class="icon-user"></i></div>
           <input type="text" autocomplete="off" class="form-control" name="username" placeholder="用户名/邮箱" />
         </div>
       </div>
       <div class="form-group">
         <div class="input-group">
           <div class="input-group-addon"><i class="icon-key"></i></div>
           <input type="password" autocomplete="off" class="form-control" name="password" placeholder="密码" />
         </div>
       </div>
       <div class="form-group">
          <input type="password" autocomplete="off" class="form-control" placeholder="验证码"
              style="display: inline-block;width: 63%;" />
          <img alt="" src="http://www.zhouhaocheng.cn" />
       </div>
       <button type="submit" class="btn btn-primary btn-block">登录</button>
     </form>
  </div>
  <footer class="footer">
     <div class="container">
       <p class="text-muted">Power By Zblog</p>
     </div>
   </footer>
 </body>
</html>

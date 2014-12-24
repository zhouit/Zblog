<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
  <%@include file="../common/bootstrap.html" %>
 </head>
 <body style="background-color: #fbfbfb;">
   <div id="login" style="width:300px;margin:0px auto;margin-top: 100px;">
     <h1><img title="Zblog" src="../../resource/img/zblog-logo.png" /></h1>
     <form id="loginform" method="post" action="login">
       <div class="form-group">
          <input type="text" autocomplete="off" class="form-control" placeholder="用户名/邮箱" />
       </div>
       <div class="form-group">
          <input type="password" autocomplete="off" class="form-control" placeholder="密码" />
       </div>
       <div class="form-group">
          <input type="password" autocomplete="off" class="form-control" placeholder="验证码"
              style="display: inline-block;width: 63%;" />
          <img alt="" src="https://account.antblue.com/qcode/login" />
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

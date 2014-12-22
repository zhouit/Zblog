<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
  <%@include file="../common/bootstrap.html" %>
 </head>
 <body>
  <div class="container" style="margin-top:100px">
    <div class="panel panel-success" style="width:300px;margin:0px auto;">
       <div class="panel-heading">登录</div>
       <div class="panel-body">
         <form method="post" action="login">
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
    </div>
  </div>
  <footer class="footer">
     <div class="container">
       <p class="text-muted">Power By Zblog</p>
     </div>
   </footer>
 </body>
</html>

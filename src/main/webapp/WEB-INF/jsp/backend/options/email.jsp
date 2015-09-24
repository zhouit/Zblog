<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE Html>
<html>
 <head>
  <jsp:include page="../common/bootstrap.jsp" flush="false" />
 </head>
 <body style="margin-top: 50px;">
  <jsp:include page="../common/navbar.jsp" flush="false" />
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-3 col-md-2" id="sidebar" style="padding: 0;">
        <jsp:include page="../common/sidebar.jsp" flush="false" />
      </div>
      <div class="col-sm-9 col-md-10">
        <ol class="breadcrumb header">
          <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
          <li>系统设置</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>邮件服务器</div>
          <div class="panel-body">
            <c:if test="${success}">
              <div class="alert alert-success" style="padding: 10px 15px;">修改成功</div>
            </c:if>
            <form action="" method="post" class="form-horizontal text-left" role="form">
              <div class="form-group ${host!=null?'has-error':''}">
                <label class="col-sm-3" for="host">*主机Host</label>
                <div class="col-sm-6">
                  <input placeholder="host" name="host" class="form-control" type="text" value="${form.host}" />
                  <p class="help-block">${host}</p>
                </div>
              </div>
               <div class="form-group ${port!=null?'has-error':''}">
                 <label class="col-sm-3" for="port">*端口</label>
                 <div class="col-sm-6">
                   <input name="port" class="form-control" type="text" value="${form.port}" />
                 </div>
               </div>
               <div class="form-group ${username!=null?'has-error':''}">
                 <label class="col-sm-3" for="username">*用户名</label>
                 <div class="col-sm-6">
                   <input name="username" class="form-control" type="text" value="${form.username}" />
                   <p class="help-block">${username}</p>
                 </div>
               </div>
               <div class="form-group ${password!=null?'has-error':''}">
                 <label class="col-sm-3" for="password">*密码</label>
                 <div class="col-sm-6">
                   <input name="password" class="form-control" type="text" value="${form.password}" />
                   <p class="help-block">${password}</p>
                 </div>
               </div>
              <div class="form-group" style="padding-top: 20px;">
                <div class="col-sm-offset-3 col-sm-2">
                  <button type="submit" class="btn btn-primary btn-block">保存更改</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
 </body>
</html>

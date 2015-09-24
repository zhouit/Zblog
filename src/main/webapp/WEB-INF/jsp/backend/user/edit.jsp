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
          <li>用户</li>
          <li class="active">添加用户</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>新建账户</div>
          <div class="panel-body">
            <form action="." method="post" class="form-horizontal text-left" role="form">
              <c:if test="${user!=null}">
                <input type="hidden" name="_method" value="PUT" />
                <input type="hidden" name="id" value="${user.id}" />
              </c:if>
              <div class="form-group ${nickName!=null?'has-error':''}">
                <label class="col-sm-3" for="title">*用户名</label>
                <div class="col-sm-6">
                  <input class="form-control" type="text" name="nickName" value="${user.nickName}" ${user!=null?'readonly':''} />
                  <p class="help-block">${nickName}</p>
                </div>
              </div>
               <div class="form-group ${email!=null?'has-error':''}">
                 <label class="col-sm-3" for="email">*电子邮件</label>
                 <div class="col-sm-6">
                   <input class="form-control" type="text" name="email" value="${user.email}" />
                   <p class="help-block">${email}</p>
                 </div>
               </div>
               <div class="form-group ${realName!=null?'has-error':''}">
                 <label class="col-sm-3" for="realName">真实名称</label>
                 <div class="col-sm-6">
                    <input class="form-control" autocomplete="off" type="text" name="realName" value="${user.realName}" />
                     <p class="help-block">${realName}</p>
                  </div>
               </div>
               <div class="form-group ${password!=null?'has-error':''}">
                 <label class="col-sm-3" for="password">*密码 </label>
                 <div class="col-sm-6">
                   <input class="form-control input-sm" type="password" name="password" />
                   <c:if test="${user!=null}"><p class="help-block">如果您想修改您的密码，请在此输入新密码。不然请留空</p></c:if>
                   <input style="margin-top: 5px;" class="form-control input-sm" type="password" name="repass" />
                   <p class="help-block">${password!=null?password:'密码长度6-16'}</p>
                 </div>
               </div>
              <div class="form-group">
                <label class="col-sm-3">角色</label>
                <div class="col-sm-6">
                  <select class="form-control" name="role">
                   <option value="contributor" ${user.role=='contributor'?'selected':''}>投稿者</option>
                   <option value="editor" ${user.role=='editor'?'selected':''}>编辑</option>
                   <option value="admin" ${user.role=='admin'?'selected':''}>管理员</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                 <label class="col-sm-3" for="description">备注</label>
                 <div class="col-sm-6">
                    <textarea rows="3" class="form-control" name="description">${user.description}</textarea>
                 </div>
               </div>
              <div class="form-group" style="padding-top: 20px;">
                <div class="col-sm-offset-3 col-sm-2">
                  <button type="submit" class="btn btn-primary btn-block">添加新用户</button>
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

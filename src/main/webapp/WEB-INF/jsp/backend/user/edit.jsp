<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
  <%@include file="../../common/bootstrap.html" %>
 </head>
 <body style="margin-top: 50px;">
  <%@include file="../common/navbar.html" %>
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-3 col-md-2" id="sidebar" style="padding: 0;">
         <%@include file="../common/sidebar.html" %>
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
              <input type="hidden" name="_method" value="PUT" />
              <div class="form-group">
                <label class="col-sm-3" for="title">*用户名</label>
                <div class="col-sm-6">
                  <input class="form-control" type="text" name="nickName" />
                </div>
              </div>
               <div class="form-group">
                 <label class="col-sm-3" for="email">*电子邮件</label>
                 <div class="col-sm-6">
                   <input class="form-control" type="text" name="email">
                 </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3" for="realName">真实名称</label>
                 <div class="col-sm-6">
                    <input class="form-control" autocomplete="off" type="text" name="realName">
                  </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3" for="email">*密码 </label>
                 <div class="col-sm-6">
                   <input class="form-control input-sm" type="password" name="pass" />
                   <input style="margin-top: 5px;" class="form-control input-sm" type="password" name="repass" />
                 </div>
               </div>
              <div class="form-group">
                <label class="col-sm-3">角色</label>
                <div class="col-sm-6">
                  <select class="form-control">
                   <option>订阅者</option><option>编辑</option>
                   <option>作者</option><option>投稿者</option>
                   <option>管理员</option>
                  </select>
                </div>
              </div>
              <div class="form-group">
                 <label class="col-sm-3" for="description">备注</label>
                 <div class="col-sm-6">
                    <textarea rows="3" class="form-control" name="description"></textarea>
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

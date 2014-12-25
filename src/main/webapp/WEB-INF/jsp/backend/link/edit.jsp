<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
          <li>链接</li>
          <li class="active">编辑链接</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>内容</div>
          <div class="panel-body">
            <form action="." method="post" class="form-horizontal text-left" role="form">
              <c:if test="${link!=null}">
                <input type="hidden" name="_method" value="PUT" />
              </c:if>
              <div class="form-group">
                <label class="col-sm-3" for="maxshow">站点名称</label>
                <div class="col-sm-6">
                  <input class="form-control" autocomplete="off" type="text" name="name" value="${link.name}" />
                  <p class="help-block">例如：好用的博客软件</p>
                </div>
              </div>
               <div class="form-group">
                 <label class="col-sm-3" for="defaultType">站点url</label>
                 <div class="col-sm-6">
                    <input class="form-control" autocomplete="off" type="text" name="url" value="${link.url}" placeholder="http://" />
                    <p class="help-block">不要忘了 http://</p>
                 </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3" for="defaultType">备注</label>
                 <div class="col-sm-6">
                    <textarea rows="3" class="form-control">${link.notes}</textarea>
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

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
          <li>系统设置</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>撰写/阅读</div>
          <div class="panel-body">
            <form action="" method="post" class="form-horizontal text-left" role="form">
              <input type="hidden" name="_method" value="PUT" />
              <div class="form-group">
                <label class="col-sm-3" for="maxshow">博客页面至多显示(文章数/页)</label>
                <div class="col-sm-6">
                  <input class="form-control" type="text" value="10" />
                </div>
              </div>
               <div class="form-group">
                 <label class="col-sm-3" for="defaultType">默认文章分类目录</label>
                 <div class="col-sm-6">
                   <select class="form-control">
                    <c:forEach var="category" items="${categorys}" begin="1">
                      <option value="${category.id}">├─<c:if test="${category.level==3}">└─</c:if>${category.text}</option>
                    </c:forEach>
                  </select>
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

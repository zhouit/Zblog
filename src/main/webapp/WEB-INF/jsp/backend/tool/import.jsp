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
          <li>数据导入</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>WordPress数据导入</div>
          <div class="panel-body">
            <c:if test="${success}">
              <div class="alert alert-success" style="padding: 10px 15px;">导入成功</div>
            </c:if>
            <form action="import" method="post" class="form-horizontal text-left" enctype="multipart/form-data" role="form">
              <div class="well">支持导入wordpress的文章及附件数据</div>
              <c:if test="${msg!=null}">
                <div class="alert alert-warning">${msg}</div>
              </c:if>
              <div class="form-group">
                <label class="col-sm-3">*wordpress</label>
                <div class="col-sm-6">
                  <input id="wordpress" name="wordpress" type="file" style="display:none" accept="text/xml" onchange="$('#up-helper').val($(this).val())" />
                 <div class="input-group">
                   <input id="up-helper" type="text" class="form-control" placeholder="xml" />
                   <span class="input-group-addon" onclick="$('#wordpress').click();">选择文件</span>
                 </div>
                </div>
              </div>
              <div class="form-group" style="padding-top: 20px;">
                <div class="col-sm-offset-3 col-sm-2">
                  <button type="submit" class="btn btn-primary btn-block">导入数据</button>
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

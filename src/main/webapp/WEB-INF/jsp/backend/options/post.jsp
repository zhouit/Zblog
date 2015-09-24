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
          <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>撰写/阅读</div>
          <div class="panel-body">
            <c:if test="${success}">
              <div class="alert alert-success" style="padding: 10px 15px;">修改成功</div>
            </c:if>
            <form action="post" method="post" class="form-horizontal text-left" role="form">
              <div class="form-group ${maxshow!=null?'has-error':''}">
                <label class="col-sm-3" for="maxshow">*博客页面至多显示(文章数/页)</label>
                <div class="col-sm-6">
                  <input class="form-control" type="number" name="maxshow" value="${form.maxshow}" />
                  <p class="help-block">${maxshow}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3">评论设置</label>
                <div class="col-sm-6">
                  <div class="checkbox">
                    <label style="padding-left: 20px;" ><input type="checkbox" name="allowComment" ${form.allowComment?'checked':''}/>允许他人在新文章上发表评论 </label>
                  </div>
                </div>
              </div>
              <div class="form-group ${defaultCategory!=null?'has-error':''}">
                <label class="col-sm-3" for="defaultType">*默认文章分类目录</label>
                <div class="col-sm-6">
                  <select class="form-control" name="defaultCategory">
                    <c:if test="${form.defaultCategory==null}">
                       <option>请选择</option>
                    </c:if>
                   <c:forEach var="category" items="${categorys}" begin="1">
                     <option value="${category.id}" ${form.defaultCategory==category.id?'selected':''} >
                       <c:if test="${category.level==3}">└─</c:if>${category.name}
                     </option>
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

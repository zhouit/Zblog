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
          <li>缓存监控</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-wrench"></span>缓存列表</div>
          <div class="panel-body">
            <table class="table table-striped list-table">
              <thead><tr>
                 <th>缓存名称</th>
                 <th>平均获取时间(ms)</th>
                 <th>命中次数</th>
                 <th>未命中数</th>
                 <th>缓存对象数</th>
                 <th>命中率</th>
                 <th class="center">操作</th>
               </tr></thead>
               <tbody>
               <c:forEach items="${caches}" var="cache">
                 <tr><td>${cache.name}</td><td>${cache.averageGetTime}</td>
                      <td>${cache.hitCount}</td><td>${cache.missCount}</td>
                      <td>${cache.size}</td><td>${cache.hitRatio}</td>
                      <td class="center">
                         <span class="glyphicon glyphicon-trash pointer" title="清空缓存"></span>
                      </td></tr>
               </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
     </div>
  </div>
 </body>
</html>

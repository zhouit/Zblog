<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="z" uri="/WEB-INF/tld/function.tld" %>
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
          <li class="active">用户</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>用户列表</div>
          <div class="panel-body">
           <table id="post-table" class="table table-striped list-table">
               <thead><tr>
                 <th>用户名</th>
                 <th>姓名</th>
                 <th>电子邮件</th>
                 <th>角色</th>
                 <th>文章</th>
                 <th>创建日期</th>
                 <th class="center">操作</th>
               </tr></thead>
              <tbody>
               <c:forEach items="${page.content}" var="user">
                 <tr><td>${user.nickName}</td><td>${user.realName}</td>
                      <td><a href="mailto:${user.email}">${user.email}</a></td><td>${user.role}</td><td>...</td>
                      <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/></td>
                     <td class="center"><span class="icon glyphicon glyphicon-pencil pointer" onclick="zblog.user.edit('${user.id}')"></span>
                       <c:if test="${z:cookieValue('un')!=user.nickName}">
                         <span class="glyphicon glyphicon-trash pointer" onclick="zblog.user.remove('${user.id}')"></span>
                       </c:if>
                     </td></tr>
               </c:forEach>
              </tbody>
            </table>
            <div class="row-fulid">
              <div class="col-sm-6 col-md-6">
                <div class="page-info">第 ${page.pageIndex}页, 共 ${page.totalPage}页, ${page.totalCount} 项</div>
              </div>
              <div class="col-sm-6 col-md-6">
                <div id="pager">
                  <jsp:include page="../common/pagination.jsp" flush="false" />
              </div>
            </div>
          </div>
         </div>
       </div>

      </div>
    </div>
  </div>
  <script type="text/javascript" src="${g.domain}/resource/js/backend/admin.user.js"></script>
 </body>
</html>

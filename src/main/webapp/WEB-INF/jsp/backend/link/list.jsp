<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="page" uri="/WEB-INF/tld/pagination.tld" %>

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
          <li class="active">链接</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>链接列表</div>
          <div class="panel-body">
           <table id="post-table" class="table table-striped list-table">
               <thead><tr>
                 <th>名称</th>
                 <th>站点</th>
                 <th>可见性</th>
                 <th>创建时间</th>
                 <th class="center">操作</th>
               </tr></thead>
              <tbody>
               <c:forEach items="${page.content}" var="link">
                 <tr><td>${link.name}</td><td><a target="_blank" href='${link.url}'>${link.url}</a></td>
                     <td>${link.visible?"是":"否"}</td><td><fmt:formatDate value="${link.createTime}" pattern="yyyy-MM-dd"/></td>
                     <td class="center"><span class="icon glyphicon glyphicon-pencil"></span>
                       <span class="glyphicon glyphicon-trash"></span></td></tr>
               </c:forEach>
              </tbody>
            </table>
            <div class="row-fulid">
              <div class="col-sm-6 col-md-6">
                <div class="page-info">显示第 ${page.pageIndex} 至  ${page.totalPage}项记录，共 ${page.totalCount} 项</div>
              </div>
              <div class="col-sm-6 col-md-6">
                <div id="pager">
                   <ul class="pagination">
                     <page:page model="${page}" pageUrl="." showPage="8">
                       <page:prev>
                          <li><a href="${pageUrl}"><span aria-hidden="true">&laquo;</span></a></li>
                       </page:prev>
                       <page:currentLeft>
                          <li><a href="${pageUrl}">${pageNumber}</a></li>
                       </page:currentLeft>
                       <page:current>
                          <li><a href="#">${pageNumber}</a></li>
                       </page:current>
                       <page:currentRight>
                          <li><a href="${pageUrl}">${pageNumber}</a></li>
                       </page:currentRight>
                       <page:next>
                          <li><a href="${pageUrl}"><span aria-hidden="true">&raquo;</span></a></li>
                       </</page:next>
                     </page:page>
                   </ul>
              </div>
            </div>
          </div>
         </div>
       </div>

      </div>
    </div>
  </div>
 </body>
</html>

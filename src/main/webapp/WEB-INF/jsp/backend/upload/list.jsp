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
          <li class="active">多媒体</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>媒体库</div>
          <div class="panel-body">
           <table id="upload-table" class="table table-striped list-table">
               <thead><tr>
                 <th style="width: 80px;">文件</th>
                 <th style="width: 30%"></th>
                 <th>作者</th>
                 <th>上传至</th>
                 <th>日期</th>
                 <th class="center">操作</th>
               </tr></thead>
              <tbody>
               <c:forEach items="${page.content}" var="upload">
                 <tr><td><img src="${upload.path}" width="80" height="50" /></td>
                     <td class="filename"><strong>${upload.name}</strong><p class="fileformat">${z:fileExt(upload.name)}</p>
                        <div class="row-action">
                          <span><a href="javascript:zblog.upload.remove('${upload.id}');">永久删除</a>&nbsp;|&nbsp;</span>
                          <span><a href="#">查看</a></span>
                        </div></td>
                     <td>${upload.user.nickName}</td><td><a href='${g.domain}/posts/${upload.post.id}' target="_blank">${upload.post.title}</a></td>
                     <td><fmt:formatDate value="${upload.createTime}" pattern="yyyy-MM-dd"/></td>
                     <td class="center">
                       <span class="glyphicon glyphicon-trash pointer" onclick="zblog.upload.remove('${upload.id}');"></span></td></tr>
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
  <script type="text/javascript" src="${g.domain}/resource/js/backend/admin.upload.js"></script>
 </body>
</html>

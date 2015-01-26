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
          <li class="active">留言</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>留言列表</div>
          <div class="panel-body">
           <table id="post-table" class="table table-striped list-table">
               <thead><tr>
                 <th style="width: 20%;">作者</th>
                 <th>评论</th>
                 <th style="width: 15%;">回应给</th>
                 <th class="center">操作</th>
               </tr></thead>
              <tbody>
               <c:forEach items="${page.content}" var="comment">
                 <tr><td><strong><img class="avatar" src='../../resource/img/avatar.png' width="32" height="32" />
                        ${comment.creator}</strong><br/>
                        <a href="${comment.url}">${z:substring(comment.url, 7)}</a><br/>
                        <a href="mailto:${comment.email}">${comment.email}</a></br/>
                        <a href="javascript:void();">${comment.ip}</a></td>
                      <td><div>提交于<a href="${g.domain}/posts/${comment.postid}/#comment-${comment.id}">
                            <fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd ahh:mm"/></a></div>
                          <p style="margin: 7px 0;">${comment.content}</p>
                          <div class="row-action">
                             <span class="edit"><a href="javascript:zblog.comment.approve('${comment.id}');">${comment.approved?'驳回':'批准'}</a>&nbsp;|&nbsp;</span>
                             <span class="edit"><a href="#">垃圾评论</a>&nbsp;|&nbsp;</span>
                             <span class="trash"><a href="#">移动到回收站</a></span></div>
                      </td>
                      <td><a href="${g.domain}/posts/${comment.postid}" target="_blank">${comment.title}</a><br /></td>
                      <td class="center"><span class="glyphicon glyphicon-trash pointer" onclick="zblog.comment.remove('${comment.id}')"></span></td>
                   </tr>
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
  <script type="text/javascript" src="${g.domain}/resource/js/backend/admin.comment.js"></script>
 </body>
</html>

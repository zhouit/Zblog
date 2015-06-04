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
            <ul class="table-nav">
              <li><a class="${type=='all'?'active':''}" href="?type=all">全部</a> | </li>
              <li><a class="${type=='wait'?'active':''}" href="?type=wait">待审
               <c:if test="${stat.wait!=null}"><span>(${stat.wait})</span></c:if> | </a>
              </li>
              <li><a class="${type=='approve'?'active':''}" href="?type=approve">获准
               <c:if test="${stat.approve!=null}"><span>(${stat.approve})</span></c:if> | </a>
              </li>
              <li><a class="${type=='trash'?'active':''}" href="?type=trash">垃圾评论
               <c:if test="${stat.trash!=null}"><span>(${stat.trash})</span></c:if></a>
              </li>
            </ul>
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
                        <a href="${comment.url}">${z:getDomainLink(comment.url)}</a><br/>
                        <a href="mailto:${comment.email}">${comment.email}</a></br/>
                        <a href="#">${comment.ip}</a></td>
                      <td><div>提交于<a href="${g.domain}/posts/${comment.postid}/#comment-${comment.id}" target="_blank">
                            <fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd ahh:mm"/></a>
                            <c:if test="${comment.pid!=null}">| 回复给 
                              <a href="${g.domain}/posts/${comment.postid}/#comment-${comment.pid}" target="_blank">${comment.pcreator}</a>
                            </c:if>
                           </div>
                          <p style="margin: 7px 0;">${comment.content}</p>
                          <div class="row-action">
                            <c:if test="${type=='all'}">
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','${comment.status=='wait'?'approve':'wait'}');">${comment.status=='wait'?'批准':'驳回'}</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','reject');">垃圾评论</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','trash');">移动到回收站</a></span> 
                            </c:if>
                            <c:if test="${type=='wait'}">
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','approve');">批准</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','wait');">驳回</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','reject');">垃圾评论</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','trash');">移动到回收站</a></span> 
                            </c:if>
                            <c:if test="${type=='approve'}">
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','wait');">驳回</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','reject');">垃圾评论</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','trash');">移动到回收站</a></span> 
                            </c:if>
                            <c:if test="${type=='reject'}">
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','approve');">批准</a>&nbsp;|&nbsp;</span>
                             <span><a href="#" onclick="zblog.comment.approve('${comment.id}','wait');">还原</a>&nbsp;|&nbsp;</span>
                            </c:if>
                          </div>
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

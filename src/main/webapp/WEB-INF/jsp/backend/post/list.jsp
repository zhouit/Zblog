<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="page" uri="/WEB-INF/tld/pagination.tld" %>
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
          <li class="active">文章</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>文章列表</div>
          <div class="panel-body">
           <table id="post-table" class="table table-striped list-table">
               <thead><tr>
                 <th style="width: 35%;">标题</th>
                 <th>作者</th>
                 <th>分类</th>
                 <th>日期</th>
                 <th>Views</th>
                 <th class="center">操作</th>
               </tr></thead>
              <tbody>
               <c:forEach items="${page.content}" var="post">
                 <tr id="edit-${post.id}">
                    <td><strong><a class="post-title" target="_blank" href="../../posts/${post.id}">${post.title}</a>
                        <c:if test="${post.pstatus=='secret'}"> - 私密</c:if>
                     </strong>
                     <span class="hide"><i class="post-ps">${post.pstatus}</i>
                          <i class="post-cs">${post.cstatus}</i><i class="post-tags">${z:join(post.tags,',')}</i></span>
                     <div class="row-action">
                       <span><a href="#">编辑</a>&nbsp;|&nbsp;</span>
                       <span><a href="#" onclick="zblog.post.fastedit('${post.id}')">快速编辑</a>&nbsp;|&nbsp;</span>
                       <span><a href="#">移到回收站</a>&nbsp;|&nbsp;</span>
                       <span><a target="_blank" href="${g.domain}/pages/${post.id}">查看</a></span>
                     </div></td><td class="post-author">${post.user.nickName}</td>
                     <td class="post-category">${post.category.name}</td>
                     <td class="post-ctime"><fmt:formatDate value="${post.createTime}" pattern="yyyy-MM-dd" /></td>
                     <td>${post.rcount} views</td>
                     <td class="center"><span class="icon glyphicon glyphicon-pencil pointer" onclick="zblog.post.edit('${post.id}')"></span>
                       <span class="glyphicon glyphicon-trash pointer" onclick="zblog.post.remove('${post.id}')"></span></td>
                  </tr>
               </c:forEach>
               <tr id="edit-row">
                 <td colspan="6">
                  <div class="edit-col-left">
                    <h5>快速编辑</h5>
                    <span><label>标题</label><input type="text" name="title" autocomplete="off"></span>
                    <span><label>日期</label><input type="text" name="createTime" readonly="readonly"></span>
                    <span><label>作者</label><label class="er-author">admin</label></span>
                    <span><label>公开度</label><input type="radio" name="pstatus" value="publish" >公开&nbsp;
                          <input type="radio" name="pstatus" value="secret" >隐藏</span>
                  </div>
                  <div class="edit-col-center">
                    <h5>分类目录</h5>
                    <ul>
                     <c:forEach items="${categorys}" var="category" begin="1">
                       <li><input type="radio" name="category" value="${category.id}" />${category.name}</li>
                     </c:forEach>
                    </ul>
                  </div>
                  <div class="edit-col-right">
                    <h5>标签</h5>
                    <textarea autocomplete="off" id="tags"></textarea>
                    <span><label>允许评论</label><input type="radio" name="cstatus" value="open">是&nbsp;
                           <input type="radio" name="cstatus" value="close">否</span>
                  </div>
                  <p>
                    <button class="btn btn-default btn-sm" onclick="zblog.post.canclefast();">取消</button>
                    <button style="float:right;" class="btn btn-primary btn-sm" onclick="zblog.post.submitfast();">更新</button>
                  </p>
                 </td>
               </tr>
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
  <script type="text/javascript" src="${g.domain}/resource/js/backend/admin.post.js"></script>
 </body>
</html>

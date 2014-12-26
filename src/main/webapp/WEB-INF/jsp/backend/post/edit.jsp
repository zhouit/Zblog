<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<!DOCTYPE Html>
<html>
 <head>
  <%@include file="../../common/bootstrap.html" %>
  <script type="text/javascript" src="${domain}/resource/ueditor-1.4.3/ueditor.config.js"></script>
  <script type="text/javascript" src="${domain}/resource/ueditor-1.4.3/ueditor.all.js"></script>
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
          <li>文章</li>
          <li class="active">编辑文章</li>
        </ol>
       <div class="row">
         <div class="col-sm-9 .col-md-9">
          <div class="panel panel-default">
            <div class="panel-heading"><span class="icon glyphicon glyphicon-edit"></span>标题/内容</div>
            <div class="panel-body">
              <input type="text" id="title" class="form-control input-md" placeholder="输入标题"><br/>
              <!-- 必须要添加width:100% -->
              <script id="container" style="width: 100%; height: 300px;" name="content" type="text/plain"></script>
            </div>
           </div>
         </div>
         <div class="col-sm-3 .col-md-3">
           <div class="panel panel-default">
             <div class="panel-heading">发布</div>
             <div class="panel-body">
               <div class="form-group">
                 <label for="categoty">分类</label>
                 <select class="form-control" id="category">
                   <c:forEach items="${categorys}" var="category" begin="1">
                     <option value="${category.id}">├─<c:if test="${category.level==3}">└─</c:if>${category.text}</option>
                   </c:forEach>
<!--                    <option value="1">├─Java</option>
                   <option value="2">├─└─JavaSE</option>
                   <option value="3">├─└─JavaEE</option>
                   <option value="4">├─└─JavaFX</option>
                   <option value="5">├─DataBase</option> -->
                 </select>
               </div>
               <div class="form-group">
                 <label>公开度</label><br/>
                 <label class="radio-inline">
                    <input type="radio" name="visual" value="open" checked="checked">公开
                 </label>
                 <label class="radio-inline">
                    <input type="radio" name="visual" value="open">隐藏
                 </label>
               </div>
               <div class="form-group">
                 <label for="mark">标签</label>
                 <input type="text" class="form-control" id="mark" />
                 <span class="help-block">多个标签请用英文逗号（,）分开</span>
               </div>
             </div>
             <div class="panel-footer">
               <button type="button" class="btn btn-info btn-block" onclick="zblog.post.insert();">发布</button>
             </div>
           </div>
         </div>
       </div>

      </div>
    </div>
  </div>
  <script type="text/javascript" src="${domain}/resource/js/backend/edit.js"></script>
 </body>
</html>

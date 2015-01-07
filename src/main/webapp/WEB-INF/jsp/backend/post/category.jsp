<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
  <jsp:include page="../common/bootstrap.jsp" flush="false" />
  <link rel="stylesheet" href="${g.domain}/resource/zdialog/alert.css" type='text/css'>
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
          <li class="active">文章分类</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>分类列表</div>
          <div class="panel-body">
             <div class="btn-group" style="margin-bottom: 10px;">
               <button type="button" class="btn btn-default" onclick=" zdialog.show('insert-box');">
                 <span class="icon glyphicon glyphicon-plus" aria-hidden="true"></span>添加
               </button>
               <button type="button" class="btn btn-default" onclick="zblog.category.remove();">
                 <span class="icon glyphicon glyphicon-minus" aria-hidden="true"></span>删除
               </button>
               <button type="button" class="btn btn-default" onclick="zblog.category.edit();">
                 <span class="icon glyphicon glyphicon-indent-left" aria-hidden="true"></span>修改
               </button>
             </div>
             <div class="well well-sm">选中父分类名称进行添加</div>
             <div id="tree" ></div>
          </div>
       </div>
      </div>
    </div>
  </div>
  
   <div id="insert-box" class="zdialog">
     <div class="zheader">
       <h3 class="title">添加分类</h3>
       <a class="toclose" title="关闭" href="#" onclick="zdialog.hide('insert-box');">关闭</a>
     </div>
     <div class="zcontent clearfix">
       <label for="name" style="float: left;line-height: 32px;margin-right: 15px;">分类名称:</label>
       <input class="form-control" type="text" id="newCategory" style="width: 230px;"  />
     </div>
     <div class="zfooter">
       <button type="button" class="btn btn-default">取消</button>
        <button type="button" class="btn btn-primary" onclick="zblog.category.insert();">确定</button>
     </div>
   </div>
  
  <script type="text/javascript" src="${g.domain}/resource/bootstrap-tree/bootstrap-treeview.min.js"></script>
  <script type="text/javascript" src="${g.domain}/resource/js/backend/admin.category.js"></script>
  <script type="text/javascript" src="${g.domain}/resource/zdialog/alert.js"></script>
 </body>
</html>

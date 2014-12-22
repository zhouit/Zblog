<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
          <li class="active">文章</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-list"></span>文章列表</div>
          <div class="panel-body">
           <table id="post-table" class="table table-striped table-bordered">
               <thead><tr>
                 <th>标题</th>
                 <th>作者</th>
                 <th>分类</th>
                 <th>日期</th>
                 <th class="center">操作</th>
               </tr></thead>
              <tbody>
               <tr>
                 <td>数据库表的行列转换</td>
                 <td>admin</td>
                 <td>DataBase</td>
                 <td>2014-11-24</td>
                 <td class="center"><span class="icon glyphicon glyphicon-pencil"></span>
                    <span class="glyphicon glyphicon-trash"></span></td>
               </tr>
               <tr>
                 <td>数据库表的行列转换</td>
                 <td>admin</td>
                 <td>DataBase</td>
                 <td>2014-11-24</td>
                 <td class="center"><span class="icon glyphicon glyphicon-pencil"></span>
                    <span class="glyphicon glyphicon-trash"></span></td>
               </tr>
              </tbody>
            </table>
            <div class="row-fulid">
              <div class="col-sm-6 col-md-6">
                <div class="page-info">显示第 1 至 10 项记录，共 57 项</div>
              </div>
              <div class="col-sm-6 col-md-6">
                <div id="pager">
                   <ul class="pagination">
                     <li><a href="#"><span aria-hidden="true">&laquo;</span></a></li>
                     <li><a href="#">1</a></li>
                     <li><a href="#">2</a></li>
                     <li><a href="#">3</a></li>
                     <li><a href="#">4</a></li>
                     <li><a href="#">5</a></li>
                     <li><a href="#"><span aria-hidden="true">&raquo;</span></a></li>
                   </ul>
              </div>
            </div>
          </div>
         </div>
       </div>

      </div>
    </div>
  </div>
  <script type="text/javascript" src="http://www.zhc.com/resource/js/backend/edit.js"></script>
 </body>
</html>

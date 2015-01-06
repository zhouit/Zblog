<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
  <%@include file="../../common/bootstrap.html" %>
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
          <li>系统设置</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>常规</div>
          <div class="panel-body">
            <form action="" method="post" class="form-horizontal text-left" role="form">
              <input type="hidden" name="CSRFToken" value="${CSRFToken}" />
              <input type="hidden" name="_method" value="PUT" />
              <div class="form-group">
                <label class="col-sm-3" for="title">站点标题</label>
                <div class="col-sm-6">
                  <input placeholder="站点标题" class="form-control" type="text" />
                </div>
              </div>
               <div class="form-group">
                 <label class="col-sm-3" for="subtitle">副标题</label>
                 <div class="col-sm-6">
                   <input placeholder="副标题" class="form-control" type="text">
                   <p class="help-block">用简洁的文字描述本站点。</p>
                 </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3" for="weburl">站点地址（URL）</label>
                 <div class="col-sm-6">
                    <input placeholder="http://" class="form-control" type="text">
                  </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3" for="email">电子邮件地址 </label>
                 <div class="col-sm-6">
                   <input class="form-control" type="text">
                   <p class="help-block">这个电子邮件地址仅为了管理方便而索要，例如新注册用户通知。</p>
                 </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3">成员资格</label>
                 <div class="col-sm-6">
                   <div class="checkbox">
                     <label style="padding-left: 20px;" ><input type="checkbox" name="defaultReg">任何人都可以注册</label>
                   </div>
                 </div>
               </div>
              <div class="form-group">
                <label class="col-sm-3">新用户默认角色</label>
                <div class="col-sm-6">
                  <select class="form-control">
                   <option>订阅者</option><option>编辑</option>
                   <option>作者</option><option>投稿者</option>
                   <option>管理员</option>
                  </select>
                </div>
              </div>
              <div class="form-group" style="padding-top: 20px;">
                <div class="col-sm-offset-3 col-sm-2">
                  <button type="submit" class="btn btn-primary btn-block">保存更改</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
 </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
          <li>系统设置</li>
        </ol>
        <div class="panel panel-default">
          <div class="panel-heading"><span class="icon glyphicon glyphicon-briefcase"></span>常规</div>
          <div class="panel-body">
            <c:if test="${success}">
              <div class="alert alert-success" style="padding: 10px 15px;">修改成功</div>
            </c:if>
            <form action="" method="post" class="form-horizontal text-left" role="form">
              <div class="form-group ${title!=null?'has-error':''}">
                <label class="col-sm-3" for="title">*站点标题</label>
                <div class="col-sm-6">
                  <input placeholder="站点标题" name="title" class="form-control" type="text" value="${form.title}" />
                  <p class="help-block">${title}</p>
                </div>
              </div>
               <div class="form-group ${subtitle!=null?'has-error':''}">
                 <label class="col-sm-3" for="subtitle">*副标题</label>
                 <div class="col-sm-6">
                   <input placeholder="副标题" name="subtitle" class="form-control" type="text" value="${form.subtitle}" />
                 </div>
               </div>
               <div class="form-group ${description!=null?'has-error':''}">
                 <label class="col-sm-3" for="description">站点描述</label>
                 <div class="col-sm-6">
                   <input placeholder="站点描述" name="description" class="form-control" type="text" value="${form.description}" />
                   <p class="help-block">${description!=null?description:'用简洁的文字描述本站点。'}</p>
                 </div>
               </div>
               <div class="form-group ${keywords!=null?'has-error':''}">
                 <label class="col-sm-3" for="keywords">站点keywords</label>
                 <div class="col-sm-6">
                   <input placeholder="keywords" name="keywords" class="form-control" type="text" value="${form.keywords}" />
                   <p class="help-block">${keywords!=null?keywords:'填写本站的关键字。'}</p>
                 </div>
               </div>
               <div class="form-group ${weburl!=null?'has-error':''}">
                 <label class="col-sm-3" for="weburl">*站点地址（URL）</label>
                 <div class="col-sm-6">
                    <input placeholder="http://" name="weburl" class="form-control" type="text" value="${form.weburl}">
                    <p class="help-block">${weburl}</p>
                  </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3" for="email">电子邮件地址 </label>
                 <div class="col-sm-6">
                   <input class="form-control" type="text" name="email" />
                   <p class="help-block">这个电子邮件地址仅为了管理方便而索要，例如新注册用户通知。</p>
                 </div>
               </div>
               <div class="form-group">
                 <label class="col-sm-3">成员资格</label>
                 <div class="col-sm-6">
                   <div class="checkbox">
                     <label style="padding-left: 20px;" >
                       <input type="checkbox" name="enableReg" readonly="readonly">任何人都可以注册</label>
                   </div>
                 </div>
               </div>
              <div class="form-group">
                <label class="col-sm-3">新用户默认角色</label>
                <div class="col-sm-6">
                  <select class="form-control" name="defaultUserRole">
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

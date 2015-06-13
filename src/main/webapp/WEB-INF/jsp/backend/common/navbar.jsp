<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="z" uri="/WEB-INF/tld/function.tld" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fulid" style="padding-right: 15px;">
    <div class="navbar-header"><a class="navbar-brand" target="_blank" href="${g.domain}">Zblog</a></div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a class="dropdown-toggle" data-toggle="dropdown" href="#">新建<span class="caret"/></a>
          <ul class="dropdown-menu">
             <li><a href="${g.domain}/backend/posts/edit">文章</a></li>
             <li><a href="${g.domain}/backend/links/edit">链接</a></li>
             <li><a href="${g.domain}/backend/users/edit">用户</a></li>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Dashboard</a></li>
        <li><a class="dropdown-toggle" data-toggle="dropdown" href="#">你好,${z:cookieValue('un')}<span class="caret"></span></a>
          <ul class="dropdown-menu">
             <li><a href="${g.domain}/backend/options/general">设置</a></li>
             <li><a href="${g.domain}/backend/users/my">编辑我的个人资料</a></li>
             <li><a href="${g.domain}/backend/logout">退出</a></li>
          </ul>
        </li>
      </ul>
      <form action="#" class="navbar-form navbar-right">
        <input type="text" autocomplete="off" placeholder="Search..." class="form-control" />
      </form>
    </div>
  </div>
</nav>
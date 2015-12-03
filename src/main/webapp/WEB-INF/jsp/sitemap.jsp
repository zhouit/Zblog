<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE Html>
<html>
<head profile="http://gmpg.org/xfn/11">
  <title>站点地图 - ${g.title}</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="description" content="${g.description}" />
  <meta name="keywords" content="${g.keywords}" />
  <style type="text/css">
   body {font-family: Verdana;font-size: 12px;margin: 0;color: #000000;background: #ffffff;}
  img {border:0;}
  li {margin-top: 8px;}
  .page {padding: 4px; border-top: 1px #EEEEEE solid}
  .author {background-color:#EEEEFF; padding: 6px; border-top: 1px #ddddee solid}
  #nav, .content, #footer {padding: 8px; border: 1px solid #EEEEEE; clear: both; width: 95%; margin: auto; margin-top: 10px;}
</style>
</head>
<body vlink="#333333" link="#333333">
  <h2 style="text-align: center; margin-top: 20px">${g.title} - blog.zhouhaocheng.cn's SiteMap </h2>
  <center></center>
  <div id="nav">
     <a href="${g.domain}"><strong>${g.title} - blog.zhouhaocheng.cn</strong></a>  &raquo; 
     <a href="${g.domain}/sitemap.html">站点地图</a>
  </div>
  <div class="content">
    <h3>最新文章</h3>
    <ul>
     <c:forEach items="${posts}" var="post">
       <li><a target="_blank" href="/posts/${post.id}">${post.title}</a></li>
     </c:forEach>
    </ul>
  </div>
  <div class="content"></div>
  <div id="footer">查看博客首页: <strong><a href="${g.domain}">${g.title} - blog.zhouhaocheng.cn</a></strong></div><br>
</body>
</html>

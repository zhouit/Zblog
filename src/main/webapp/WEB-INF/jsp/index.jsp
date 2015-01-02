<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="page" uri="/WEB-INF/tld/pagination.tld" %>
<!DOCTYPE Html>
<html>
 <head>
   <%@include file="common/head.html" %>
 </head>
 <body class="custom-background">
   <%@include file="common/header.html" %>
   <div id="content" class="clearfix">
     <div id="left_col">
       <c:if test="${categoryName!=null}">
         <div id="archive_headline"><h2>'<span style="color: #00a19e;">${categoryName}</span>'目录归档</h2></div>
       </c:if>
       <c:forEach items="${page.content}" var="post">
       <div class="post_wrap clearfix">
         <div class="post">
           <h3 class="title"><a href="posts/${post.id}">${post.title}</a></h3>
           <div class="post_content">
             <p class="excerpt">${post.excerpt}...</p>
             <p><a class="more-link" href="${domain}/posts/${post.id}">阅读全文</a></p>
          </div>
         </div>
         <div class="meta">
           <ul>
             <li class="post_date clearfix">
              <span class="date">13</span><span class="month">Dec</span><span class="year">2014</span>
             </li>
             <li class="post_comment"> 41 人阅读</li>
             <li class="post_author">
               <a rel="author" title="由${post.creator}发布" href="${domain}/author/26793713/">${post.creator}</a>
             </li>
             <li class="post_comment">
                <a title="${post.title}的评论" href="${domain}/posts/${post.id}/#respond">发表评论</a>
             </li>
            </ul>
         </div>
       </div>
       </c:forEach>
       <div id="page_nav">
         <div class="clearfix page">
           <ul class="page-numbers">
<!--              <li><span class="current">1</span></li>
             <li><a href="#">2</a></li>
             <li><span class="dots">…</span></li>
              <li><a href="#">66</a></li>
              <li><a href="#">>></a></li> -->
              <page:page model="${page}" pageUrl="." showPage="8">
                <page:prev>
                  <li><a href="${pageUrl}"><<</a></li>
                </page:prev>
                <page:currentLeft>
                  <li><a href="${pageUrl}">${pageNumber}</a></li>
                </page:currentLeft>
                <page:current>
                  <li><span class="current">${pageNumber}</span></li>
                </page:current>
                <page:currentRight>
                  <li><a href="${pageUrl}">${pageNumber}</a></li>
                </page:currentRight>
                <page:next>
                  <li><a href="${pageUrl}">>></a></li>
                </page:next>
              </page:page>
           </ul>
         </div>
       </div>
     </div>
     <div id="post_mask"></div>
     <div id="right_col">
      <div class="side_widget clearfix">
       <h3 class="headline">About</h3>
       <p>You can show your site introduction by using Site Introduction Widget. 
       You also can show Social Icon on upper part, and search form at bottom. </p>
        <div id="search_area" class="clearfix">
          <form method="get">
             <div class="search_input">
               <input type="text" autocomplete="off" name="word" placeholder="搜索一下" />
             </div>
             <div class="search_button">
                <input type="submit" value="Search" />
             </div>
           </form>
         </div>
       </div>
       <%@include file="common/recent.html" %>
       <%@include file="common/link.html" %>
     </div>
     <%@include file="common/footer.html" %>
   </div>
 </body>
</html>

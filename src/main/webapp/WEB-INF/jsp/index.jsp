<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
         <div id="archvie_headline"><h2>'${categoryName}'目录归档</h2></div>
       </c:if>
       <div class="post_wrap clearfix">
         <div class="post">
           <h3 class="title"><a href="posts/20141238">《C#并发编程经典实例》—— 超时</a></h3>
           <div class="post_content">
             <p><strong>问题</strong><br>
                                       我们希望事件能在预定的时间内到达，即使事件不到达，也要确保程序能及时进行响应。<br>
                                      通常此类事件是单一的异步操作（例如，等待 Web 服务请求的响应）。
             </p>
             <p><a class="more-link" href="http://ifeve.com/timeout/#more-17716">阅读全文</a></p>
          </div>
         </div>
         <div class="meta">
           <ul>
             <li class="post_date clearfix">
              <span class="date">13</span><span class="month">Dec</span><span class="year">2014</span>
             </li>
             <li class="post_comment"> 41 人阅读</li>
             <li class="post_author">
               <a rel="author" title="由郭蕾发布" href="http://ifeve.com/author/26793713/">郭蕾</a>
             </li>
             <li class="post_comment">
                <a title="《《C#并发编程经典实例》—— 超时》上的评论" href="http://ifeve.com/timeout/#respond">发表评论</a>
             </li>
            </ul>
         </div>
       </div>
       
       <div class="post_wrap clearfix">
         <div class="post">
           <h3 class="title"><a href="posts/20141245">《C#并发编程经典实例》—— 超时</a></h3>
           <div class="post_content">
             <p><strong>问题</strong><br>
                                       我们希望事件能在预定的时间内到达，即使事件不到达，也要确保程序能及时进行响应。<br>
                                      通常此类事件是单一的异步操作（例如，等待 Web 服务请求的响应）。
             </p>
             <p><a class="more-link" href="http://ifeve.com/timeout/#more-17716">阅读全文</a></p>
          </div>
         </div>
         <div class="meta">
           <ul>
             <li class="post_date clearfix">
              <span class="date">13</span><span class="month">Dec</span><span class="year">2014</span>
             </li>
             <li class="post_comment"> 41 人阅读</li>
             <li class="post_author">
               <a rel="author" title="由郭蕾发布" href="http://ifeve.com/author/26793713/">郭蕾</a>
             </li>
             <li class="post_comment">
                <a title="《《C#并发编程经典实例》—— 超时》上的评论" href="http://ifeve.com/timeout/#respond">发表评论</a>
             </li>
            </ul>
         </div>
       </div>
       <div id="page_nav">
         <div class="clearfix page">
           <ul class="page-numbers">
             <li><span class="current">1</span></li>
             <li><a href="#">2</a></li>
             <li><span class="dots">…</span></li>
              <li><a href="#">66</a></li>
              <li><a href="#">>></a></li>
           </ul>
         </div>
       </div>
     </div>
     <div id="post_mask"></div>
     <div id="right_col">
      <div class="side_widget clearfix">
       <h3 class="headline">About</h3>
       <p>本站模仿自<a href="http://ifeve.com/">并发编程网</a></p>
        <div id="search_area" class="clearfix">
          <form method="get">
             <div class="search_input">
               <input type="text" autocomplete="off" name="s" placeholder="搜索一下" />
             </div>
             <div class="search_button">
                <input type="submit" value="Search" />
             </div>
           </form>
         </div>
       </div>
       <div class="side_widget clearfix">
         <h3 class="headline">近期文章</h3>
         <ul>
           <li><a href="http://ifeve.com/timeout/">《C#并发编程经典实例》—— 超时</a></li>
           <li><a href="http://ifeve.com/timeout/">《C#并发编程经典实例》—— 用限流和抽样抑制事件流</a></li>
           <li><a href="http://ifeve.com/timeout/">《C#并发编程经典实例》—— 超时</a></li>
         </ul>
       </div>
       <%@include file="common/link.html" %>
     </div>
     <%@include file="common/footer.html" %>
   </div>
 </body>
</html>

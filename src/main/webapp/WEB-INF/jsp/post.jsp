<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
   <%@include file="common/head.html" %>
 </head>
 <body class="custom-background">
   <%@include file="common/header.html" %>
   <div id="content" class="clearfix">
     <div id="left_col">
       <div id="bread_crumb">
         <ul class="clearfix">
           <li><a title="首页" href="http://www.zhc.com/">首页</a></li>
           <li><a href="http://www.zhc.com/categorys/JavaSE">JavaSE</a></li>
           <li class="last">${post.title}</li>
         </ul>
       </div>
       <div class="post_wrap clearfix">
         <div class="post">
           <h3 class="title"><span>${post.title}</span></h3>
           <div class="post_content">
             ${post.content}
          </div>
         </div>
         <div class="meta">
           <ul>
             <li class="post_date clearfix">
              <span class="date">13</span><span class="month">Dec</span><span class="year">2014</span>
             </li>
             <li class="post_comment"> 41 人阅读</li>
             <li class="post_author">
               <a rel="author" title="由${post.creator}发布" href="../author/26793713/">${post.creator}</a>
             </li>
             <li class="post_comment">
                <a title="${post.title}" href="#respond">发表评论</a>
             </li>
            </ul>
         </div>
       </div>
       <div id="comments_wrapper">
         <div id="comment_header" class="clearfix">
           <span class="comments_right">评论 (${post.ccount})</span>
         </div>
         <div id="comments">
           <div id="comment_area">
             <ol class="commentlist">
               <li class="comment even_comment">
                 <div class="comment-meta">
                   <img class="avatar" width="35" height="35" src="#">
                   <ul class="comment-name-date">
                     <li class="comment-name">
                       <a class="url" rel="external nofollow" href="#"> 天天备忘录 </a>
                     </li>
                     <li class="comment-date">2013/01/21 11:20下午</li>
                    </ul>
                   </div>
                   <div class="comment-content"><p>学习了很多东西！</p></div>
                 </li>
                 
               <li class="comment odd_comment">
                 <div class="comment-meta">
                   <img class="avatar" width="35" height="35" src="#">
                   <ul class="comment-name-date">
                     <li class="comment-name">
                       <a class="url" rel="external nofollow" href="#"> 天天备忘录 </a>
                     </li>
                     <li class="comment-date">2013/01/21 11:20下午</li>
                    </ul>
                   </div>
                   <div class="comment-content"><p>学习了很多东西！</p></div>
                 </li>
               </ol>
           </div>
           <%@include file="common/comments_form.html" %>
         </div>
       </div>
     </div>
     <div id="post_mask"></div>
     <div id="right_col">
      <div class="side_widget clearfix">
       <h3 class="headline">About</h3>
       <p>本站模仿自<a href="http://ifeve.com/">并发编程网</a></p>
        <div id="search_area">
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

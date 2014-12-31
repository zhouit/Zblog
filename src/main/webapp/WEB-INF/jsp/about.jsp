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
       <div class="post_wrap clearfix">
         <div class="post">
           <h3 class="title"><span>关于本站</span></h3>
           <div class="post_content">
             <p>欢迎光临并发编程网！并发编程网成立于2011年10月，从名字可以看出来，并发编程网创办之初是一个垂直性技术网站，
                                                致力于促进并发编程的研究和传播。随着这几年的发展，并发网组织翻译和原创了几百篇技术精品文章，包括Java，C++，开源框架和架构，
                                                所以并发网从最初致力于并发编程的研究和传播，进化成现在的致力于精品技术的研究和传播。2014年8月19日本站Alex世界排名四万名。中国排名3519名。
             </p>
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
            </ul>
         </div>
       </div>
       <div id="comments_wrapper">
         <div id="comment_header" class="clearfix">
           <span class="comments_right">评论 (39)</span>
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
          <form method="get" action="../">
             <div class="search_input">
               <input type="text" autocomplete="off" name="word" placeholder="搜索一下" />
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

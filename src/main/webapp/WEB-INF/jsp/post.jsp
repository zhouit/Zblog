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
       <jsp:include page="../../post/post-${post.id}.html" flush="false" />
      <%--  <%@include file="../../../post/post-201412.html" %> --%>
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
          <form method="get" action="${domain}" >
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE Html>
<html>
 <head>
   <jsp:include page="common/head.jsp" flush="false" />
 </head>
 <body>
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
                   <img class="avatar" width="35" height="35" src="../resource/img/avatar.png">
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
                   <img class="avatar" width="35" height="35" src="../resource/img/avatar.png">
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
           <jsp:include page="common/comments_form.jsp" flush="false" />
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
          <form method="get" action="${g.domain}">
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
     <jsp:include page="common/footer.jsp" flush="false" />
   </div>
 </body>
</html>

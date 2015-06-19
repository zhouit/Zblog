<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/shiro-function.tld" prefix="sf" %>
<c:if test="${post.type=='post'}">
<div id="bread_crumb">
  <ul class="clearfix">
   <li><a title="首页" href="${g.domain}">首页</a></li>
   <li><a href="${g.domain}/categorys/${post.category.name}">${post.category.name}</a></li>
   <li class="last">${post.title}</li>
  </ul>
</div>
</c:if>
<div class="post_wrap clearfix">
  <div class="post">
   <h3 class="title"><span>${post.title}</span></h3>
   <div class="post_content">
     <c:choose>
       <c:when test="${!sf:isUser()&&post.pstatus=='secret'}">由于作者设置了权限，你没法阅读此文，请与作者联系</c:when>
       <c:otherwise>
         ${post.content}
         <div style="margin-top: 15px; font-style: italic;">
           <p style="margin:0;"><strong>原创文章，转载请注明：</strong>转载自<a href="${g.domain}">${g.title} – zhouhaocheng.com</a></p>
         </div>
       </c:otherwise>
     </c:choose>
   </div>
  </div>
  <div class="meta">
    <ul>
     <li class="post_date clearfix">
       <span class="date"><fmt:formatDate value="${post.createTime}" pattern="dd" /></span>
       <span class="month"><fmt:formatDate value="${post.createTime}" pattern="MMM"/></span>
       <span class="year"><fmt:formatDate value="${post.createTime}" pattern="yyyy" /></span>
     </li>
     <li class="post_read">${post.rcount}人阅读</li>
     <c:if test="${post.type=='post'}">
       <li class="post_category"><a href="${g.domain}/categorys/${post.category.name}">${post.category.name}</a></li>
     </c:if>
     <li class="post_author">
       <a title="由${post.user.nickName}发布" href="#">${post.user.nickName}</a>
     </li>
     <li class="post_comment">
       <a title="${post.title}" href="#respond">发表评论</a>
     </li>
    </ul>
  </div>
</div>

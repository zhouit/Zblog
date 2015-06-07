<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<ol class="commentlist">
  <c:forEach items="${comments}" var="comment" varStatus="status">
   <c:choose>
    <c:when test="${depth==null||depth==0}">
     <li class="comment ${status.index%2==0?'even_comment':'odd_comment'}" id="comment-${comment.id}">
    </c:when>
    <c:otherwise>
      <li class="comment ${depth%2==(parent?0:1)?'even_comment':'odd_comment'}" id="comment-${comment.id}">
    </c:otherwise>
   </c:choose>
   <div class="comment-meta">
     <img class="avatar" width="35" height="35" src="../../resource/img/avatar.png" />
     <ul class="comment-name-date">
       <li class="comment-name"><a class="url" target="_blank" href="${comment.url}">${comment.creator}</a></li>
       <li class="comment-date"><fmt:formatDate value="${comment.createTime}" pattern="yyyy/MM/dd hh:mma"/></li>
      </ul>
      <c:if test="${comment.status=='approve'}"><div class="comment-reply"><a href='#respond'>回复</a></div></c:if>
     </div>
     <div class="comment-content">
       <c:if test="${comment.status=='wait'}">
        <span class="comment-note">你的评论正在等待审核。。。</span>
      </c:if>
       <p>${comment.content}</p>
     </div>
     <c:if test="${comment.children!=null&&fn:length(comment.children)!=0}">
       <c:set var="depth" value="${depth+1}" scope="request" />
       <c:set var="parent" value="${status.index%2==0}" scope="request" />
       <c:set var="comments" value="${comment.children}" scope="request" />
       <jsp:include page="comments_list.jsp" flush="false" />
       <c:set var="depth" value="${depth-1}" scope="request" />
     </c:if>
   </li>
  </c:forEach>
</ol>

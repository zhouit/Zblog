<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="z" uri="/WEB-INF/tld/function.tld" %>
<%@ taglib uri="/WEB-INF/tld/shiro-function.tld" prefix="sf" %>
<fieldset id="respond" class="comment_form_wrapper">
<c:choose>
 <c:when test="${!g.allowComment || post.cstatus=='close'|| (!sf:isUser()&&post.pstatus=='secret')}">
  当前禁止评论
 </c:when>
 <c:otherwise>
  <div id="cancel_comment_reply">
     <a href="#respond" rel="nofollow">点击这里取消回复。</a>
  </div>
 <!--  <form method="post" action="/comments" id="comment_form"> -->
    <input type="hidden" id="postid" name="postid" value="${post.id}" />
    <input type="hidden" id="comment_parent" name="parent" />
    <c:if test="${cookie.comment_author!=null}">
      <div id="guest_info">登录身份:${z:cookieValue('comment_author')}</div>
    </c:if>
    <c:if test="${cookie.comment_author==null}">
     <div id="guest_info">
      <div id="guest_name">
       <label for="author"><span>昵称</span>( 必须 )</label>
       <input id="author" autocomplete="off" type="text" aria-required="true" size="22" name="creator">
      </div>
      <div id="guest_email">
        <label for="email"><span>E-MAIL</span>( 必须 ) - 不会公开 -</label>
        <input id="email" autocomplete="off" type="text" aria-required="true" size="22" name="email">
      </div>
      <div id="guest_url">
        <label for="url"><span>网址</span></label>
        <input id="url" type="text" tabindex="3" size="22" placeholder="http://" name="url">
      </div>
     </div>
    </c:if>
    <div id="comment_textarea">
      <textarea id="comment" tabindex="4" rows="10" cols="50" name="content"></textarea>
    </div>
    <div id="submit_comment_wrapper">
      <input id="submit_comment" type="button" value="发表评论" >
    </div>
<!--   </form> -->
 </c:otherwise>
</c:choose>
</fieldset>
<script src="${g.domain}/resource/js/zblog.js"></script>

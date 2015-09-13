<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="page" uri="/WEB-INF/tld/pagination.tld" %>
<!DOCTYPE Html>
<html>
<head>
<jsp:include page="common/head.jsp" flush="false" />
</head>
<body>
  <%@include file="common/header.html" %>
  <div id="content" class="clearfix">
    <div id="left_col" class="clearfix">
     <c:if test="${category!=null||tag!=null||archive!=null||search!=null}">
       <div id="archive_headline"><h2>
        <c:choose>
          <c:when test="${category!=null}">'<span>${category}</span>' 目录归档</c:when>
          <c:when test="${tag!=null}">标签 '<span>${tag}</span>'</c:when>
          <c:when test="${search!=null}">搜索结果之于 [ '<span>${search}</span>' ] - ${page.totalCount}条</c:when>
          <c:otherwise>归档之于 '<span><fmt:formatDate value="${archive}" pattern="yyyy 年MMM" /></span>'</c:otherwise>
        </c:choose></h2>
       </div>
      </c:if>
      <c:forEach items="${page.content}" var="post">
      <div class="post_wrap clearfix">
        <div class="post">
          <h3 class="title"><a href="${g.domain}/posts/${post.id}">
          <c:if test="${post.pstatus=='secret'}">私密:</c:if>
          ${post.title}</a></h3>
          <div class="post_content">
            <p class="excerpt">${post.excerpt}...</p>
            <p><a class="more_link" href="${g.domain}/posts/${post.id}">阅读全文</a></p>
          </div>
          <c:if test="${post.tags!=null&&fn:length(post.tags)!=0}">
            <div class="post_tags clearfix">
              <ul class="clearfix">
                <li></li>
                <c:forEach items="${post.tags}" var="tag">
                   <li><a href="${g.domain}/tags/${tag}">${tag}</a></li>
                </c:forEach>
              </ul>
            </div>
            </c:if>
        </div>
        <div class="meta">
          <ul>
            <li class="post_date clearfix">
             <span class="date"><fmt:formatDate value="${post.createTime}" pattern="dd" /></span>
             <span class="month"><fmt:formatDate value="${post.createTime}" pattern="MMM" /></span>
             <span class="year"><fmt:formatDate value="${post.createTime}" pattern="yyyy" /></span>
            </li>
            <li class="post_read">${post.rcount}人阅读</li>
            <li class="post_author">
              <a rel="author" title="由${post.user.nickName}发布" href="${g.domain}/authors/${post.creator}">${post.user.nickName}</a>
            </li>
            <li class="post_comment">
               <a title="${post.title}的评论" href="${g.domain}/posts/${post.id}/#respond">
                 <c:choose>
                   <c:when test="${post.ccount>0}">${post.ccount}条评论</c:when>
                   <c:otherwise>发表评论</c:otherwise>
                 </c:choose>
                </a>
            </li>
           </ul>
        </div>
      </div>
      </c:forEach>
      <div id="page_nav">
        <div class="clearfix page">
          <ul class="page-numbers">
<!--         <li><span class="current">1</span></li>
             <li><a href="#">2</a></li>
             <li><span class="dots">…</span></li>
              <li><a href="#">66</a></li>
              <li><a href="#">>></a></li> -->
             <page:page model="${page}" pageUrl="${request.requestURL}" showPage="9" boundary="2">
               <page:prev>
                 <li><a href="${pageUrl}"><<</a></li>
               </page:prev>
              <page:pager>
                <c:choose>
                  <c:when test="${dot}">
                   <li><span class="dots">…</span></li>
                  </c:when>
                  <c:when test="${pageNumber==page.pageIndex}">
                     <li><span class="current">${pageNumber}</span></li>
                  </c:when>
                  <c:otherwise>
                    <li><a href="${pageUrl}">${pageNumber}</a></li>
                  </c:otherwise>
                </c:choose>
              </page:pager>
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
     <div id="introduction_widget" class="side_widget clearfix">
      <h3 class="headline">About</h3>
      <ul id="social_link">
        <li class="rss_button"><a target="_blank" href="${g.domain}/feed"></a></li>
        <li class="github_button"><a target="_blank" href="https://github.com/dongfangshangren"></a></li>
      </ul>
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
      <%@include file="common/tagcloud.html" %>
      <%@include file="common/archive.html" %>
      <%@include file="common/link.html" %>
    </div>
    <jsp:include page="common/footer.jsp" flush="false" />
  </div>
</body>
</html>

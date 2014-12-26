<%@ taglib prefix="page" uri="/WEB-INF/tld/pagination.tld" %>

<ul class="pagination">
  <page:page model="${page}" pageUrl="" showPage="8">
    <page:prev>
       <li><a href="${pageUrl}"><span aria-hidden="true">&laquo;</span></a></li>
    </page:prev>
    <page:currentLeft>
      <li><a href="${pageUrl}">${pageNumber}</a></li>
    </page:currentLeft>
    <page:current>
       <li><a href="#">${pageNumber}</a></li>
    </page:current>
    <page:currentRight>
      <li><a href="${pageUrl}">${pageNumber}</a></li>
    </page:currentRight>
    <page:next>
      <li><a href="${pageUrl}"><span aria-hidden="true">&raquo;</span></a></li>
    </page:next>
  </page:page>
</ul>
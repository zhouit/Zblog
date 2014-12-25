package com.zblog.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

abstract class AbstartTagSupport extends TagSupport{
  private static final long serialVersionUID = 1L;

  protected void setPageAttribute(int pageNumber){
    Pagination p = (Pagination) findAncestorWithClass(this, Pagination.class);

    pageContext.setAttribute("pageNumber", pageNumber);
    pageContext.setAttribute("pageUrl", p.getPageUrl() + pageNumber);
  }

  @Override
  public int doEndTag() throws JspException{
    pageContext.removeAttribute("pageNumber");
    pageContext.removeAttribute("pageUrl");
    return super.doEndTag();
  }

}

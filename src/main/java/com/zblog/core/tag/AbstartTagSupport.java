package com.zblog.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

abstract class AbstartTagSupport extends TagSupport{
  private static final long serialVersionUID = 1L;

  protected void setPageAttribute(int pageNumber){
    Pagination p = (Pagination) findAncestorWithClass(this, Pagination.class);

    pageContext.setAttribute("pageNumber", pageNumber);
    pageContext.setAttribute("pageUrl", p.getPageUrl() + pageNumber);
  }

  protected void clearPageAttribute(){
    pageContext.removeAttribute("pageNumber");
    pageContext.removeAttribute("pageUrl");
  }

  @Override
  public int doEndTag() throws JspException{
    clearPageAttribute();
    return super.doEndTag();
  }

}

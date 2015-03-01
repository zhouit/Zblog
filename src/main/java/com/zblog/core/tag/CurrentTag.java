package com.zblog.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

public class CurrentTag extends AbstartTagSupport{
  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException{
    Pagination p = (Pagination) findAncestorWithClass(this, Pagination.class);
    PageModel model = p.getModel();
    /* 防止下标越界 */
    setPageAttribute(Math.min(model.getPageIndex(), model.getTotalPage()));

    return TagSupport.EVAL_BODY_INCLUDE;
  }

}

package com.zblog.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.common.plugin.PageModel;

public class LastTag extends AbstartTagSupport{
  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException{
    Pagination p = (Pagination) findAncestorWithClass(this, Pagination.class);
    PageModel model = p.getModel();

    if(model.getPageIndex() > 1){
      setPageAttribute(model.getTotalPage());
      return TagSupport.EVAL_BODY_INCLUDE;
    }else{
      return TagSupport.SKIP_BODY;
    }
  }

}

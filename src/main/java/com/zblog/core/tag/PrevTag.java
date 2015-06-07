package com.zblog.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

public class PrevTag extends AbstartTagSupport{
  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException{
    PageModel<?> model = getPagination().getModel();

    if(model.getPageIndex() > 1){
      setPageAttribute(model.getPageIndex() - 1);
      return TagSupport.EVAL_BODY_INCLUDE;
    }else{
      return TagSupport.SKIP_BODY;
    }
  }

}

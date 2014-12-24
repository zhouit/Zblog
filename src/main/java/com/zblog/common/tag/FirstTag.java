package com.zblog.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class FirstTag extends TagSupport{
  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException{
    pageContext.setAttribute("page", 1);
    return TagSupport.EVAL_BODY_INCLUDE;
  }

  @Override
  public int doEndTag() throws JspException{
    pageContext.removeAttribute("page");
    return super.doEndTag();
  }
  
}

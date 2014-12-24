package com.zblog.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.common.plugin.PageModel;

public class LastTag extends TagSupport{
  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException{
    PageModel model = (PageModel) pageContext.getRequest().getAttribute("model");
    if(model.getPageIndex() > 1){
      pageContext.setAttribute("page", model.getTotalPage());
      return TagSupport.EVAL_BODY_INCLUDE;
    }else{
      return TagSupport.SKIP_BODY;
    }
  }
  
  @Override
  public int doEndTag() throws JspException{
    pageContext.removeAttribute("page");
    return super.doEndTag();
  }

}

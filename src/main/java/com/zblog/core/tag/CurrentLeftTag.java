package com.zblog.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

public class CurrentLeftTag extends AbstartTagSupport{
  private static final long serialVersionUID = 1L;
  /* 当前页数 */
  private int current, end;

  @Override
  public int doStartTag() throws JspException{
    Pagination p = (Pagination) findAncestorWithClass(this, Pagination.class);
    PageModel model = p.getModel();

    if(model.getPageIndex() < model.getTotalPage() / 2){
      current = Math.max(1, model.getPageIndex() - p.getShowPage() / 2);
      end = current + p.getShowPage() - 1;
    }else{
      end = Math.min(model.getTotalPage(), model.getPageIndex() + p.getShowPage() / 2 - 1);
      current = Math.max(1, end - p.getShowPage());
    }

    end = Math.min(model.getPageIndex() - 1, model.getTotalPage() - 1);
    if(current > end)
      return TagSupport.SKIP_BODY;

    setPageAttribute(current);
    current++;

    return TagSupport.EVAL_BODY_INCLUDE;
  }

  @Override
  public int doAfterBody() throws JspException{
    setPageAttribute(current);

    return current++ <= end ? TagSupport.EVAL_BODY_AGAIN : TagSupport.SKIP_BODY;
  }

}

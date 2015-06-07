package com.zblog.core.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

public class Pager extends AbstartTagSupport{
  private static final long serialVersionUID = 1L;

  private int current, start, end;

  @Override
  public int doAfterBody() throws JspException{
    Pagination<?> p = getPagination();
    PageModel<?> model = p.getModel();

    pageContext.removeAttribute("dot");
    if(current <= p.getBoundary()){
      setPageAttribute(current);

      return current++ <= model.getTotalPage() ? TagSupport.EVAL_BODY_AGAIN : TagSupport.SKIP_BODY;
    }
    
    if(current < start){
      pageContext.setAttribute("dot", true);
      clearPageAttribute();
      current = start;
      return TagSupport.EVAL_BODY_AGAIN;
    }

    if(current >= start && current <= end){
      setPageAttribute(current);
      current++;

      return TagSupport.EVAL_BODY_AGAIN;
    }

    if(p.getBoundary() < 1)
      return TagSupport.SKIP_BODY;

    if(current > end && current < model.getTotalPage() - p.getBoundary() + 1){
      pageContext.setAttribute("dot", true);
      clearPageAttribute();
      current = model.getTotalPage() - p.getBoundary() + 1;
      return TagSupport.EVAL_BODY_AGAIN;
    }

    /* 此时totalPage - boundary<=current<=totalPage */
    setPageAttribute(current);
    return current++ <= model.getTotalPage() ? TagSupport.EVAL_BODY_AGAIN : TagSupport.SKIP_BODY;
  }

  @Override
  public int doStartTag() throws JspException{
    initStartEnd();

    setPageAttribute(current);
    current++;
    return TagSupport.EVAL_BODY_INCLUDE;
  }

  private void initStartEnd(){
    Pagination<?> p = getPagination();
    PageModel<?> model = p.getModel();
    int extra = p.getShowPage() - 2 * p.getBoundary();
    if(model.getTotalPage() - 2 * p.getBoundary() > 0){
      if(model.getPageIndex() <= model.getTotalPage() / 2){
        start = Math.max(p.getBoundary() + 1, model.getPageIndex() - extra / 2);
        end = Math.min(start + extra - 1, model.getTotalPage() - p.getBoundary());
      }else{
        end = Math.min(model.getTotalPage() - p.getBoundary(), model.getPageIndex() + extra / 2);
        start = Math.max(end - extra + 1, p.getBoundary() + 1);
      }
    }else{
      start = 1;
      end = model.getTotalPage();
    }

    current = p.getBoundary() > 0 ? 1 : start;
  }

}

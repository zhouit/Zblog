package com.zblog.core.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

public class Pagination extends TagSupport{
  private static final long serialVersionUID = 1L;

  private static final int SHOW_PAGE = 10;
  private static final int BOUNDARY_PAGE = 3;

  // 页面访问参数如/user/product
  private String pageUrl;
  private PageModel model;

  // 显示几个页脚
  private int showPage = SHOW_PAGE;
  // 最左最右保留几个页脚
  private int boundary = BOUNDARY_PAGE;

  public void setPageUrl(String pageUrl){
    this.pageUrl = pageUrl;
  }

  String getPageUrl(){
    return pageUrl;
  }

  public void setModel(PageModel model){
    this.model = model;
  }

  PageModel getModel(){
    return model;
  }

  public void setShowPage(int showPage){
    this.showPage = showPage < 2 ? SHOW_PAGE : showPage;
  }

  int getShowPage(){
    return showPage;
  }

  public void setBoundary(int boundary){
    this.boundary = boundary < 0 ? 0 : boundary;
  }

  int getBoundary(){
    return boundary;
  }

  @Override
  public int doStartTag() throws JspException{
    if(model.getTotalPage() < 2)
      return TagSupport.SKIP_BODY;

    this.pageUrl = genPageUrl();

    return TagSupport.EVAL_BODY_INCLUDE;
  }

  private String genPageUrl(){
    String result = pageUrl;
    Map<String, Object> query = model.getQuery();
    if(query != null && !query.isEmpty()){
      result += "?";
      for(Map.Entry<String, Object> entry : query.entrySet()){
        result += entry.getKey() + "=" + entry.getValue() + "&";
      }
      result += "page=";
    }else{
      result += "?page=";
    }

    return result;
  }

}

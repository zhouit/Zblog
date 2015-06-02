package com.zblog.core.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

/**
 * 分页标签,使用方法:
 * 
 * <pre>
 *   &lt;page:page model="${requestPageAttr}" pageUrl="." showPage="8"&gt;
 *     &lt;page:first&gt;
 *      somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:first&gt;
 *     &lt;page:prev&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:prev&gt;
 *     &lt;page:currentLeft&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:currentLeft&gt;
 *     &lt;page:current&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:current&gt;
 *     &lt;page:currentRight&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:currentRight&gt;
 *     &lt;page:next&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:next&gt;
 *     &lt;page:last&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:last&gt;
 *   &lt;/page:page&gt;
 * </pre>
 * 
 * @author zhou
 *
 */
public class Pagination extends TagSupport{
  private static final long serialVersionUID = 1L;

  private static final int SHOW_PAGE = 10;
  private static final int BOUNDARY_PAGE = 3;

  // 页面访问参数如/user/product
  private String pageUrl;
  private PageModel model;
  /* 当前遍历索引 */
  private int current = 0;

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

  void setCurrent(int current){
    this.current = current;
  }

  int getCurrent(){
    return current;
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

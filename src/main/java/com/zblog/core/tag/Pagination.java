package com.zblog.core.tag;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.core.plugin.PageModel;

/**
 * 灵活的分页标签,使用方法:
 * 
 * <pre>
 *   &lt;page:page model="${requestPageAttr}" pageUrl="." showPage="8"&gt;
 *     &lt;page:first&gt;
 *      somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:first&gt;
 *     &lt;page:prev&gt;
 *       somehtml,${pageUrl}-${pageNumber}
 *     &lt;/page:prev&gt;
 *     &lt;page:pager&gt;
 *     &lt;c:choose&gt;
 *       &lt;c:when test='${dot}'&gt;
 *         ...
 *       &lt;/c:when&gt;
 *       &lt;c:when test='${pageNumber==page.pageIndex}'&gt;
 *         currentIndex,${pageUrl}-${pageNumber}
 *       &lt;/c:when&gt;
 *       &lt;c:otherwise&gt;
 *         somehtml,${pageUrl}-${pageNumber}
 *       &lt;/c:otherwise&gt;
 *     &lt;/c:choose&gt;
 *     &lt;/page:pager&gt;
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
public class Pagination<T> extends TagSupport{
  private static final long serialVersionUID = 1L;

  private static final int SHOW_PAGE = 10;

  // 页面访问参数如/user/product
  private String pageUrl;
  private PageModel<T> model;

  // 显示几个页脚
  private int showPage = SHOW_PAGE;
  // 最左最右保留几个页脚
  private int boundary = 0;

  public void setPageUrl(String pageUrl){
    this.pageUrl = pageUrl;
  }

  String getPageUrl(){
    return pageUrl;
  }

  public void setModel(PageModel<T> model){
    this.model = model;
  }

  PageModel<T> getModel(){
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

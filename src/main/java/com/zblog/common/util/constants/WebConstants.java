package com.zblog.common.util.constants;

import com.zblog.common.util.ServletUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;

public class WebConstants{
  private WebConstants(){
  }

  /**
   * 站点标题
   */
  public static String TITLE = "Zblog";
  /**
   * 站点副标题
   */
  public static String SUBTITLE = "赚够钱背着画板去旅行";
  /**
   * 是否允许评论
   */
  public static boolean ALLOW_COMMENT = false;

  /**
   * 站点标题前缀
   */
  public static final String PRE_TITLE_KEY = "ptitle";

  public static String DESCRIPTION = "Zblog Spring MyBatis";

  public static String APPLICATION_PATH;
  public static final String PREFIX = "/WEB-INF/jsp/";

  public static String getDomain(){
    WebContext context = WebContextHolder.get();
    return ServletUtils.getDomain(context.getRequest());
  }

  public static void init(String title, String subtitle){
    if(!StringUtils.isBlank(title))
      TITLE = title;
    if(!StringUtils.isBlank(subtitle))
      SUBTITLE = subtitle;
  }

  public static void allowComment(String allowComment){
    try{
      ALLOW_COMMENT = Boolean.parseBoolean(allowComment);
    }catch(Exception e){
    }
  }

}

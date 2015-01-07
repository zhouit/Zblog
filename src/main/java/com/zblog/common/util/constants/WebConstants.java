package com.zblog.common.util.constants;

import com.zblog.common.util.ServletUtils;
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
  public static String DESCRIPTION = "Zblog Spring MyBatis";

  public static String APPLICATION_PATH;
  public static final String PREFIX = "/WEB-INF/jsp/";

  public static String getDomain(){
    WebContext context = WebContextHolder.get();
    return ServletUtils.getDomain(context.getRequest());
  }

  public static void init(String title, String subtitle){
    TITLE = title;
    SUBTITLE = subtitle;
  }

}

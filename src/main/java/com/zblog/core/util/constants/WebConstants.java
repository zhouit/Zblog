package com.zblog.core.util.constants;

import com.zblog.core.util.ServletUtils;
import com.zblog.core.util.web.WebContext;
import com.zblog.core.util.web.WebContextFactory;

public class WebConstants{
  private WebConstants(){
  }

  /**
   * 站点标题前缀
   */
  public static final String PRE_TITLE_KEY = "ptitle";

  public static String APPLICATION_PATH;
  public static final String PREFIX = "/WEB-INF/jsp/";
  
  /**
   * 以/backend开头,非/login结尾
   */
  static final String BACKEND_URL = "^/backend.*(?<!/login|/rcode)$";

  public static String getDomain(){
    WebContext context = WebContextFactory.get();
    return ServletUtils.getDomain(context.getRequest());
  }

}

package com.zblog.core;

public class WebConstants{
  private WebConstants(){
  }

  /**
   * 站点标题前缀
   */
  public static final String PRE_TITLE_KEY = "ptitle";

  public static String APPLICATION_PATH;
  public static final String PREFIX = "/WEB-INF/jsp/";
  private static String DOMAIN;

  /**
   * 以/backend开头,非/login结尾
   */
  static final String BACKEND_URL = "^/backend.*(?<!/login|/rcode)$";

  public static String getDomain(){
    return DOMAIN;
  }

  /**
   * 设置当前站点域名，因为{@link getDomain()}
   * 为ThreadLocal方式实现获取domain,这样不在servlet线程中执行时会拿到null值
   * 
   * @param domain
   */
  public static void setDomain(String domain){
    DOMAIN = domain;
  }

  public static String getDomainLink(String path){
    return getDomain() + path;
  }

}

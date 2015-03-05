package com.zblog.core.tag;

import com.zblog.core.util.CookieUtil;
import com.zblog.core.util.FileUtils;
import com.zblog.core.util.StringUtils;
import com.zblog.core.util.web.WebContext;
import com.zblog.core.util.web.WebContextFactory;

public class FunctionHelper{

  private FunctionHelper(){
  }

  /**
   * 获取base64解码后的cookie值
   * 
   * @param name
   * @return
   */
  public static String cookieValue(String name){
    WebContext context = WebContextFactory.get();
    CookieUtil cookieUtil = new CookieUtil(context.getRequest(), context.getResponse());
    return cookieUtil.getCookie(name);
  }

  public static String substring(String src, int start){
    return StringUtils.isBlank(src) ? "" : src.substring(start);
  }

  public static String fileExt(String filename){
    return FileUtils.getFileExt(filename).toUpperCase();
  }

}

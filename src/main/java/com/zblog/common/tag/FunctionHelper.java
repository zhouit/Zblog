package com.zblog.common.tag;

import com.zblog.common.util.CookieUtil;
import com.zblog.common.util.FileUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.web.WebContext;
import com.zblog.common.util.web.WebContextHolder;

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
    WebContext context = WebContextHolder.get();
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

package com.zblog.common.util;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.safety.Whitelist;

/**
 * html文本处理工具
 * 
 * @author zhou
 * 
 */
public class JsoupUtils{
  private final static Whitelist content_filter = Whitelist.relaxed();
  static{
    // 增加可信标签到白名单
    content_filter.addTags("embed", "object", "param", "div", "font", "del");
    // 增加可信属性
    content_filter.addAttributes(":all", "style", "class", "id", "name", "on");
    content_filter.addAttributes("object", "width", "height", "classid", "codebase");
    content_filter.addAttributes("param", "name", "value");
    content_filter.addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen",
        "allowScriptAccess", "flashvars", "name", "type", "pluginspage");
  }

  /**
   * 对用户输入内容进行过滤
   * 
   * @param html
   * @return
   */
  public static String filter(String html){
    return StringUtil.isBlank(html) ? "" : Jsoup.clean(html, content_filter);
  }

  /**
   * 比较宽松的过滤，但是会过滤掉object，script， span,div等标签，适用于富文本编辑器内容或其他html内容
   * 
   * @param html
   * @return
   */
  public static String relaxed(String html){
    return Jsoup.clean(html, Whitelist.relaxed());
  }

  /**
   * 去掉所有标签，返回纯文字.适用于textarea，input
   * 
   * @param html
   * @return
   */
  public static String plainText(String html){
    return Jsoup.parse(html).text();
    // return Jsoup.clean(html, Whitelist.none());
  }

}

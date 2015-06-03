package com.zblog.core.util;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

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
    content_filter.addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess",
        "flashvars", "name", "type", "pluginspage");
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
   * 获取当前html文本中所有可能存在图片的地址
   * 
   * @param html
   * @return
   */
  public static List<String> getImagesOrLinks(String html){
    Document doc = Jsoup.parse(html);
    Elements eles = doc.select("img,a");
    List<String> result = new LinkedList<>();
    for(Element element : eles){
      boolean isa = "a".equals(element.nodeName());
      String link = element.attr(isa ? "href" : "src");
      if(StringUtils.isBlank(link))
        continue;

      if(isa){
        int question = link.indexOf("?");
        if(question > 0)
          link = link.substring(0, question);
        int comma = link.lastIndexOf(".");
        String ext = link.substring(comma + 1).toLowerCase();
        if(FileUtils.isImageExt(ext)){
          result.add(link);
        }
      }else{
        result.add(link);
      }
    }

    return result;
  }

  /**
   * 比较宽松的过滤，但是会过滤掉object，script， span,div等标签，适用于富文本编辑器内容或其他html内容
   * 
   * @param html
   * @return
   */
  public static String simpleText(String html){
    return Jsoup.clean(html, Whitelist.simpleText());
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

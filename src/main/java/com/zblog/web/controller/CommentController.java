package com.zblog.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.common.dal.entity.Comment;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.IpUtils;

@Controller
@RequestMapping("/comments")
public class CommentController{
  private static Whitelist content_filter = Whitelist.relaxed();

  static{
    content_filter.addTags("embed", "object", "param", "span", "div");
    content_filter.addAttributes(":all", "style", "class", "id", "name");
    content_filter.addAttributes("object", "width", "height", "classid", "codebase");
    content_filter.addAttributes("param", "name", "value");
    content_filter.addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess",
        "flashvars", "name", "type", "pluginspage");
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object post(Comment comment, HttpServletRequest request){
    comment.setIp(IpUtils.getIp(request));
    comment.setAgent(request.getHeader("User-Agent"));
    /* 使用jsoup来对帖子内容进行过滤 */
    comment.setContent(Jsoup.clean(comment.getContent(), content_filter));
    return new MapContainer("success", true);
  }

}

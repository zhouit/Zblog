package com.zblog.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.zblog.common.dal.entity.Comment;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.JsoupUtils;
import com.zblog.common.util.ServletUtils;
import com.zblog.web.validator.CommentValidator;

@Controller
@RequestMapping("/comments")
public class CommentController{

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object post(Comment comment, HttpServletRequest request){
    MapContainer result = CommentValidator.validate(comment);
    if(!result.isEmpty()){
      result.put("success", false);
      return result;
    }

    comment.setIp(ServletUtils.getIp(request));
    comment.setAgent(request.getHeader("User-Agent"));
    /* 使用jsoup来对帖子内容进行过滤 */
    String content = HtmlUtils.htmlUnescape(comment.getContent());
    comment.setContent(JsoupUtils.relaxed(content));
    return new MapContainer("success", true);
  }

}

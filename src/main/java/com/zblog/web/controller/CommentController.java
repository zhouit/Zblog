package com.zblog.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.zblog.common.dal.entity.Comment;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.CookieUtil;
import com.zblog.common.util.IdGenarater;
import com.zblog.common.util.JsoupUtils;
import com.zblog.common.util.ServletUtils;
import com.zblog.service.CommentService;
import com.zblog.web.validator.CommentValidator;

@Controller
@RequestMapping("/comments")
public class CommentController{
  @Autowired
  private CommentService commentService;

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object post(Comment comment, HttpServletRequest request, HttpServletResponse response){
    MapContainer result = CommentValidator.validate(comment);
    if(!result.isEmpty()){
      result.put("success", false);
      return result;
    }

    CookieUtil cookieUtil = new CookieUtil(request, response);
    cookieUtil.setCookie("comment_author", comment.getCreator(), "/", false, 365 * 24 * 3600, false);
    cookieUtil.setCookie("comment_author_email", comment.getEmail(), "/", false, 365 * 24 * 3600, false);
    cookieUtil.setCookie("comment_author_url", comment.getUrl(), "/", false, 365 * 24 * 3600, false);

    comment.setId(IdGenarater.uuid19());
    comment.setIp(ServletUtils.getIp(request));
    comment.setAgent(request.getHeader("User-Agent"));
    /* 使用jsoup来对帖子内容进行过滤 */
    String content = HtmlUtils.htmlUnescape(comment.getContent());
    comment.setContent(JsoupUtils.simpleText(content));
    commentService.insert(comment);
    return new MapContainer("success", true);
  }

}

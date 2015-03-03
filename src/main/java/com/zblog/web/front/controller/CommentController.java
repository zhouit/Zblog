package com.zblog.web.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.zblog.core.dal.entity.Comment;
import com.zblog.core.util.CookieUtil;
import com.zblog.core.util.IdGenarater;
import com.zblog.core.util.JsoupUtils;
import com.zblog.core.util.ServletUtils;
import com.zblog.core.util.StringUtils;
import com.zblog.service.CommentService;
import com.zblog.web.front.validator.CommentValidator;

@Controller
@RequestMapping("/comments")
public class CommentController{
  @Autowired
  private CommentService commentService;

  @RequestMapping(method = RequestMethod.POST)
  public String post(Comment comment, HttpServletRequest request, HttpServletResponse response){
    CookieUtil cookieUtil = new CookieUtil(request, response);
    if(StringUtils.isBlank(comment.getCreator())){
      comment.setCreator(cookieUtil.getCookie("comment_author"));
      comment.setUrl(cookieUtil.getCookie("comment_author_url", false));
      comment.setEmail(cookieUtil.getCookie("comment_author_email", false));
    }

    if(!CommentValidator.validate(comment).isEmpty()){
      return "redirect:/posts/"+comment.getPostid();
    }

    /* 根据RFC-2109中的规定，在Cookie中只能包含ASCII的编码 */
    cookieUtil.setCookie("comment_author", comment.getCreator(), "/", false, 365 * 24 * 3600, true);
    cookieUtil.setCookie("comment_author_email", comment.getEmail(), "/", false, 365 * 24 * 3600, false);
    cookieUtil.setCookie("comment_author_url", comment.getUrl(), "/", false, 365 * 24 * 3600, false);

    comment.setId(IdGenarater.uuid19());
    comment.setIp(ServletUtils.getIp(request));
    comment.setAgent(request.getHeader("User-Agent"));
    /* 使用jsoup来对帖子内容进行过滤 */
    String content = HtmlUtils.htmlUnescape(comment.getContent());
    comment.setContent(JsoupUtils.simpleText(content));
    commentService.insert(comment);
    return "redirect:/posts/"+comment.getPostid();
  }
  
}

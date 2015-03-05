package com.zblog.web.front.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.biz.aop.PostIndexManager;
import com.zblog.core.dal.entity.Option;
import com.zblog.core.feed.ArticleAdapter;
import com.zblog.core.feed.Channel;
import com.zblog.core.feed.Channel.Article;
import com.zblog.core.feed.RssFeedWriter;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.ServletUtils;
import com.zblog.core.util.StringUtils;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.PostService;

@Controller
public class IndexController{
  @Autowired
  private PostService postService;
  @Autowired
  private PostIndexManager postIndexManager;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, String word, Model model){
    if(!StringUtils.isBlank(word)){
      model.addAttribute("page", postIndexManager.search(word, page));
      model.addAttribute(WebConstants.PRE_TITLE_KEY, word);
    }else{
      model.addAttribute("page", postService.listPost(page, 10));
    }
    return "index";
  }

  @RequestMapping(value = "/feed")
  public void rss(HttpServletRequest request, HttpServletResponse response) throws IOException{
    Channel channel = new Channel();
    channel.setDomain(ServletUtils.getDomain(request));
    channel.setLogoUrl(channel.getDomain()+"/resource/img/logo.png");
    channel.setTitle(WebConstants.TITLE);
    channel.setDescription(WebConstants.DESCRIPTION);
    
    List<Article> items = new ArrayList<>();
    for(MapContainer mc : postService.listRss()){
      items.add(new ArticleAdapter(mc));
    }
    channel.setItems(items);
    response.setContentType("text/xml");
    try{
      RssFeedWriter.write(channel, response.getOutputStream());
    }catch(XMLStreamException | IOException e){
      throw new IOException(e);
    }
  }

  @RequestMapping("/init.json")
  public void init(@ModelAttribute Option option){
    option.setName("此为测试页");
    option.setValue("hello");
  }

}

package com.zblog.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.biz.EhCacheManager;
import com.zblog.common.lucene.SearchEnginer;

@Controller
@RequestMapping("/backend/monitor")
public class MonitorController{
  @Autowired
  private EhCacheManager ehCacheManager;

  @ResponseBody
  @RequestMapping("/ehcache")
  public Object ehcache(){
    return ehCacheManager.stats();
  }
  
  @ResponseBody
  @RequestMapping("/lucene")
  public Object lucene(){
    return SearchEnginer.postEnginer().docCount();
  }

}

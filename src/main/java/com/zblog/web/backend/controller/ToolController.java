package com.zblog.web.backend.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.zblog.biz.EhCacheManager;
import com.zblog.biz.WordPressManager;
import com.zblog.core.dal.entity.User;
import com.zblog.core.lucene.SearchEnginer;
import com.zblog.core.util.web.WebContextFactory;

@Controller
@RequestMapping("/backend/tool")
@RequiresRoles("admin")
public class ToolController{
  @Autowired
  private EhCacheManager ehCacheManager;
  @Autowired
  private WordPressManager wordPressManager;

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

  @RequestMapping(value = "/import", method = RequestMethod.GET)
  public String importPage(){
    return "backend/tool/import";
  }

  @RequestMapping(value = "/import", method = RequestMethod.POST)
  public DeferredResult<String> importData(final MultipartFile wordpress, final Model model){
    final User user = WebContextFactory.get().getUser();
    final DeferredResult<String> result = new DeferredResult<String>(600000);
    new Thread(){
      @Override
      public void run(){
        try{
          wordPressManager.importData(wordpress.getInputStream(), user);
          model.addAttribute("msg", "导入成功");
        }catch(Exception e){
          e.printStackTrace();
          model.addAttribute("msg", "导入失败，请重试");
        }

        result.setResult("backend/tool/import");
      }
    }.start();

    return result;
  }
}

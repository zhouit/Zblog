package com.zblog.web.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.PostService;
import com.zblog.template.FreeMarkerUtils;
import com.zblog.web.form.BaseForm;

@Controller
public class IndexController{
  @Autowired
  private PostService postService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", postService.listPost(page, 10));
    return "index";
  }

  @RequestMapping("/init.json")
  public void init(@ModelAttribute("form") BaseForm form){
    MapContainer map = new MapContainer("keywords", "JavaEE - Zblog");
    map.put("description", "Spring MyBatis FreeMarker Lucene Bootstarp");
    map.put("title", "JavaTalk");
    map.put("domain", Constants.DOMAIN);
    map.put("backdomain", Constants.DOMAIN + "/backend");
    FreeMarkerUtils.genHtml("/common/head.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/head.html"), map);

    FreeMarkerUtils.genHtml("/common/footer.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/footer.html"), map);
    FreeMarkerUtils.genHtml("/common/comments_form.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/comments_form.html"), map);
    FreeMarkerUtils.genHtml("/common/bootstrap.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/bootstrap.html"), map);
    FreeMarkerUtils.genHtml("/backend/sidebar.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/backend/common/sidebar.html"), map);
  }

}

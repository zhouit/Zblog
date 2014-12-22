package com.zblog.biz.aop;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.constants.Constants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.CategoryService;
import com.zblog.service.LinkService;
import com.zblog.template.FreeMarkerUtils;

public class StaticTemplate{
  private static final Logger logger = LoggerFactory.getLogger(StaticTemplate.class);

  /**
   * 静态化导航栏
   */
  public void staticHeader(){
    CategoryService categoryService = ApplicationContextUtil.getBean(CategoryService.class);

    MapContainer map = new MapContainer();
    map.put("domain", Constants.DOMAIN);
    map.put("categorys", categoryService.listAsTree());

    FreeMarkerUtils.genHtml("/common/header.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/header.html"), map);

    logger.info("staticHeader");
  }

  /**
   * 静态化友情链接
   */
  public void staticLinks(){
    LinkService linksService = ApplicationContextUtil.getBean(LinkService.class);

    MapContainer map = new MapContainer();
    map.put("links", linksService);

    FreeMarkerUtils.genHtml("/common/link.html", new File(WebConstants.APPLICATION_PATH, WebConstants.PREFIX
        + "/common/link.html"), map);
    logger.info("staticLinks");
  }

}

package com.zblog.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.util.constants.CategoryConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.CategoryService;

public class ContextListener implements ServletContextListener{
  @Override
  public void contextInitialized(ServletContextEvent sce){
    WebConstants.APPLICATION_PATH = sce.getServletContext().getRealPath("/");
    CategoryService categoryService = ApplicationContextUtil.getBean(CategoryService.class);
    if(categoryService.loadByName(CategoryConstants.ROOT) == null){
      categoryService.init();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce){

  }

}

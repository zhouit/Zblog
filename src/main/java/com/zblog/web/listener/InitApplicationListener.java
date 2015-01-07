package com.zblog.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zblog.backend.form.GeneralOption;
import com.zblog.biz.OptionManager;
import com.zblog.common.plugin.ApplicationContextUtil;
import com.zblog.common.util.constants.CategoryConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.CategoryService;

public class InitApplicationListener implements ServletContextListener{
  @Override
  public void contextInitialized(ServletContextEvent sce){
    WebConstants.APPLICATION_PATH = sce.getServletContext().getRealPath("/");
    initApp();
  }

  private void initApp(){
    CategoryService categoryService = ApplicationContextUtil.getBean(CategoryService.class);
    if(categoryService.loadByName(CategoryConstants.ROOT) == null){
      categoryService.init();
    }

    OptionManager optionManager = ApplicationContextUtil.getBean(OptionManager.class);
    GeneralOption option = optionManager.getGeneralOption();
    if(option != null)
      WebConstants.init(option.getTitle(), option.getSubtitle());
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce){

  }

}

package com.zblog.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zblog.core.WebConstants;

public class InitApplicationListener implements ServletContextListener{
  
  @Override
  public void contextInitialized(ServletContextEvent sce){
    WebConstants.APPLICATION_PATH = sce.getServletContext().getRealPath("/");
    /* 给log4j设置环境变量，必须要在jvm加载log4j.properties前设置 */
    System.setProperty("log4jHome", WebConstants.APPLICATION_PATH);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce){

  }

}

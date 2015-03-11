package com.zblog.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Log4jConfigureListener implements ServletContextListener{

  @Override
  public void contextInitialized(ServletContextEvent sce){
    /* 给log4j设置环境变量，必须要在jvm加载log4j.properties前设置 */
    System.setProperty("log4jHome", sce.getServletContext().getRealPath("/"));
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce){

  }

}

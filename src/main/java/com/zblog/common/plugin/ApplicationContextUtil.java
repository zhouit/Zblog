package com.zblog.common.plugin;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware{
  static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
    context = applicationContext;
  }
  
  /**
   * 根据提供的bean名称得到相应的类
   * 
   * @param name
   *          bean名称
   */
  public static Object getBean(String name){
    return context.getBean(name);
  }

  /**
   * 根据提供的指定类型的类得到相应的类
   * 
   * @param servName
   *          bean名称
   */
  public static <T> T getBean(Class<T> clazz){
    return context.getBean(clazz);
  }

  /**
   * 根据提供的bean名称得到对应于指定类型的类
   * 
   * @param name
   *          bean名称
   * @param clazz
   *          返回的bean类型,若类型不匹配,将抛出异常
   */
  public static <T> T getBean(String name, Class<T> clazz){
    return context.getBean(name, clazz);
  }

}

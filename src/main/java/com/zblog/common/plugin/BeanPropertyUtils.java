package com.zblog.common.plugin;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanPropertyUtils{
  static final Logger logger = LoggerFactory.getLogger(BeanPropertyUtils.class);

  public static Object getFieldValue(Object obj, String fieldName){
    Object value = null;
    try{
      Field field = getFieldByFieldName(obj, fieldName);
      if(field != null){
        if(field.isAccessible()){
          value = field.get(obj);
        }else{
          field.setAccessible(true);
          value = field.get(obj);
          field.setAccessible(false);
        }
      }
    }catch(Exception e){
      logger.warn("can't get field " + fieldName + " value in class " + obj);
    }

    return value;
  }

  public static Field getFieldByFieldName(Object obj, String fieldName){
    if(obj == null || fieldName == null){
      return null;
    }

    for(Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
        .getSuperclass()){
      try{
        return superClass.getDeclaredField(fieldName);
      }catch(Exception e){
        logger.warn("can't found field " + fieldName + " in class "
            + obj.getClass());
      }
    }

    return null;
  }

  public static boolean setFieldValue(Object obj, String fieldName,Object value){
    try{
      Field field = obj.getClass().getDeclaredField(fieldName);
      if(field.isAccessible()){
        field.set(obj, value);
      }else{
        field.setAccessible(true);
        field.set(obj, value);
        field.setAccessible(false);
      }
      return true;
    }catch(Exception e){
    }
    return false;
  }

}

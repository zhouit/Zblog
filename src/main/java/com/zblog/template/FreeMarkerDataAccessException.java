package com.zblog.template;

import org.springframework.dao.DataAccessException;

/**
 * <p>
 * freemarker静态化在spring的事务中进行，此处继承spring的dao异常
 * </p>
 * 
 * @author zhou
 * 
 */
public class FreeMarkerDataAccessException extends DataAccessException{
  private static final long serialVersionUID = 1L;

  public FreeMarkerDataAccessException(String msg){
    super(msg);
  }

  public FreeMarkerDataAccessException(String msg, Throwable t){
    super(msg, t);
  }

}

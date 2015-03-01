package com.zblog.core.filter;

import java.util.Calendar;
import java.util.Date;

import com.zblog.core.util.constants.WebConstants;

public class Global{
  private String domain;

  public Global(String domain){
    this.domain = domain;
  }

  public String getTitle(){
    return WebConstants.TITLE;
  }

  public String getSubtitle(){
    return WebConstants.SUBTITLE;
  }

  public String getDescription(){
    return WebConstants.DESCRIPTION;
  }

  public boolean isAllowComment(){
    return WebConstants.ALLOW_COMMENT;
  }

  public int getYear(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    return calendar.get(Calendar.YEAR);
  }

  public String getDomain(){
    return domain;
  }

}

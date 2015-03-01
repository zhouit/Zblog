package com.zblog.core.dal.entity;

import java.util.Date;

public class BaseEntity{
  private String id;
  private Date createTime;
  private String creator;
  private Date lastUpdate;

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

  public Date getCreateTime(){
    return createTime;
  }

  public void setCreateTime(Date createTime){
    this.createTime = createTime;
  }

  public String getCreator(){
    return creator;
  }

  public void setCreator(String creator){
    this.creator = creator;
  }

  public Date getLastUpdate(){
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate){
    this.lastUpdate = lastUpdate;
  }

}

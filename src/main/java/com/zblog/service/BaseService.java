package com.zblog.service;

import java.util.Date;
import java.util.List;

import com.zblog.common.dal.entity.BaseEntity;
import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;

public abstract class BaseService{

  public <T extends BaseEntity> int insert(T t){
    t.setCreateTime(new Date());

    return getMapper().insert(t);
  }

  public <T extends BaseEntity> T loadById(String id){
    return getMapper().loadById(id);
  }

  public void list(PageModel model){
     List<MapContainer> result=getMapper().list(model);
     model.setContent(result);
  }
  
  public List<MapContainer> list(){
    return getMapper().list();
  }

  public <T extends BaseEntity> void update(T t){
    t.setLastUpdate(new Date());

    getMapper().update(t);
  }

  public void deleteById(String id){
    getMapper().deleteById(id);
  }

  protected abstract BaseMapper getMapper();
}
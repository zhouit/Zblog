package com.zblog.service;

import java.util.List;

import com.zblog.core.dal.entity.BaseEntity;
import com.zblog.core.dal.mapper.BaseMapper;
import com.zblog.core.plugin.PageModel;

public abstract class BaseService{

  public <T extends BaseEntity> int insert(T t){
    return getMapper().insert(t);
  }

  public <T extends BaseEntity> T loadById(String id){
    return getMapper().loadById(id);
  }

  public <T> void list(PageModel<T> model){
     List<T> result=getMapper().list(model);
     model.setContent(result);
  }
  
  public <T> List<T> list(){
    return getMapper().list();
  }

  public <T extends BaseEntity> void update(T t){
    getMapper().update(t);
  }

  public void deleteById(String id){
    getMapper().deleteById(id);
  }

  protected abstract BaseMapper getMapper();
}
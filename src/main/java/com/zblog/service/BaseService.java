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
    List<T> result = getMapper().list(model);
    model.setContent(result);
  }

  public <T> List<T> list(){
    return getMapper().list();
  }

  public <T extends BaseEntity> int update(T t){
    return getMapper().update(t);
  }

  public int deleteById(String id){
    return getMapper().deleteById(id);
  }

  public long count(){
    return getMapper().count();
  }

  protected abstract BaseMapper getMapper();
}
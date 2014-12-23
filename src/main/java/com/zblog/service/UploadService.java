package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.UploadMapper;
import com.zblog.common.plugin.PageModel;

@Service
public class UploadService extends BaseService{
  @Autowired
  private UploadMapper postmetaMapper;
  
  public PageModel list(int pageIndex,int pageSize){
    PageModel pageModel=new PageModel(pageIndex, pageSize);
    list(pageModel);
    
    return pageModel;
  }

  @Override
  protected BaseMapper getMapper(){
    return postmetaMapper;
  }

}

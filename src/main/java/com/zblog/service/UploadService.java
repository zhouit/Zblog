package com.zblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.UploadMapper;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;

@Service
public class UploadService extends BaseService{
  @Autowired
  private UploadMapper uploadMapper;

  public PageModel list(int pageIndex, int pageSize){
    PageModel pageModel = new PageModel(pageIndex, pageSize);
    list(pageModel);

    return pageModel;
  }
  
  public List<MapContainer> listByPostid(String postid){
    return uploadMapper.listByPostid(postid);
  }

  public void updatePostid(String postid, String uploadToken){
    uploadMapper.updatePostid(postid, uploadToken);
  }
  
  public void deleteByPostid(String postid){
    uploadMapper.deleteByPostid(postid);
  }

  @Override
  protected BaseMapper getMapper(){
    return uploadMapper;
  }

}

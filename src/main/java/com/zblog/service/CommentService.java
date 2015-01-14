package com.zblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.CommentMapper;
import com.zblog.common.plugin.MapContainer;

@Service
public class CommentService extends BaseService{
  @Autowired
  private CommentMapper commentMapper;

  /**
   * 最近留言
   * 
   * @return
   */
  public List<MapContainer> listRecent(){
    return commentMapper.listRecent();
  }

  @Override
  protected BaseMapper getMapper(){
    return commentMapper;
  }

}

package com.zblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.core.dal.mapper.BaseMapper;
import com.zblog.core.dal.mapper.CommentMapper;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;

@Service
public class CommentService extends BaseService{
  @Autowired
  private CommentMapper commentMapper;

  public PageModel list(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    super.list(page);
    return page;
  }

  /**
   * 最近留言
   * 
   * @return
   */
  public List<MapContainer> listRecent(){
    return commentMapper.listRecent();
  }

  public List<MapContainer> listByPost(String postid){
     return commentMapper.listByPost(postid);
  }

  public void approve(String commentid){
    commentMapper.approve(commentid);
  }

  @Override
  protected BaseMapper getMapper(){
    return commentMapper;
  }

}

package com.zblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.CommentMapper;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;

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

  public void approve(String commentid, boolean approved){
    commentMapper.approve(commentid, approved);
  }

  @Override
  protected BaseMapper getMapper(){
    return commentMapper;
  }

}

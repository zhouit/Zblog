package com.zblog.service;

import java.util.Collection;
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

  /**
   * 查找指定状态的评论
   * 
   * @param pageIndex
   * @param pageSize
   * @param status
   * @return
   */
  public PageModel listByStatus(int pageIndex, int pageSize, Collection<String> status){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("status", status);
    super.list(page);
    page.removeQuery("status");
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

  public List<MapContainer> listByPost(String postid, String creator){
    return commentMapper.listByPost(postid, creator);
  }

  public void setStutas(String commentid, String newStatus){
    commentMapper.setStutas(commentid, newStatus);
  }

  @Override
  protected BaseMapper getMapper(){
    return commentMapper;
  }

}

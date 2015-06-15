package com.zblog.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.core.dal.mapper.BaseMapper;
import com.zblog.core.dal.mapper.CommentMapper;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;
import com.zblog.service.vo.CommentVO;

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
  public PageModel<MapContainer> listByStatus(int pageIndex, int pageSize, Collection<String> status){
    PageModel<MapContainer> page = new PageModel<>(pageIndex, pageSize);
    page.insertQuery("status", status);
    super.list(page);
    page.removeQuery("status");
    return page;
  }

  /**
   * 获取各种状态评论的总数
   * 
   * @return
   */
  public MapContainer listCountByGroupStatus(){
    List<MapContainer> list = commentMapper.listCountByGroupStatus();
    MapContainer mc = new MapContainer();
    for(MapContainer temp : list){
      mc.put(temp.getAsString("status"), temp.get("count"));
    }

    return mc;
  }

  /**
   * 最近留言
   * 
   * @return
   */
  public List<CommentVO> listRecent(){
    return commentMapper.listRecent();
  }

  /**
   * 根据postid获取被批准的评论+指定creator的评论
   * 
   * @param postid
   * @param creator
   * @return
   */
  public List<CommentVO> listByPost(String postid, String creator){
    return commentMapper.listByPost(postid, creator);
  }

  public int setStatus(String commentid, String newStatus){
    return commentMapper.setStatus(commentid, newStatus);
  }

  @Override
  protected BaseMapper getMapper(){
    return commentMapper;
  }

}

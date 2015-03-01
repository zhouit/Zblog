package com.zblog.core.dal.mapper;

import java.util.List;

import com.zblog.core.plugin.MapContainer;

public interface CommentMapper extends BaseMapper{

  List<MapContainer> listRecent();

  List<MapContainer> listByPost(String postid);

  /**
   * 如果commentid对应评论是拒绝就批准，是批准就拒绝
   * 
   * @param commentid
   */
  void approve(String commentid);

}

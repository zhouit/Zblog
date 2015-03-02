package com.zblog.core.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zblog.core.plugin.MapContainer;

public interface CommentMapper extends BaseMapper{

  List<MapContainer> listRecent();

  /**
   * 根据postid获取被批准的评论和指定creator的评论
   * 
   * @param postid
   * @param creator
   * @return
   */
  List<MapContainer> listByPost(@Param("postid") String postid, @Param("creator") String creator);

  /**
   * 更改comment的状态
   * 
   * @param commentid
   */
  void setStutas(@Param("commentid")String commentid, @Param("status")String status);

}

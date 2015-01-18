package com.zblog.common.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zblog.common.plugin.MapContainer;

public interface CommentMapper extends BaseMapper{

  List<MapContainer> listRecent();

  void approve(@Param("commentid") String commentid, @Param("approved") boolean approved);

}

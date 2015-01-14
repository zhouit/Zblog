package com.zblog.common.dal.mapper;

import java.util.List;

import com.zblog.common.plugin.MapContainer;

public interface CommentMapper extends BaseMapper{
  
  List<MapContainer> listRecent();
  
}

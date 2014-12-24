package com.zblog.common.dal.mapper;

import java.util.List;

import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;

public interface PostMapper extends BaseMapper{
  
  List<MapContainer> listRecent();
  List<MapContainer> listByCategory(PageModel model);
  
}

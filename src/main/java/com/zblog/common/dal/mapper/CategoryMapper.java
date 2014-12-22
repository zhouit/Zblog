package com.zblog.common.dal.mapper;

import java.util.List;

import com.zblog.common.dal.entity.Category;
import com.zblog.common.plugin.MapContainer;

public interface CategoryMapper extends BaseMapper{
  
  List<MapContainer> list();
  Category loadByName(String name);
  
  boolean addLeftv(int rightv);
  boolean addRightv(int rightv);

}

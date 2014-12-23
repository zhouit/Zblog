package com.zblog.common.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zblog.common.dal.entity.Category;
import com.zblog.common.plugin.MapContainer;

public interface CategoryMapper extends BaseMapper{

  List<MapContainer> list();

  Category loadByName(String name);

  void updateTnsertLeftv(int rightv);

  void updateInsertRightv(int rightv);

  void delete(@Param("leftv") int leftv, @Param("rightv") int rightv);

  void updateDeleteLeftv(@Param("leftv") int leftv, @Param("length") int length);

  void updateDeleteRightv(@Param("rightv") int rightv, @Param("length") int length);

}

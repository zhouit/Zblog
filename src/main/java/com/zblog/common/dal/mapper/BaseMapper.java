package com.zblog.common.dal.mapper;

import java.util.List;

import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;

public interface BaseMapper{

  /**
   * 新增记录
   * 
   * @param t
   */
  public <T> int insert(T t);

  /**
   * 根据ID查询记录
   * 
   * @param id
   * @return
   */
  public <T> T loadById(String id);

  public List<MapContainer> list(PageModel model);
  
  public List<MapContainer> list();

  /**
   * 根据ID删除记录
   * 
   * @param id
   */
  public <T> void deleteById(String id);

  /**
   * 更新记录
   * 
   * @param t
   */
  public <T> void update(T t);

}

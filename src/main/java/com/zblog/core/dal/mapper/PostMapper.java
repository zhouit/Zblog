package com.zblog.core.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;

public interface PostMapper extends BaseMapper{

  /**
   * 与loadById区别为:此方法会加载content，而loadById会加载exceprt
   * 
   * @param postid
   * @return
   */
  Post loadEditById(String postid);

  /**
   * 获取页面(只包含ID和title)
   * 
   * @param onlyParent
   * @return
   */
  List<MapContainer> listPage(boolean onlyParent);

  /**
   * 获取最近发表文章
   * 
   * @return
   */
  List<MapContainer> listRecent();

  List<MapContainer> listByCategory(PageModel model);

  void updateCategory(@Param("oldCategoryIds") List<String> oldCategoryIds, @Param("newCategoryId") String newCategoryId);

}

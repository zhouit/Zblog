package com.zblog.core.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;

public interface PostMapper extends BaseMapper{

  public MapContainer loadReadById(String postid);

  /**
   * 获取制定post的下一篇
   * 
   * @param postid
   * @return
   */
  Post getPrevPost(String postid);

  /**
   * 获取指定post的上一篇
   * 
   * @param postid
   * @return
   */
  Post getNextPost(String postid);

  /**
   * 获取页面(只包含ID和title)
   * 
   * @param onlyParent
   * @return
   */
  List<MapContainer> listPage(boolean onlyParent);

  public void addRcount(@Param("postid") String postid, @Param("count") int count);

  public void addCcount(@Param("commentid") String commentid, @Param("count") int count);

  /**
   * 获取最近发表文章
   * 
   * @return 文章id
   */
  List<String> listRecent(int nums);

  List<MapContainer> listByCategory(PageModel model);

  void updateCategory(@Param("oldCategoryIds") List<String> oldCategoryIds, @Param("newCategoryId") String newCategoryId);

}

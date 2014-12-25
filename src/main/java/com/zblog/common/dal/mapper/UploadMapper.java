package com.zblog.common.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zblog.common.plugin.MapContainer;

public interface UploadMapper extends BaseMapper{
  
  List<MapContainer> listByPostid(String postid);

  /**
   * 更新上传文件记录对应的文章ID
   * 
   * @param postid
   * @param uploadToken
   * @return
   */
  public int updatePostid(@Param("postid") String postid, @Param("uploadToken") String uploadToken);
  
  public int deleteByPostid(String postid);

}

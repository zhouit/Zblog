package com.zblog.common.dal.mapper;

import org.apache.ibatis.annotations.Param;

public interface UploadMapper extends BaseMapper{
  
  public int updatePostid(@Param("postid")String postid,@Param("uploadToken")String uploadToken);
  
}

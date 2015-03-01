package com.zblog.core.dal.mapper;

import org.apache.ibatis.annotations.Param;

import com.zblog.core.dal.entity.User;

public interface UserMapper extends BaseMapper{
  
  User loadByNameAndPass(@Param("username")String username,@Param("password")String password);
  
}

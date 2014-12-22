package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.UserMapper;
import com.zblog.common.plugin.PageModel;

@Service
public class UserService extends BaseSevice{
  @Autowired
  private UserMapper userMapper;

  public PageModel list(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageSize, pageSize);
    super.list(page);
    return page;
  }

  @Override
  protected BaseMapper getMapper(){
    return userMapper;
  }

}

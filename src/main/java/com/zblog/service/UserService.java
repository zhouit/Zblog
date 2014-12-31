package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.entity.User;
import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.UserMapper;
import com.zblog.common.plugin.PageModel;

@Service
public class UserService extends BaseService{
  @Autowired
  private UserMapper userMapper;

  public PageModel list(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    super.list(page);
    return page;
  }
  
  public User login(String username,String password){
    return userMapper.loadByNameAndPass(username, password);
  }

  @Override
  protected BaseMapper getMapper(){
    return userMapper;
  }

}

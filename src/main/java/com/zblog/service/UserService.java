package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.core.dal.entity.User;
import com.zblog.core.dal.mapper.BaseMapper;
import com.zblog.core.dal.mapper.UserMapper;
import com.zblog.core.plugin.PageModel;

@Service
public class UserService extends BaseService{
  @Autowired
  private UserMapper userMapper;

  public PageModel<User> list(int pageIndex, int pageSize){
    PageModel<User> page = new PageModel<>(pageIndex, pageSize);
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

package com.zblog.service;

import org.springframework.stereotype.Service;

import com.zblog.common.dal.entity.User;

@Service
public class AuthenticationService{

  public boolean isAuthentication(String uri, User user){
    return true;
  }

}

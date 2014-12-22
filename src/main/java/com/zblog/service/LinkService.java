package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.LinkMapper;

@Service
public class LinkService extends BaseSevice{
  @Autowired
  private LinkMapper linkMapper;

  @Override
  protected BaseMapper getMapper(){
    return linkMapper;
  }

}

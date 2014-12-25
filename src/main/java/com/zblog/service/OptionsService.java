package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.OptionMapper;

@Service
public class OptionsService{
  @Autowired
  private OptionMapper optionMapper;
  
  public String getOptionValue(String name){
    return optionMapper.getOptionValue(name);
  }

}

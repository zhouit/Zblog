package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.entity.Option;
import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.OptionMapper;

@Service
public class OptionsService extends BaseService{
  @Autowired
  private OptionMapper optionMapper;

  public String getOptionValue(String name){
    return optionMapper.getOptionValue(name);
  }

  /**
   * 此处为MySQL的replace into, 注意这需要主键id一致
   * @param name
   * @param value
   */
  public void updateOptionValue(String name, String value){
    Option option=new Option(name, value);
    option.setId(name);
    optionMapper.update(option);
  }

  @Override
  protected BaseMapper getMapper(){
    return optionMapper;
  }

}

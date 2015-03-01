package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.core.dal.entity.Option;
import com.zblog.core.dal.mapper.BaseMapper;
import com.zblog.core.dal.mapper.OptionMapper;

@Service
public class OptionsService extends BaseService{
  @Autowired
  private OptionMapper optionMapper;

  public String getOptionValue(String name){
    return optionMapper.getOptionValue(name);
  }

  /**
   * 以select .. for update,注意此方法须在事务中执行
   * 
   * @param name
   * @return
   */
  public String getOptionValueForUpdate(String name){
    return optionMapper.getOptionValueForUpdate(name);
  }

  /**
   * 此处为MySQL的replace into, 注意这需要主键id一致
   * 
   * @param name
   * @param value
   */
  public void updateOptionValue(String name, String value){
    Option option = new Option(name, value);
    option.setId(name);
    optionMapper.update(option);
  }

  @Override
  protected BaseMapper getMapper(){
    return optionMapper;
  }

}

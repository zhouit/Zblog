package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.PostMapper;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.constants.PostConstants;

@Service
public class PostService extends BaseSevice{
  @Autowired
  private PostMapper postMapper;

  public PageModel listPost(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageSize, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    super.list(page);
    return page;
  }
  
  public PageModel listPage(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageSize, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    super.list(page);
    return page;
  }
  
  public String createId(){
    return DateUtils.currentDate("yyyyMM");
  }

  @Override
  protected BaseMapper getMapper(){
    return postMapper;
  }

}

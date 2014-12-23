package com.zblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.PostMapper;
import com.zblog.common.dal.mapper.UploadMapper;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.constants.PostConstants;

@Service
public class PostService extends BaseService{
  @Autowired
  private PostMapper postMapper;
  @Autowired
  private UploadMapper postmetaMapper;

  public PageModel listPost(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    super.list(page);
    return page;
  }

  @Transactional
  public boolean insert(Post post, String uploadToken){
    insert(post);
    postmetaMapper.updatePostid(post.getId(),uploadToken);
    
    return true;
  }

  public PageModel listPage(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
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

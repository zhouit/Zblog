package com.zblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.dal.mapper.BaseMapper;
import com.zblog.common.dal.mapper.PostMapper;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.constants.PostConstants;

@Service
public class PostService extends BaseService{
  @Autowired
  private PostMapper postMapper;

  /**
   * 此方法会加载Post的content,loadById只会加载excerpt
   * 
   * @param postid
   * @return
   */
  public Post loadEditById(String postid){
    return postMapper.loadEditById(postid);
  }

  public PageModel listPost(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    super.list(page);
    /* 由于分页标签会根据query产生,这里删除掉无用query,下同 */
    page.removeQuery("type");
    return page;
  }

  public List<MapContainer> listRecent(){
    return postMapper.listRecent();
  }

  public PageModel listByCategory(String categoryName, int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    page.insertQuery("categoryName", categoryName);
    List<MapContainer> content = postMapper.listByCategory(page);
    page.setContent(content);
    page.removeQuery("type");

    return page;
  }

  public PageModel listPage(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    super.list(page);
    page.removeQuery("type");
    return page;
  }

  /**
   * 获取所有页面(只包含ID和title)
   * 
   * @param onlyParent
   *          是否只取父类页面
   * @return
   */
  public List<MapContainer> listPage(boolean onlyParent){
    return postMapper.listPage(onlyParent);
  }

  public void updateCategory(String oldCategoryId, String newCategoryId){
    postMapper.updateCategory(oldCategoryId, newCategoryId);
  }

  /**
   * 生成postid，生成规则yyyyMM+当年所长生的文章自增ID
   * 
   * @return
   */
  public String createPostid(){
    return DateUtils.currentDate("yyyyMM");
  }

  @Override
  protected BaseMapper getMapper(){
    return postMapper;
  }

}

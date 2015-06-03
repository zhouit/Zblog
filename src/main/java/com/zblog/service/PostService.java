package com.zblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.mapper.BaseMapper;
import com.zblog.core.dal.mapper.PostMapper;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;
import com.zblog.core.util.constants.PostConstants;

@Service
public class PostService extends BaseService{
  @Autowired
  private PostMapper postMapper;

  public MapContainer loadReadById(String postid){
    return postMapper.loadReadById(postid);
  }

  public Post getNextPost(String postid){
    return postMapper.getNextPost(postid);
  }

  public Post getPrevPost(String postid){
    return postMapper.getPrevPost(postid);
  }

  public PageModel listPost(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("type", PostConstants.TYPE_POST);
    super.list(page);
    /* 由于分页标签会根据query产生,这里删除掉无用query,下同 */
    page.removeQuery("type");
    page.setTotalCount(152);
    return page;
  }

  /**
   * 更改阅读数
   * 
   * @param postid
   * @param count
   */
  public void addRcount(String postid, int count){
    postMapper.addRcount(postid, count);
  }

  /**
   * 更改评论数
   * 
   * @param commentid
   * @param count
   */
  public void addCcount(String commentid, int count){
    postMapper.addCcount(commentid, count);
  }

  public List<String> listRecent(int nums){
    return postMapper.listRecent(nums);
  }

  public PageModel listByCategory(Category category, int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("category", category);
    List<MapContainer> content = postMapper.listByCategory(page);
    page.setContent(content);
    page.removeQuery("category");

    return page;
  }

  public PageModel listPage(int pageIndex, int pageSize){
    PageModel page = new PageModel(pageIndex, pageSize);
    page.insertQuery("type", PostConstants.TYPE_PAGE);
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

  public void updateCategory(List<String> oldCategoryIds, String newCategoryId){
    postMapper.updateCategory(oldCategoryIds, newCategoryId);
  }

  @Override
  protected BaseMapper getMapper(){
    return postMapper;
  }

}

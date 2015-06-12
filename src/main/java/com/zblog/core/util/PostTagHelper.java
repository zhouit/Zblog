package com.zblog.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.entity.Tag;

/**
 * Post标签工具类
 * 
 * @author zhou
 *
 */
public class PostTagHelper{

  private PostTagHelper(){
  }

  /**
   * 
   * @param post
   * @param tagsByComma
   *          以逗号分隔的字符串
   * @param creator
   * @return
   */
  public static List<Tag> from(Post post, String tagsByComma, String creator){
    return !StringUtils.isBlank(tagsByComma) ? from(post, Arrays.asList(tagsByComma.split(",")), creator) : Collections
        .<Tag> emptyList();
  }

  public static List<Tag> from(Post post, List<String> tags, String creator){
    List<Tag> list = new ArrayList<>();
    if(!CollectionUtils.isEmpty(tags)){
      for(String tag : tags){
        Tag t = new Tag();
        t.setId(IdGenerator.uuid19());
        t.setName(tag.trim());
        t.setCreateTime(post.getLastUpdate());
        t.setPostid(post.getId());
        t.setCreator(creator);
        list.add(t);
      }
    }

    return list;
  }

}

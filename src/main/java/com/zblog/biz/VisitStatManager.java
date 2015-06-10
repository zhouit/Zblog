package com.zblog.biz;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zblog.service.PostService;
import com.zblog.service.vo.PostVO;

/**
 * 文章的访问统计
 * 
 * @author zhou
 * 
 */
@Component
public class VisitStatManager{
  private static final Logger logger = LoggerFactory.getLogger(VisitStatManager.class);
  @Autowired
  private PostService postService;
  private ConcurrentMap<String, Integer> visit = new ConcurrentHashMap<>();

  public void flush(){
    ConcurrentMap<String, Integer> copy = visit;
    visit = new ConcurrentHashMap<String, Integer>();
    if(!copy.isEmpty()){
      logger.debug("flush visit stat to database");
    }

    for(Map.Entry<String, Integer> entry : copy.entrySet()){
      postService.addRcount(entry.getKey(), entry.getValue());
    }
    copy.clear();
    copy = null;
  }

  public void record(String postid){
    Integer count = visit.get(postid);
    /* 该数据，并发问题忽略 */
    visit.put(postid, count == null ? 1 : count + 1);
    /* 此处更新文章阅读数 */
    PostVO p = postService.loadById(postid);
    /* 此处实际为更改缓存中数据 */
    p.setRcount(p.getRcount() + 1);
  }

}

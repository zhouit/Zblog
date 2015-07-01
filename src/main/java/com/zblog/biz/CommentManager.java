package com.zblog.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.core.dal.constants.CommentConstants;
import com.zblog.core.dal.entity.Comment;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.plugin.TreeUtils;
import com.zblog.service.CommentService;
import com.zblog.service.PostService;
import com.zblog.service.vo.CommentVO;

@Component
public class CommentManager{
  @Autowired
  private CommentService commentService;
  @Autowired
  private PostService postService;

  public List<CommentVO> getAsTree(String postid, String creator){
    List<CommentVO> list = commentService.listByPost(postid, creator);
    TreeUtils.rebuildTree(list);

    return list;
  }

  /**
   * 最近留言
   * 
   * @return
   */
  public List<CommentVO> listRecent(){
    List<CommentVO> list = commentService.listRecent();
    for(CommentVO cvo : list){
      Post post = postService.loadById(cvo.getPostid());
      cvo.setPost(post);
    }

    return list;
  }

  /**
   * 更改评论状态，同时更改该评论对应的post的评论数
   * 
   * @param commentid
   * @param newStatus
   */
  @Transactional
  public int setStatus(String commentid, String newStatus){
    Comment comment = commentService.loadById(commentid);
    int result = -1;
    if(comment != null){
      commentService.setStatus(commentid, newStatus);
      int count = CommentConstants.TYPE_APPROVE.equals(newStatus) ? 1 : -1;
      result = postService.addCcount(commentid, count);
    }

    return result;
  }

  /**
   * 删除评论，同时更改对应文章的评论数
   * 
   * @param commentid
   */
  @Transactional
  public int deleteComment(String commentid){
    Comment comment = commentService.loadById(commentid);
    int result = -1;
    if(comment != null){
      postService.addCcount(commentid, -1);
      result = commentService.deleteById(commentid);
    }

    return result;
  }

}

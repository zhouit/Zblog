package com.zblog.biz.aop;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.lucene.LuceneUtils;
import com.zblog.common.lucene.QueryBuilder;
import com.zblog.common.lucene.SearchEnginer;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.JsoupUtils;
import com.zblog.common.util.constants.PostConstants;

/**
 * 文章Lucene索引管理器
 * 
 * @author zhou
 * 
 */
@Component
public class PostIndexManager{
  private static final Logger logger = LoggerFactory.getLogger(PostIndexManager.class);

  /**
   * 只有添加文章才插入Lucene索引
   * 
   * @param post
   */
  public void insert(Post post){
    if(PostConstants.TYPE_POST.equals(post.getType())){
      logger.debug("add post index -->" + post.getTitle());
      SearchEnginer.postEnginer().insert(convert(post));
    }
  }

  /**
   * 只有更新文章才更新Lucene索引
   * 
   * @param post
   */
  public void update(Post post){
    if(PostConstants.TYPE_POST.equals(post.getType()))
      SearchEnginer.postEnginer().update(new Term("id", post.getId()), convert(post));
  }

  public void remove(String postid, String postType){
    if(PostConstants.TYPE_POST.equals(postType))
    SearchEnginer.postEnginer().delete(new Term("id", postid));
  }

  public PageModel search(String word, int pageIndex){
    PageModel result = new PageModel(pageIndex, 15);
    QueryBuilder builder = new QueryBuilder(SearchEnginer.postEnginer().getAnalyzer());
    builder.addShould("title", word).addShould("excerpt", word);
    SearchEnginer.postEnginer().searchHighlight(builder, result);

    return result;
  }

  private Document convert(Post post){
    Document doc = new Document();
    doc.add(new Field("id", post.getId() + "", LuceneUtils.directType()));
    doc.add(new Field("title", post.getTitle(), LuceneUtils.searchType()));
    /* 用jsoup剔除html标签 */
    doc.add(new Field("excerpt", JsoupUtils.plainText(post.getContent()), LuceneUtils.searchType()));
    doc.add(new Field("creator", post.getCreator(), LuceneUtils.storeType()));
    doc.add(new Field("createTime", DateUtils.formatDate("yyyy-MM-dd", post.getCreateTime()), LuceneUtils.storeType()));

    return doc;
  }

}

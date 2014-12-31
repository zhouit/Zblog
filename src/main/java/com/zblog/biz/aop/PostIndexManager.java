package com.zblog.biz.aop;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.zblog.common.dal.entity.Post;
import com.zblog.common.lucene.LuceneUtils;
import com.zblog.common.lucene.QueryBuilder;
import com.zblog.common.lucene.SearchEnginer;
import com.zblog.common.plugin.PageModel;
import com.zblog.common.util.DateUtils;

/**
 * 文章Lucene索引管理器
 * 
 * @author zhou
 * 
 */
@Component
public class PostIndexManager{

  public void insert(Post post){
    SearchEnginer.postEnginer().insert(convert(post));
  }

  public void remove(String postid){
    SearchEnginer.postEnginer().delete(new Term("id", postid));
  }

  public PageModel search(String word, int pageIndex){
    PageModel result = new PageModel(pageIndex, 15);
    QueryBuilder builder = new QueryBuilder(SearchEnginer.postEnginer().getAnalyzer());
    builder.addShould("title", word).addShould("content", word);
    SearchEnginer.postEnginer().searchHighlight(builder, result);

    return result;
  }

  private Document convert(Post post){
    Document doc = new Document();
    doc.add(new Field("id", post.getId() + "", LuceneUtils.directType()));
    doc.add(new Field("title", post.getTitle(), LuceneUtils.searchType()));
    /* 用jsoup剔除html标签和&nbsp;等内容 */
    System.out.println(Jsoup.parse(post.getContent()).text());
    doc.add(new Field("content", Jsoup.parse(post.getContent()).text(), LuceneUtils.searchType()));
    doc.add(new Field("createTime", DateUtils.formatDate("yyyy-MM-dd", post.getCreateTime()), LuceneUtils.storeType()));

    return doc;
  }

}

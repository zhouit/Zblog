package com.zblog.core.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 查询构建器
 * 
 * @author zhou
 * 
 */
public class QueryBuilder{
  private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
  private static int MAX_QUERY_TOKENS = 5;
  /* 最长查询字符串 */
  private static int MAX_QUERY_LENGTH = 20;

  private Analyzer analyzer;
  private List<Term> must;
  private List<Term> should;
  private List<String> lighters;

  private Set<SortField> sorts;
  private Filter filter;

  public QueryBuilder(Analyzer analyzer){
    this.analyzer = analyzer;
    must = new LinkedList<Term>();
    should = new LinkedList<Term>();
    lighters = new ArrayList<String>();
  }

  public QueryBuilder addMust(String field, String value){
    String[] tokens = token(value);
    if(tokens != null && tokens.length > 0){
      for(String token : tokens){
        must.add(new Term(field, token));
      }
    }else{
      logger.warn("must query field word can't analyzer -->" + value);
    }

    return this;
  }

  public QueryBuilder addShould(String field, String value){
    String[] tokens = token(value);
    if(tokens != null && tokens.length > 0){
      for(String token : tokens){
        should.add(new Term(field, token));
      }
    }else{
      logger.warn("should query field word can't analyzer -->" + value);
    }

    return this;
  }

  /**
   * 添加高亮字段
   * 
   * @param fields
   * @return
   */
  public QueryBuilder addLighter(String... fields){
    lighters.addAll(Arrays.asList(fields));

    return this;
  }

  public List<String> getHighlighter(){
    return lighters;
  }

  public QueryBuilder sortBy(SortField field){
    if(sorts == null)
      sorts = new HashSet<SortField>();
    sorts.add(field);

    return this;
  }

  public QueryBuilder filter(Filter filter){
    this.filter = filter;

    return this;
  }

  public Query getQuery(){
    BooleanQuery query = new BooleanQuery();
    for(Term term : must){
      query.add(new TermQuery(term), Occur.MUST);
    }

    for(Term term : should){
      query.add(new TermQuery(term), Occur.SHOULD);
    }

    return query;
  }

  public Filter getFilter(){
    return filter;
  }

  public Sort getSort(){
    return null;
  }

  /**
   * 看新版本文档,tokenStream之后必须reset
   * 
   * @param str
   * @return
   */
  public String[] token(String str){
    str = str.length() > MAX_QUERY_LENGTH ? str.substring(0, MAX_QUERY_LENGTH) : str;

    TokenStream stream = null;
    try{
      List<String> list = new ArrayList<String>();
      /* tokenStream的fieldName随意 */
      stream = analyzer.tokenStream("any", new StringReader(str));
      CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
      stream.reset();
      while(stream.incrementToken() && list.size() <= MAX_QUERY_TOKENS){
        list.add(cta.toString());
      }

      stream.end();
      /* 当没有分词结果时(如停用词，量词等),直接返回null */
      if(list.isEmpty()){
        return null;
      }

      list = reSubset(list);
      String[] result = new String[list.size()];
      return list.toArray(result);
    }catch(IOException e){
      logger.error("analyer " + str + " error", e);
    }finally{
      LuceneUtils.closeQuietly(stream);
    }

    return null;
  }

  /**
   * 剔除子集(例:[视频网站、视频、网站、购物]剔除后变为[视频网站、购物])<br>
   * 其实和最大匹配分词效果一样(IKAnalyzer.setUseSmart(true)),但在添加文档必须使用最细粒度分词
   * 
   * @param source
   * @return
   */
  private List<String> reSubset(List<String> source){
    List<String> result = new ArrayList<String>(source.size());
    for(int i = 0; i < source.size() - 1; i++){
      buildMinHeap(source, source.size() - i - 1);
      Collections.swap(source, 0, source.size() - i - 1);
    }

    result.add(source.get(0));
    for(int i = 1; i < source.size(); i++){
      String item = source.get(i);
      boolean hasSub = false;
      for(String temp : result){
        if(temp.indexOf(item) != -1){
          hasSub = true;
          break;
        }
      }
      if(!hasSub)
        result.add(item);
    }

    return result;
  }

  // 建小顶堆
  private void buildMinHeap(List<String> list, int last){
    // 从最后一个非叶子节点起
    for(int i = (last - 1) / 2; i >= 0; i--){
      int current = i; // 当前非叶节点
      while((2 * current + 1) <= last){ // 如果current存在子节点
        int big = 2 * current + 1;
        if(big < last){ // 如果current节点右子节点存在
          if(list.get(big).length() > list.get(big + 1).length())
            big++;
        }

        if(list.get(current).length() > list.get(big).length()){ // 如果current节点小于它的最大子节点
          Collections.swap(list, current, big);
          // 将big赋值给current，因为修改了big节点值，开始下一层循环
          current = big;
        }else
          break;
      }
    }
  }

}

package com.zblog.core.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库分页组件 在service层构造PageModel,添加查询参数insertQuery 在sql中不用写limit语句
 * 
 * @author zhou
 * 
 */
public class PageModel<T> {
  private static final int PAGE_SIZE = 10;

  private int pageIndex;
  /* 当pageSize小于0时,为查询所有 */
  private int pageSize;
  private long totalCount;

  private List<T> content;
  // 查询参数
  private MapContainer query;

  public PageModel(int pageIndex){
    this(pageIndex, PAGE_SIZE);
  }

  public PageModel(int pageIndex, int pageSize){
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
    this.query = new MapContainer();
    this.content = new ArrayList<T>();
  }

  public int getPageIndex(){
    return pageIndex;
  }

  public int getPageSize(){
    return pageSize;
  }

  public int getTotalPage(){
    int pages = (int) (totalCount / pageSize);
    if(totalCount % pageSize != 0)
      pages++;

    return pages;
  }

  public long getTotalCount(){
    return totalCount;
  }

  public void setTotalCount(long totalCount){
    this.totalCount = totalCount;
  }

  public List<T> getContent(){
    return content;
  }

  public void setContent(List<T> content){
    this.content = content;
  }

  public void addContent(T mc){
    content.add(mc);
  }

  public PageModel<T> insertQuery(String key, Object value){
    query.put(key, value);
    return this;
  }

  public PageModel<T> insertQuerys(MapContainer map){
    if(map != null)
      query.putAll(map);

    return this;
  }

  public MapContainer getQuery(){
    return query;
  }

  public Object removeQuery(String key){
    return query.remove(key);
  }

  public boolean isQueryAll(){
    return pageSize < 1;
  }

  /**
   * 生成查询数量sql
   * 
   * @return
   */
  public String countSql(String sql){
    // int index = query.getSql().indexOf(" from ");
    // String sql = "select count(*) " + query.getSql().substring(index);
    // index = sql.indexOf("order by");
    // sql = index == -1 ? sql : sql.substring(0, index);
    // /* 只要有group by就使用子查询 */
    // if(sql.indexOf("group by") != -1){
    // sql = "select count(*) from ( " + sql + " ) _temp_";
    // }

    return "select count(*) from ( " + sql + " ) _temp_";
  }

  /**
   * 生成分页sql
   * 
   * @return
   */
  public String pageSql(String sql){
    return sql + " limit " + (getPageIndex() - 1) * getPageSize() + "," + getPageSize();
  }

}

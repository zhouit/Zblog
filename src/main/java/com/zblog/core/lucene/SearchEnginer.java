package com.zblog.core.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.FileSwitchDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.zblog.core.WebConstants;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.plugin.PageModel;
import com.zblog.core.util.StringUtils;

/**
 * 搜索引擎服务提供类
 * 
 * @author zhou
 * 
 */
public final class SearchEnginer{
  private static final Logger logger = LoggerFactory.getLogger(SearchEnginer.class);

  private final TrackingIndexWriter nrtWriter;
  private final IndexWriter writer;
  private final ReferenceManager<IndexSearcher> manager;
  private final Analyzer analyzer;
  private final ControlledRealTimeReopenThread<IndexSearcher> nmrt;
  /* 每次IndexReader的reopen都会导致generation增1 */
  private volatile long reopenToken;

  private SearchEnginer(String indexDir){
    try{
      Directory directory = FSDirectory.open(new File(WebConstants.APPLICATION_PATH, indexDir));
      // Directory directory = initDirectory(new
      // File(WebConstants.APPLICATION_PATH,indexDir));
      /* 默认使用最细粒度分词 */
      analyzer = new IKAnalyzer(false);
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
      LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
      // 达到5个文件时就和合并,默认10个
      mergePolicy.setMergeFactor(5);
      iwc.setMergePolicy(mergePolicy);
      iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
      writer = new IndexWriter(directory, iwc);
      nrtWriter = new TrackingIndexWriter(writer);
      manager = new SearcherManager(writer, true, null);

      nmrt = new ControlledRealTimeReopenThread<IndexSearcher>(nrtWriter, manager, 5.0, 0.05);
      nmrt.setName("reopen thread");
      nmrt.start();
    }catch(IOException e){
      throw new IllegalStateException("Lucene index could not be created: " + e.getMessage());
    }
  }

  /**
   * 此方法返回切换Directory<br>
   * 要关闭复合文件格式(除段信息文件，锁文件，以及删除的文件外，其他的一系列索引文件压缩一个后缀名为cfs的文件,默认为关闭)
   * 生成复合文件将消耗更多的时间,但它有更好的查询效率,适合查询多更新少的场合<br>
   * IndexWriterConfig. setUseCompoundFile(false);
   * 
   * @param path
   * @return
   * @throws IOException
   */
  Directory initDirectory(File path) throws IOException{
    // 添加放置在nio文件里的索引文件,由主索引负责打开的文件
    // .fdt文件用于存储具有Store.YES属性的Field的数据；.fdx是一个索引，用于存储Document在.fdt中的位置
    Set<String> files = new HashSet<String>();
    files.add("fdt");
    files.add("fdx");

    Directory dir = FSDirectory.open(path);// 装载磁盘索引
    /* RAMDirectory来访问索引其速度和效率都是非常优异的 */
    RAMDirectory map = new RAMDirectory(dir, IOContext.READ);
    NIOFSDirectory nio = new NIOFSDirectory(path);// 基于并发大文件的NIO索引
    // 组合不同Directory的优点
    FileSwitchDirectory fsd = new FileSwitchDirectory(files, nio, map, true);

    return fsd;
  }

  /**
   * 获取当前的分词器
   * 
   * @return
   */
  public Analyzer getAnalyzer(){
    return analyzer;
  }

  public void insert(Document doc) throws LuceneException{
    try{
      reopenToken = nrtWriter.addDocument(doc);
    }catch(IOException e){
      logger.error("Error while in Lucene index operation: {}", e);
      throw new LuceneException("Error while add index", e);
    }finally{
      commit();
    }
  }

  /**
   * 批量添加文档
   * 
   * @param docs
   * @throws LuceneException
   */
  public void insert(Collection<Document> docs) throws LuceneException{
    try{
      for(Document doc : docs){
        nrtWriter.addDocument(doc);
      }

      reopenToken = nrtWriter.getGeneration();
    }catch(IOException e){
      logger.error("Error while in Lucene index operation: {}", e);
      throw new LuceneException("Error while add index", e);
    }finally{
      commit();
    }
  }

  public void update(Term term, Document doc) throws LuceneException{
    try{
      reopenToken = nrtWriter.updateDocument(term, doc);
    }catch(IOException e){
      logger.error("Error in lucene re-indexing operation: {}", e);
      throw new LuceneException("Error while update index", e);
    }finally{
      commit();
    }
  }

  /**
   * 删除索引
   * 
   * @param term
   * @throws LuceneException
   */
  public void delete(Term term) throws LuceneException{
    try{
      reopenToken = nrtWriter.deleteDocuments(term);
    }catch(IOException e){
      logger.error("Error in lucene re-indexing operation: {}", e);
      throw new LuceneException("Error while remove index", e);
    }finally{
      commit();
    }
  }

  /**
   * 清空索引
   * 
   * @throws LuceneException
   */
  public void truncate() throws LuceneException{
    try{
      reopenToken = nrtWriter.deleteAll();
    }catch(IOException e){
      logger.error("Error truncating lucene index: {}", e);
      throw new LuceneException("Error while tuncate index", e);
    }finally{
      commit();
    }
  }

  /**
   * 获取所有文档数,不包含删除文档数
   * 
   * @return
   */
  public int docCount(){
    /* 注:numDocs()返回为包含删除文档数 */
    return writer.maxDoc();
  }

  /**
   * 与指定文档相似搜索
   * 
   * @param docNum
   *          给定文档编号
   * @param fields
   * @return
   * @throws IOException
   */
  public List<MapContainer> like(int docNum, String[] fields) throws IOException{
    MoreLikeThis mlt = new MoreLikeThis(DirectoryReader.open(writer, false));
    mlt.setFieldNames(fields); // 设定查找域
    mlt.setMinTermFreq(2); // 一篇文档中一个词语至少出现次数，小于这个值的词将被忽略,默认值是2
    mlt.setMinDocFreq(3); // 一个词语最少在多少篇文档中出现，小于这个值的词会将被忽略，默认值是5
    mlt.setAnalyzer(getAnalyzer());
    Query query = mlt.like(docNum);

    return search(query, 5, null);
  }

  public void searchHighlight(QueryBuilder builder, PageModel<MapContainer> model){
    searchHighlight(builder, model, null);
  }

  private ScoreDoc lastScoreDoc(IndexSearcher searcher, QueryBuilder builder, PageModel<MapContainer> model)
      throws IOException{
    if(model.getPageIndex() == 1)
      return null;

    int num = model.getPageSize() * (model.getPageIndex() - 1);
    TopDocs tds = searcher.search(builder.getQuery(), builder.getFilter(), num);
    return tds.scoreDocs[num - 1];
  }

  /**
   * 高亮查询(适合只做下一页搜索)
   * 
   * @param builder
   * @param model
   * @param fields
   */
  public void searchAfterHighlight(QueryBuilder builder, PageModel<MapContainer> model, Set<String> fields){
    IndexSearcher searcher = null;
    try{
      nmrt.waitForGeneration(reopenToken);
      searcher = manager.acquire();
      // 先获取上一页的最后一个元素
      ScoreDoc last = lastScoreDoc(searcher, builder, model);
      // 通过最后一个元素搜索下页的pageSize个元素
      TopDocs docs = searcher.searchAfter(last, builder.getQuery(), model.getPageSize());
      model.setTotalCount(docs.totalHits);
      for(ScoreDoc sd : docs.scoreDocs){
        Document doc = null;
        if(fields == null || fields.isEmpty())
          doc = searcher.doc(sd.doc);
        else
          doc = searcher.doc(sd.doc, fields);

        MapContainer mc = DocConverter.convert(doc, builder.getHighlighter());
        for(String filter : builder.getHighlighter()){
          String content = doc.get(filter);
          if(content == null || content.trim().length() == 0){
            logger.warn("field " + filter + " is null");
            continue;
          }

          mc.put(filter, getBestFragment(builder.getQuery(), content, filter));
        }

        model.addContent(mc);
      }

    }catch(Exception e){
      logger.error(e.getMessage(), e);
    }finally{
      release(searcher);
    }
  }

  /**
   * 高亮再查询
   * 
   * @param builder
   *          查询构建器
   * @param model
   *          分页数据模型
   * @param fields
   *          需要从Document中获取的字段,为空时为全部获取
   */
  public void searchHighlight(QueryBuilder builder, PageModel<MapContainer> model, Set<String> fields){
    IndexSearcher searcher = null;
    try{
      nmrt.waitForGeneration(reopenToken);
      searcher = manager.acquire();
      TopDocs docs = searcher.search(builder.getQuery(), builder.getFilter(), Integer.MAX_VALUE);
      model.setTotalCount(docs.totalHits);
      ScoreDoc[] sd = docs.scoreDocs;

      int start = (model.getPageIndex() - 1) * model.getPageSize();
      int end = model.getPageIndex() * model.getPageSize();
      for(int i = start; i < end && i < sd.length; i++){
        Document doc = null;
        if(fields == null || fields.isEmpty())
          doc = searcher.doc(sd[i].doc);
        else
          doc = searcher.doc(sd[i].doc, fields);

        MapContainer mc = DocConverter.convert(doc, builder.getHighlighter());
        for(String filter : builder.getHighlighter()){
          String content = doc.get(filter);
          if(content == null || content.trim().length() == 0){
            logger.warn("field " + filter + " is null");
            continue;
          }

          mc.put(filter, getBestFragment(builder.getQuery(), content, filter));
        }

        model.addContent(mc);
      }

    }catch(Exception e){
      logger.error(e.getMessage(), e);
    }finally{
      release(searcher);
    }
  }

  /**
   * 获取高亮文本
   * 
   * @param query
   * @param target
   * @param fieldName
   * @return
   * @throws Exception
   */
  private String getBestFragment(Query query, String target, String fieldName) throws Exception{
    Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
    Scorer scorer = new QueryScorer(query);
    Highlighter hlighter = new Highlighter(formatter, scorer);

    // 设置文本摘要大小
    Fragmenter fg = new SimpleFragmenter(200);
    hlighter.setTextFragmenter(fg);

    String result = hlighter.getBestFragment(analyzer, fieldName, target);
    result = StringUtils.isBlank(result) ? target.substring(0, Math.min(200, target.length())) : result;
    return result;
  }

  /**
   * 搜索指定域的文档
   * 
   * @param query
   * @param max
   * @param fields
   * @return
   */
  private List<MapContainer> search(Query query, int max, Set<String> fields){
    List<MapContainer> result = new LinkedList<MapContainer>();
    IndexSearcher searcher = null;
    try{
      nmrt.waitForGeneration(reopenToken);
      searcher = manager.acquire();
      TopDocs docs = searcher.search(query, max);
      ScoreDoc[] sd = docs.scoreDocs;

      for(int i = 0; i < docs.totalHits; i++){
        Document doc = null;
        if(fields == null || fields.isEmpty())
          doc = searcher.doc(sd[i].doc);
        else
          doc = searcher.doc(sd[i].doc, fields);

        MapContainer mc = DocConverter.convert(doc);
        result.add(mc);
      }

    }catch(Exception e){
      logger.error(e.getMessage(), e);
    }finally{
      release(searcher);
    }

    return result;
  }

  private void commit(){
    try{
      writer.commit();
    }catch(IOException e){
      logger.error("Error commit index");
    }
  }

  private void release(IndexSearcher searcher){
    if(searcher == null)
      return;

    try{
      manager.release(searcher);
    }catch(IOException e){
      logger.error("Error releaser IndexSearcher");
    }
  }

  @Override
  protected void finalize() throws Throwable{
    shutdwon();

    super.finalize();
  }

  public void shutdwon(){
    try{
      nmrt.interrupt();
      nmrt.close();
      writer.commit();
      writer.close();
    }catch(Exception e){
      logger.error("Error while closing lucene index: {}", e);
    }
  }

  /**
   * 这个方法尽量不要调用，除非极少的索引维护
   */
  public void optimize(){
    try{
      writer.forceMerge(1);
    }catch(IOException e){
      logger.error("Error optimizing lucene index {}", e);
    }
  }

  private static final class SearchEnginerHolder{
    static SearchEnginer POST = new SearchEnginer("post/index");
  }

  public static SearchEnginer postEnginer(){
    return SearchEnginerHolder.POST;
  }

}

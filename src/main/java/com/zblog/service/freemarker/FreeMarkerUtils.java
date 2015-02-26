package com.zblog.service.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zblog.common.util.constants.Constants;

import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtils{
  private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtils.class);

  static Configuration config;

  static{
    try{
      config = new Configuration();
      config.setCacheStorage(new NullCacheStorage());
      config.setAutoFlush(false);
      config.setOutputEncoding(Constants.ENCODING_UTF_8);
      URL uri = FreeMarkerUtils.class.getResource("ftl");
      config.setDirectoryForTemplateLoading(new File(uri.toURI()));
    }catch(Exception e){
      throw new ExceptionInInitializerError(e);
    }
  }

  public static boolean genHtml(String name, File out, Object param){
    boolean result = true;
    OutputStreamWriter writer = null;
    try{
      Template template = config.getTemplate(name, Constants.ENCODING_UTF_8);
      File parent = out.getParentFile();
      if(!parent.exists())
        parent.mkdirs();
      writer = new OutputStreamWriter(new FileOutputStream(out), Constants.ENCODING_UTF_8);
      template.process(param, writer);
    }catch(Exception e){
      logger.error("genHtml error-->" + name + " -out " + out.getAbsolutePath() + " param-->" + param);
      result = false;
      /* 静态化失败,则删除文件 */
      out.delete();
      throw new FreeMarkerDataAccessException("genHtml error " + name, e);
    }finally{
      IOUtils.closeQuietly(writer);
    }

    return result;
  }

}

package com.zblog.common.util;

/**
 * 文件工具类
 * 
 * @author zhou
 * 
 */
public class FileUtils{

  private FileUtils(){
  }

  /**
   * 获取文件名后缀(不含".")
   * 
   * @param filename
   * @return
   */
  public static String getFileExt(String filename){
    int point = filename.lastIndexOf(".");
    return filename.substring(point + 1);
  }

  /**
   * 获取文件名(不包含后缀)
   * 
   * @param filename
   * @return
   */
  public static String getFileName(String filename){
    int point = filename.lastIndexOf(".");
    return filename.substring(0, point);
  }

}

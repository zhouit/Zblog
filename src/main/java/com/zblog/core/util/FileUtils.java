package com.zblog.core.util;

import java.util.Arrays;

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

  /**
   * 判断指定格式是否为图片
   * 
   * @param ext
   * @return
   */
  public static boolean isImageExt(String ext){
    return ext != null && Arrays.asList("jpg", "jpeg", "png", "bmp", "gif").contains(ext.toLowerCase());
  }

}

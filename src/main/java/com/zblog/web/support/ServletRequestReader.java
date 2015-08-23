package com.zblog.web.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zblog.core.util.ServletUtils;

/**
 * Servlet参数提取工具
 * 
 * @author zhou
 * 
 */
public class ServletRequestReader{
  ServletRequest request;

  public ServletRequestReader(ServletRequest request){
    this.request = request;
  }

  public boolean hasParam(String key){
    return request.getParameter(key) != null;
  }

  public String getAsString(String name){
    return request.getParameter(name);
  }

  public String[] getAsStrings(String name){
    return request.getParameterValues(name);
  }

  public Date getAsDate(String key){
    return getAsDate(key, "yyyy-MM-dd HH:mm:ss");
  }

  public Date getAsDate(String key, String pattern){
    String value = request.getParameter(key);
    if(value == null)
      return null;

    Date result = null;
    try{
      SimpleDateFormat format = new SimpleDateFormat(pattern);
      result = format.parse(value.toString());
    }catch(ParseException e){
      e.printStackTrace();
    }

    return result;
  }

  public int getAsInteger(String key, int defaults){
    String value = request.getParameter(key);
    if(value == null)
      return defaults;

    return Integer.parseInt(value.toString());
  }

  public int getAsInteger(String key){
    return getAsInteger(key, 0);
  }

  public int[] getAsIntegers(String key){
    String[] values = request.getParameterValues(key);
    int[] result = new int[values.length];
    for(int i = 0; i < values.length; i++){
      result[i] = Integer.parseInt(values[i]);
    }

    return result;
  }

  public boolean getAsBoolean(String key){
    return getAsBoolean(key, false);
  }

  public boolean getAsBoolean(String key, boolean defaults){
    String value = request.getParameter(key);
    if(value == null)
      return defaults;

    return Boolean.parseBoolean(value.toString());
  }

  // 获取基于虚拟路径的绝对路径
  public String getRealPath(String path){
    // 注: request.getServletContext().getRealPath(path);在Servlet3.0规范下才定义的
    return request.getServletContext().getRealPath(path);
  }

  public String getDomain(){
    return ServletUtils.getDomain((HttpServletRequest) request);
  }

  public Map<String, String> getQuerys(){
    Map<String, String> result = new HashMap<String, String>();

    Enumeration<String> params = (Enumeration<String>) request.getParameterNames();
    while(params.hasMoreElements()){
      String param = params.nextElement();
      String value = request.getParameter(param);
      if(value != null && !"".equals(value.trim())){
        result.put(param, value);
      }
    }

    return result;
  }

  /**
   * 获取上传文件名
   * 
   * @param field
   * @param containExt
   *          是否包含后缀
   * @return
   */
  public String getUploadName(String field, boolean containExt){
    String fileName = null;
    if(request instanceof MultipartHttpServletRequest){
      MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
      fileName = mhsr.getFile(field).getOriginalFilename();
    }else{
      fileName = request.getParameter(fileName);
    }

    if(fileName != null && !containExt){
      int point = fileName.lastIndexOf(".");
      fileName = fileName.substring(0, point);
    }

    return fileName;
  }

  public MultipartFile getFile(String field){
    MultipartHttpServletRequest mphsr = (MultipartHttpServletRequest) request;
    return mphsr.getFile(field);
  }

}

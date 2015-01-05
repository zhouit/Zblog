package com.zblog.common.plugin;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MapContainer extends LinkedHashMap<String, Object>{
  private static final long serialVersionUID = 1L;

  public MapContainer(){
  }

  public MapContainer(String key, Object value){
    super();
    put(key, value);
  }

  public <T> T get(String key, Class<T> clazz){
    return (T) get(key);
  }

  @Override
  public MapContainer put(String key, Object value){
    super.put(key, value);
    return this;
  }

  public static MapContainer convert(Map<String, ? extends Object> map){
    MapContainer container = new MapContainer();
    container.putAll(map);
    return container;
  }

  @Override
  public MapContainer clone(){
    MapContainer clone = new MapContainer();
    clone.putAll(this);
    return clone;
  }

  public String getAsString(String key){
    Object value = get(key);
    if(value == null)
      return "";

    return value.toString();
  }

  public Timestamp getAsTimestamp(String key){
    Object value = get(key);
    if(value == null)
      return null;

    return Timestamp.valueOf(value.toString());
  }

  /**
   * 以yyyy-MM-dd HH:mm:ss格式获取日期
   * 
   * @param key
   * @return
   */
  public Date getAsDate(String key){
    return getAsDate(key, "yyyy-MM-dd HH:mm:ss");
  }

  public Date getAsDate(String key, String pattern){
    Object value = get(key);
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
    Object value = get(key);
    if(value == null)
      return defaults;

    return Integer.parseInt(value.toString());
  }

  public int getAsInteger(String key){
    return getAsInteger(key, 0);
  }

  public int removeAsInteger(String key, int defaults){
    Object value = remove(key);
    if(value == null){
      return defaults;
    }

    return Integer.parseInt(value.toString());
  }

  public int removeAsInteger(String key){
    return removeAsInteger(key, 0);
  }

  public Boolean getAsBoolean(String key){
    Object value = get(key);
    if(value == null)
      return null;

    return Boolean.parseBoolean(value.toString());
  }

  public boolean getAsBoolean(String key, boolean defaults){
    Object value = get(key);
    if(value == null)
      return defaults;

    return Boolean.parseBoolean(value.toString());
  }

  public long getAsLong(String key, long defaults){
    Object value = get(key);
    if(value == null)
      return defaults;

    return Long.parseLong(value.toString());
  }

  public long getAsLong(String key){
    return getAsLong(key, 0);
  }

  /**
   * 注:当key对应的键值不存在时,会put入一个List
   * 
   * @param key
   * @param clazz
   * @return
   */
  public <T> List<T> getAsList(String key, Class<T> clazz){
    List<T> result = (List<T>) get(key);
    if(result == null){
      result = new LinkedList<T>();
      this.put(key, result);
    }

    return result;
  }

}

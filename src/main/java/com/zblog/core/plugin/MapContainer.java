package com.zblog.core.plugin;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zblog.core.util.DateUtils;

/**
 * 提供方法链功能和增强的Map&lt;String,Object&gt;实现
 * 
 * @author zhou
 *
 */
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
    return (T) super.get(key);
  }

  /**
   * 此方法和getAs方法区别为getAs为把对应键值转化为字符串再parse，此方法为直接获取值进行强转
   * 
   * @param key
   * @return
   */
  public <T> T get(String key){
    return (T) super.get(key);
  }

  @Override
  public MapContainer put(String key, Object value){
    super.put(key, value);
    return this;
  }

  /**
   * 缺少即加入,和ConcurrentHashMap中的putIfAbsent一致
   * 
   * @param key
   * @param value
   * @return
   */
  public <T> T putIfAbsent(String key, T value){
    T result = value;
    if(containsKey(key)){
      result = get(key);
    }else{
      put(key, value);
    }

    return result;
  }

  /**
   * 将指定对象中的所有Map实现MapContainer化，使用时注意返回值
   * 
   * @param obj
   * @return
   */
  public static <T> T from(Object obj){
    if(obj == null)
      return null;

    Object result = obj;
    if(Map.class.isInstance(obj)){
      MapContainer temp = MapContainer.class.isInstance(obj) ? (MapContainer) obj : new MapContainer();
      for(Map.Entry<String, Object> entry : ((Map<String, Object>) obj).entrySet()){
        temp.put(entry.getKey(), from(entry.getValue()));
      }

      result = temp;
    }else if(Collection.class.isInstance(obj)){
      Collection<Object> collect = (Collection<Object>) obj;
      if(!collect.isEmpty()){
        List<Object> copy = new ArrayList<Object>(collect.size());
        Iterator<Object> it = collect.iterator();
        while(it.hasNext()){
          copy.add(from(it.next()));
          it.remove();
        }

        collect.addAll(copy);
      }
    }else if(obj.getClass().isArray()){
      for(int i = 0; i < Array.getLength(obj); i++){
        Array.set(obj, i, from(Array.get(obj, i)));
      }
    }

    return (T) result;
  }

  @Override
  public MapContainer clone(){
    MapContainer clone = new MapContainer();
    clone.putAll(this);
    return clone;
  }

  public String getAsString(String key){
    return getAsString(key, null);
  }

  public String getAsString(String key, String defaults){
    Object value = get(key);
    if(value == null)
      return defaults;

    return value.toString();
  }

  public int getAsInteger(String key){
    return getAsInteger(key, 0);
  }

  public int getAsInteger(String key, int defaults){
    Object value = get(key);
    if(value == null)
      return defaults;

    return Integer.parseInt(value.toString());
  }

  public boolean getAsBoolean(String key){
    return getAsBoolean(key, false);
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
   * 以yyyy-MM-dd HH:mm:ss格式获取日期
   * 
   * @param key
   * @return
   */
  public Date getAsDate(String key){
    return getAsDate(key, "yyyy-MM-dd HH:mm:ss");
  }

  public Date getAsDate(String key, String pattern){
    return getAsDate(key, pattern, null);
  }

  public Date getAsDate(String key, String pattern, Date defaults){
    Object value = get(key);
    if(value == null)
      return defaults;

    return DateUtils.parse(pattern, value.toString());
  }

  public Timestamp getAsTimestamp(String key){
    Object value = get(key);
    if(value == null)
      return null;

    return Timestamp.valueOf(value.toString());
  }

}

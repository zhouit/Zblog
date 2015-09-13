package com.zblog.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class CollectionUtils{

  private CollectionUtils(){
  }

  public static boolean isEmpty(Collection<?> collect){
    return collect == null || collect.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map){
    return (map == null) || map.isEmpty();
  }

  public static <T> boolean isEmpty(T[] array){
    return array == null || array.length == 0;
  }

  public static <K, V> K getKeyByValue(Map<K, V> map, V value){
    K result = null;
    for(Map.Entry<K, V> entry : map.entrySet()){
      if(Objects.equals(entry.getValue(), value)){
        result = entry.getKey();
        break;
      }
    }

    return result;
  }

}

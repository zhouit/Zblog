package com.zblog.core.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils{

  private CollectionUtils(){
  }

  public static boolean isEmpty(Collection<?> collect){
    return collect == null || collect.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map){
    return (map == null) || map.isEmpty();
  }

}

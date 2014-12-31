package com.zblog.common.util;

import java.util.Collection;

public class CollectionUtils{

  private CollectionUtils(){
  }

  public static boolean isEmpty(Collection<?> collect){
    return collect == null || collect.isEmpty();
  }

}

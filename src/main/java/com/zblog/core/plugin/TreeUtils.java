package com.zblog.core.plugin;

import java.util.Iterator;
import java.util.List;

import com.zblog.core.util.StringUtils;

public class TreeUtils{

  private TreeUtils(){
  }

  /**
   * 根据指定的list构建森林
   * 
   * @param list
   */
  public static <T extends TreeItem<T>> void rebuildTree(List<T> list){
    while(!solve(list)){
      Iterator<T> it = list.iterator();
      while(it.hasNext()){
        T item = it.next();
        if(hasChild(list, item.getId()))
          continue;

        if(!StringUtils.isBlank(item.getParent())){
          /* 获取item父节点 */
          for(T temp : list){
            if(item.getParent().equals(temp.getId())){
              it.remove();
              temp.addChild(item);
              break;
            }
          }
        }
      }
    }
  }

  /**
   * 指定id是否有子节点
   * 
   * @param list
   * @param id
   * @return
   */
  private static <T extends TreeItem<T>> boolean hasChild(List<T> list, String id){
    for(T mc : list){
      if(id.equals(mc.getParent()))
        return true;
    }
    return false;
  }

  /**
   * 如果在当前所有顶级节点中没有任何一个有子节点，就意味着森林构建成功
   * 
   * @param list
   * @return
   */
  private static <T extends TreeItem<T>> boolean solve(List<T> list){
    for(T mc : list){
      if(hasChild(list, mc.getId()))
        return false;
    }

    return true;
  }

}

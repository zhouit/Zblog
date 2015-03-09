package com.zblog.core.plugin;

import java.util.Iterator;
import java.util.List;

import com.zblog.core.util.StringUtils;

public class TreeUtils{

  private TreeUtils(){
  }

  /**
   * 根据一棵树的先序遍历集合还原成一颗树
   * 
   * @param preOrder
   * @return
   */
  public static MapContainer buildTreefromPreOrder(List<MapContainer> preOrder){
    // MapContainer root=preOrder.remove(0);
    // for(MapContainer current:preOrder){
    // int level=current.removeAsInteger("level");
    // current.getAsList("attributes", Integer.class).add(level-1);
    // MapContainer parent=getLastParentByLevel(root, level-1);
    // parent.getAsList("children", MapContainer.class).add(current);
    // }

    MapContainer root = preOrder.get(0).clone();
    for(int i = 1; i < preOrder.size(); i++){
      MapContainer current = preOrder.get(i).clone();
      int level = current.removeAsInteger("level");
      if(level == 2)
        current.put("icon", "glyphicon glyphicon-star");
      current.put("level", level - 1);
      MapContainer parent = getLastParentByLevel(root, level - 1);
      parent.getAsList("nodes", MapContainer.class).add(current);
    }

    return root;
  }

  private static MapContainer getLastParentByLevel(MapContainer mc, int currentlevel){
    MapContainer current = mc;
    for(int i = 1; i < currentlevel; i++){
      List<MapContainer> children = current.getAsList("nodes", MapContainer.class);
      current = children.get(children.size() - 1);
    }

    return current;
  }

  public static void rebuildTree(List<MapContainer> list){
    /* 原理就是把list中parent不为Root的元素全删除 */
    while(!solve(list)){
      Iterator<MapContainer> it = list.iterator();
      while(it.hasNext()){
        MapContainer item = it.next();
        if(!hasChild(list, item.getAsString("id"))){
          if(!StringUtils.isBlank(item.getAsString("parent"))){
            MapContainer parent = getParent(list, item);
            /* 有可能该条评论的parent以前被批准后又被驳回/删除 */
            if(parent != null){
              it.remove();
              parent.getAsList("children", MapContainer.class).add(item);
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
  private static boolean hasChild(List<MapContainer> list, String id){
    for(MapContainer mc : list){
      if(id.equals(mc.get("parent")))
        return true;
    }
    return false;
  }

  /**
   * 获取item的父节点
   * 
   * @param list
   * @param item
   * @return
   */
  private static MapContainer getParent(List<MapContainer> list, MapContainer item){
    for(MapContainer mc : list){
      if(item.getAsString("parent").equals(mc.get("id")))
        return mc;
    }

    return null;
  }

  /**
   * 如果在当前所有顶级节点中没有任何一个有子节点，就意味着森林构建成功
   * 
   * @param list
   * @return
   */
  private static boolean solve(List<MapContainer> list){
    for(MapContainer mc : list){
      if(hasChild(list, mc.getAsString("id")))
        return false;
    }

    return true;
  }

}

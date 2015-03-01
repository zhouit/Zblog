package com.zblog.core.plugin;

import java.util.List;

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

}

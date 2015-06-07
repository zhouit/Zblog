package com.zblog.core.plugin;

import java.util.Collection;

public interface TreeItem<T> {

  public String getId();

  public String getParent();

  public Collection<T> getChildren();

  public void addChild(T child);
  
}

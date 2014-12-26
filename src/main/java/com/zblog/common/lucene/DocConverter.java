package com.zblog.common.lucene;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import com.zblog.common.plugin.MapContainer;

public class DocConverter{
  
  private DocConverter(){}

  public static MapContainer convert(Document obj){
    MapContainer mc = new MapContainer();
    for(IndexableField field : obj.getFields()){
      mc.put(field.name(), field.stringValue());
    }

    return mc;
  }

  public static MapContainer convert(Document obj, String... filters){
    MapContainer mc = new MapContainer();
    List<String> list = Arrays.asList(filters);
    for(IndexableField field : obj.getFields()){
      if(list.contains(field.name()))
        continue;
      mc.put(field.name(), field.stringValue());
    }

    return mc;
  }

}

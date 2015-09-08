package com.zblog.web.backend.form.validator;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.StringUtils;

public class CategoryFormValidator{

  public static MapContainer validateInsert(Category category){
    MapContainer form = new MapContainer();
    if(StringUtils.isBlank(category.getName())){
      form.put("msg", "分类名称不能为空");
    }

    return form;
  }

}

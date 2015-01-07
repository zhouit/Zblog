package com.zblog.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zblog.backend.form.GeneralOption;
import com.zblog.backend.form.PostOption;
import com.zblog.common.util.NumberUtils;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.OptionConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.service.OptionsService;

@Component
public class OptionManager{
  @Autowired
  private OptionsService optionsService;

  /**
   * 更新网站基础设置，同时更新WebConstants中变量
   * 
   * @param form
   */
  @Transactional
  public void updateGeneralOption(GeneralOption form){
    optionsService.updateOptionValue(OptionConstants.TITLE, form.getTitle());
    optionsService.updateOptionValue(OptionConstants.SUBTITLE, form.getSubtitle());
    optionsService.updateOptionValue("weburl", form.getWeburl());

    WebConstants.init(form.getTitle(), form.getSubtitle());
  }

  @Transactional
  public void updatePostOption(PostOption form){
    optionsService.updateOptionValue("maxshow", form.getMaxshow() + "");
    optionsService.updateOptionValue(OptionConstants.DEFAULT_CATEGORY_ID, form.getDefaultCategory());
  }

  /**
   * 从数据库中获取站点通用设置,不存在时返回null
   * 
   * @return
   */
  public GeneralOption getGeneralOption(){
    GeneralOption form = new GeneralOption();
    form.setTitle(optionsService.getOptionValue(OptionConstants.TITLE));
    if(!StringUtils.isBlank(form.getTitle())){
      form.setSubtitle(optionsService.getOptionValue(OptionConstants.SUBTITLE));
      form.setWeburl(optionsService.getOptionValue("weburl"));
    }else{
      form = null;
    }

    return form;
  }

  public PostOption getPostOption(){
    PostOption option = new PostOption();
    option.setMaxshow(NumberUtils.toInteger(optionsService.getOptionValue("maxshow"), 10));
    option.setDefaultCategory(optionsService.getOptionValue(OptionConstants.DEFAULT_CATEGORY_ID));

    return option;
  }

}

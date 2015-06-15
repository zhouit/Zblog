package com.zblog.web.support;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UrlPathHelper;

import com.zblog.core.util.CollectionUtils;

/**
 * 解决@PathVariable注解造成的xss攻击问题,注意:此类必需由WebApplicationContext初始化
 * 
 * @author zhou
 *
 */
public class XssHandlerMappingPostProcessor implements BeanPostProcessor{

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
    if(bean instanceof AbstractHandlerMapping){
      AbstractHandlerMapping ahm = (AbstractHandlerMapping) bean;
      ahm.setUrlPathHelper(new XssUrlPathHelper());
    }

    return bean;
  }

  static class XssUrlPathHelper extends UrlPathHelper{

    @Override
    public Map<String, String> decodePathVariables(HttpServletRequest request, Map<String, String> vars){
      Map<String, String> result = super.decodePathVariables(request, vars);
      if(!CollectionUtils.isEmpty(result)){
        for(String key : result.keySet()){
          result.put(key, cleanXSS(result.get(key)));
        }
      }

      return result;
    }

    @Override
    public MultiValueMap<String, String> decodeMatrixVariables(HttpServletRequest request,
        MultiValueMap<String, String> vars){
      MultiValueMap<String, String> mvm = super.decodeMatrixVariables(request, vars);
      if(!CollectionUtils.isEmpty(mvm)){
        for(String key : mvm.keySet()){
          List<String> value = mvm.get(key);
          for(int i = 0; i < value.size(); i++){
            value.set(i, cleanXSS(value.get(i)));
          }
        }
      }

      return mvm;
    }

    private String cleanXSS(String value){
      return HtmlUtils.htmlEscape(value);
    }

  }

}

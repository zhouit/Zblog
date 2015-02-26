package com.zblog.service.shiro;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * <p>
 * Shiro提供了Spring AOP集成用于权限注解的解析和验证,<br/>
 * but注解只支持方法上,不支持基于类的注解(见AuthorizationAttributeSourceAdvisor.match)。
 * </p>
 * <p>
 * 这里扩展,注:AnnotationResolver.getAnnotation的实现默实现(SpringAnnotationResolver)
 * 为先查找方法上的注解，找不到再查找类上面的注解<br>
 * 即实现:若方法注解上的权限不满足，不会再判断类方法上的注解权限。同样你可以实现其它需要的注解组合策略
 * </p>
 * 
 * @author zhou
 * 
 */
public class EnhanceAuthorizationAdvisor extends AuthorizationAttributeSourceAdvisor{
  private static final long serialVersionUID = 1L;

  private static final List<Class<? extends Annotation>> AUTHZ_ANNOTATION_CLASSES = Arrays.asList(
      RequiresPermissions.class, RequiresRoles.class, RequiresUser.class, RequiresGuest.class,
      RequiresAuthentication.class);

  @Override
  @SuppressWarnings("rawtypes")
  public boolean matches(Method method, Class targetClass){
    return super.matches(method, targetClass) || isAuthzAnnotationPresent(method.getDeclaringClass());
  }

  private boolean isAuthzAnnotationPresent(Class<?> clazz){
    for(Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES){
      Annotation a = AnnotationUtils.findAnnotation(clazz, annClass);
      if(a != null){
        return true;
      }
    }
    return false;
  }

}

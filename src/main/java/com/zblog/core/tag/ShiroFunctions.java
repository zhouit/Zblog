package com.zblog.core.tag;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

/**
 * shiro函数表达式支持
 * 
 * @author zhou
 *
 */
public class ShiroFunctions{
  
  public static PrincipalCollection getPrincipals(){
    return SecurityUtils.getSubject().getPrincipals();
  }

  public static boolean hasAllRoles(final String commaDelimitedRoleNames){
    Subject subject = SecurityUtils.getSubject();
    Set<String> roleNameSet = StringUtils.splitToSet(commaDelimitedRoleNames,
        String.valueOf(StringUtils.DEFAULT_DELIMITER_CHAR));
    return subject.hasAllRoles(roleNameSet);
  }

  public static boolean hasAnyRoles(final String commaDelimitedRoleNames){
    final Subject subject = SecurityUtils.getSubject();
    if(CollectionUtils.isEmpty(subject.getPrincipals())){
      return false;
    }

    boolean hasAnyRole = false;
    final String[] roleNames = StringUtils.split(commaDelimitedRoleNames);
    for(final String roleName : roleNames){
      if(subject.hasRole(roleName)){
        hasAnyRole = true;
        break;
      }
    }

    return hasAnyRole;
  }

  public static boolean hasRole(final String roleName){
    return SecurityUtils.getSubject().hasRole(roleName);
  }

  public static boolean isAuthenticated(){
    return SecurityUtils.getSubject().isAuthenticated();
  }

  public static boolean isGuest(){
    return CollectionUtils.isEmpty(SecurityUtils.getSubject().getPrincipals());
  }

  public static boolean isPermitted(final String permission){
    return SecurityUtils.getSubject().isPermitted(permission);
  }

  public static boolean isPermittedAll(final String commaDelimitedPermissions){
    return SecurityUtils.getSubject().isPermittedAll(StringUtils.split(commaDelimitedPermissions));
  }

  public static boolean isPermittedAny(final String commaDelimitedPermissions){
    final Subject subject = SecurityUtils.getSubject();
    if(CollectionUtils.isEmpty(subject.getPrincipals())){
      return false;
    }

    boolean hasAnyPermission = false;
    final String[] permissions = StringUtils.split(commaDelimitedPermissions);
    for(final String permission : permissions){
      if(subject.isPermitted(permission)){
        hasAnyPermission = true;
        break;
      }
    }

    return hasAnyPermission;
  }

  public static boolean isRemembered(){
    return SecurityUtils.getSubject().isRemembered();
  }

  public static boolean isRunAs(){
    return SecurityUtils.getSubject().isRunAs();
  }

  public static boolean isUser(){
    return !CollectionUtils.isEmpty(SecurityUtils.getSubject().getPrincipals());
  }

  public static boolean lacksPermission(final String permission){
    final Subject subject = SecurityUtils.getSubject();
    return !subject.isPermitted(permission);
  }

  public static boolean lacksRole(final String roleName){
    final Subject subject = SecurityUtils.getSubject();
    return !subject.hasRole(roleName);
  }

  public static boolean notAuthenticated(){
    Subject subject = SecurityUtils.getSubject();
    return !subject.isAuthenticated();
  }

  private ShiroFunctions(){
  }

}

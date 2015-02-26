package com.zblog.service.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 如果之后调用Subject.getSession()将抛出DisabledSessionException异常
 * 
 * @author zhou
 * 
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory{
  
  public Subject createSubject(SubjectContext context){
    // 不创建session
    context.setSessionCreationEnabled(false);
    return super.createSubject(context);
  }

}
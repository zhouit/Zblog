package com.zblog.service.vo;

import java.util.List;

public class MailVO{
  private String from;
  private List<String> to;
  private String subject;
  private String content;

  public String getFrom(){
    return from;
  }

  public void setFrom(String from){
    this.from = from;
  }

  public List<String> getTo(){
    return to;
  }

  public void setTo(List<String> to){
    this.to = to;
  }

  public String getSubject(){
    return subject;
  }

  public void setSubject(String subject){
    this.subject = subject;
  }

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

}

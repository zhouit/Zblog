package com.zblog.common.tag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.zblog.common.util.IdGenarater;
import com.zblog.common.util.constants.Constants;

public class CSRFTagSupport extends TagSupport{
  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException{
    JspWriter writer = pageContext.getOut();
    String csrfInput = "<input type='hidden' id='csrf-token' name='" + Constants.CRSF_TOKEN + "' value='"
        + getTokenForSession() + "' />";
    try{
      writer.write(csrfInput);
    }catch(IOException e){
      e.printStackTrace();
    }

    return super.doStartTag();
  }

  private String getTokenForSession(){
    String token = null;
    HttpSession session = pageContext.getSession();
    synchronized(session){
      token = (String) session.getAttribute(Constants.CRSF_TOKEN);
      if(token == null){
        token = IdGenarater.uuid32();
        session.setAttribute(Constants.CRSF_TOKEN, token);
      }
    }

    return token;
  }

}

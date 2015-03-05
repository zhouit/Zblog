package com.zblog.web.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redstone.xmlrpc.XmlRpcServer;

import com.zblog.biz.MetaWeblogManager;

/**
 * <a href="http://www.xmlrpc.com/metaWeblogApi">MetaWeblog API</a>
 * 
 * @author zhou
 * 
 */
@Controller
public class MetaWeblogController{
  XmlRpcServer server = new XmlRpcServer();

  @Autowired
  public void setMetaWeblogManager(MetaWeblogManager manager){
    server.addInvocationHandler("blogger", manager);
    server.addInvocationHandler("metaWeblog", manager);
  }

  @RequestMapping(value = "/xmlrpc", method = RequestMethod.POST)
  public void xmlrpc(HttpServletRequest request, HttpServletResponse response) throws Exception{
    response.setContentType("text/xml");
    server.execute(request.getInputStream(), response.getWriter());
    response.getWriter().flush();
  }

}

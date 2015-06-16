package com.zblog.web.front.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import redstone.xmlrpc.XmlRpcServer;

import com.zblog.biz.MetaWeblogManager;
import com.zblog.core.WebConstants;
import com.zblog.core.dal.constants.OptionConstants;
import com.zblog.core.util.ServletUtils;
import com.zblog.service.OptionsService;

/**
 * <a href="http://www.xmlrpc.com/metaWeblogApi">MetaWeblog API</a>
 * 
 * @author zhou
 * 
 */
@Controller
@RequestMapping("/xmlrpc")
public class MetaWeblogController{
  private XmlRpcServer server = new XmlRpcServer();
  @Autowired
  private OptionsService optionsService;

  @Autowired
  public void setMetaWeblogManager(MetaWeblogManager manager){
    server.addInvocationHandler("blogger", manager);
    server.addInvocationHandler("metaWeblog", manager);
  }

  /**
   * 输出XMLRPC描述信息
   * 
   * @param response
   */
  @RequestMapping("/rsd")
  public void rsd(HttpServletResponse response){
    response.setContentType("text/xml");

    StringBuilder xml = new StringBuilder();
    xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
    xml.append("<rsd version=\"1.0\" xmlns=\"http://archipelago.phrasewise.com/rsd\">\n");
    xml.append("<service>\n");
    xml.append("<engineName>Zblog</engineName>\n");
    xml.append("<engineLink>" + WebConstants.getDomainLink("/") + "</engineLink>\n");
    xml.append("<homePageLink>" + WebConstants.getDomainLink("/") + "</homePageLink>\n");
    xml.append("<apis><api name=\"MetaWeblog\" preferred=\"true\" apiLink=\"");
    xml.append(WebConstants.getDomainLink("/xmlrpc"));
    xml.append("\"  blogID=\"1\" /></apis>\n");
    xml.append("</service>\n");
    xml.append("</rsd>");
    ServletUtils.sendText(response, xml.toString());
  }

  /**
   * Just for Live Writer
   * 
   * @param response
   */
  @RequestMapping("/wlwmanifest")
  public void wlwmanifest(HttpServletResponse response){
    response.setContentType("text/xml");

    StringBuilder xml = new StringBuilder();
    xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
    xml.append("<manifest xmlns=\"http://schemas.microsoft.com/wlw/manifest/weblog\">\n");
    xml.append("<options><clientType>Metaweblog</clientType><supportsKeywords>Yes</supportsKeywords><supportsNewCategories>Yes</supportsNewCategories><supportsSlug>Yes</supportsSlug><supportsAuthor>Yes</supportsAuthor><supportsGetTags>Yes</supportsGetTags></options>");
    xml.append("<weblog>\n");
    xml.append("<serviceName>Zblog</serviceName>\n");
    xml.append("<homepageLinkText>" + optionsService.getOptionValue(OptionConstants.TITLE) + "</homepageLinkText>\n");
    xml.append("<adminLinkText>博客管理</adminLinkText>\n");
    xml.append("<adminUrl>" + WebConstants.getDomainLink("/backend/login") + "</adminUrl>\n");
    xml.append("<postEditingUrl>" + WebConstants.getDomainLink("/backend/posts/edit?pid={post-id}")
        + "</postEditingUrl>\n");
    xml.append("</weblog>\n");
    xml.append("</manifest>");
    ServletUtils.sendText(response, xml.toString());
  }

  @RequestMapping(method = RequestMethod.POST)
  public void index(HttpServletRequest request, HttpServletResponse response) throws Exception{
    response.setContentType("text/xml");
    server.execute(request.getInputStream(), response.getWriter());
    response.getWriter().flush();
  }

}

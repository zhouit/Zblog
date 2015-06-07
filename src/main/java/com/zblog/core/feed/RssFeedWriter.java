package com.zblog.core.feed;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.zblog.core.feed.Channel.Article;
import com.zblog.core.util.IndentXMLStreamWriter;
import com.zblog.core.util.StringUtils;

public class RssFeedWriter{

  private RssFeedWriter(){
  }

  public static void write(Channel channel, OutputStream out) throws XMLStreamException{
    XMLOutputFactory factory = XMLOutputFactory.newFactory();
    XMLStreamWriter writer = null;
    try{
      writer = new IndentXMLStreamWriter(factory.createXMLStreamWriter(out, "UTF-8"));
      SimpleDateFormat format = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

      writer.writeStartDocument("UTF-8", "1.0");
      writer.writeEndDocument();

      writer.writeStartElement("rss");
      writer.writeAttribute("version", "2.0");
      writer.writeNamespace("content", "http://purl.org/rss/1.0/modules/content/");

      writer.writeStartElement("channel");

      createNode(writer, "title", channel.getTitle());
      createNode(writer, "link", channel.getDomain());
      createNode(writer, "description", channel.getDescription());
      createNode(writer, "language", "zh-CN");
      createNode(writer, "pubdate", format.format(new Date()));

      if(!StringUtils.isBlank(channel.getLogoUrl())){
        writer.writeStartElement("image");
        createNode(writer, "link", channel.getDomain());
        createNode(writer, "url", channel.getLogoUrl());
        createNode(writer, "title", channel.getTitle());
        writer.writeEndElement();
      }

      for(Article article : channel.getItems()){
        writer.writeStartElement("item");
        createNode(writer, "title", article.getTitle());
        createNode(writer, "link", channel.getDomain() + article.getLink());
        createNode(writer, "category", article.getCategory());
        createNode(writer, "author", article.getAuthor());
        createNode(writer, "description", article.getDescription());
        createNode(writer, "content", "http://purl.org/rss/1.0/modules/content/", "encoded", article.getContent());
        createNode(writer, "pubDate", format.format(article.getPubDate()));
        createNode(writer, "guid", channel.getDomain() + article.getGuid());
        createNode(writer, "comments", channel.getDomain() + article.getGuid() + "#comments");
        writer.writeEndElement();
      }

      writer.writeEndElement();

      writer.writeEndElement();

      writer.flush();
    }finally{
      if(writer != null)
        writer.close();
    }
  }

  private static void createNode(XMLStreamWriter writer, String name, String value) throws XMLStreamException{
    writer.writeStartElement(name);
    writer.writeCharacters(value);
    writer.writeEndElement();
  }

  private static void createNode(XMLStreamWriter writer, String prefix, String namespace, String name, String value)
      throws XMLStreamException{
    writer.writeStartElement(prefix, name, namespace);
    writer.writeCData(value);
    writer.writeEndElement();
  }

}

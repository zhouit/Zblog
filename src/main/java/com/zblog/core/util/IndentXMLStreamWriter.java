package com.zblog.core.util;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * 增强的XMLStreamWriter,格式化写入xml文件
 * @author zhou
 *
 */
public class IndentXMLStreamWriter implements XMLStreamWriter{
  protected XMLStreamWriter out;

  public IndentXMLStreamWriter(XMLStreamWriter out){
    this.out = out;
  }

  /** 当前元素深度, 根元素深度为1 */
  private int depth = 0;
  private int[] stack = new int[]{ 0, 0, 0, 0 };

  private static final int WROTE_MARKUP = 1;
  private static final int WROTE_DATA = 2;

  private String indent = "  ";
  private String newLine = "\n";
  private char[] linePrefix = null;

  public void setIndent(String indent){
    if(!indent.equals(this.indent)){
      this.indent = indent;
      linePrefix = null;
    }
  }

  public String getIndent(){
    return indent;
  }

  public void writeStartDocument() throws XMLStreamException{
    beforeMarkup();
    out.writeStartDocument();
    afterMarkup();
  }

  public void writeStartDocument(String version) throws XMLStreamException{
    beforeMarkup();
    out.writeStartDocument(version);
    afterMarkup();
  }

  public void writeStartDocument(String encoding, String version) throws XMLStreamException{
    beforeMarkup();
    out.writeStartDocument(encoding, version);
    afterMarkup();
  }

  public void writeDTD(String dtd) throws XMLStreamException{
    beforeMarkup();
    out.writeDTD(dtd);
    afterMarkup();
  }

  public void writeProcessingInstruction(String target) throws XMLStreamException{
    beforeMarkup();
    out.writeProcessingInstruction(target);
    afterMarkup();
  }

  public void writeProcessingInstruction(String target, String data) throws XMLStreamException{
    beforeMarkup();
    out.writeProcessingInstruction(target, data);
    afterMarkup();
  }

  public void writeComment(String data) throws XMLStreamException{
    beforeMarkup();
    out.writeComment(data);
    afterMarkup();
  }

  public void writeEmptyElement(String localName) throws XMLStreamException{
    beforeMarkup();
    out.writeEmptyElement(localName);
    afterMarkup();
  }

  public void writeEmptyElement(String namespaceURI, String localName)
      throws XMLStreamException{
    beforeMarkup();
    out.writeEmptyElement(namespaceURI, localName);
    afterMarkup();
  }

  public void writeEmptyElement(String prefix, String localName, String namespaceURI)
      throws XMLStreamException{
    beforeMarkup();
    out.writeEmptyElement(prefix, localName, namespaceURI);
    afterMarkup();
  }

  public void writeStartElement(String localName) throws XMLStreamException{
    beforeStartElement();
    out.writeStartElement(localName);
    afterStartElement();
  }

  public void writeStartElement(String namespaceURI, String localName)
      throws XMLStreamException{
    beforeStartElement();
    out.writeStartElement(namespaceURI, localName);
    afterStartElement();
  }

  public void writeStartElement(String prefix, String localName, String namespaceURI)
      throws XMLStreamException{
    beforeStartElement();
    out.writeStartElement(prefix, localName, namespaceURI);
    afterStartElement();
  }

  public void writeCharacters(String text) throws XMLStreamException{
    out.writeCharacters(text);
    afterData();
  }

  public void writeCharacters(char[] text, int start, int len) throws XMLStreamException{
    out.writeCharacters(text, start, len);
    afterData();
  }

  public void writeCData(String data) throws XMLStreamException{
    out.writeCData(data);
    afterData();
  }

  public void writeEntityRef(String name) throws XMLStreamException{
    out.writeEntityRef(name);
    afterData();
  }

  public void writeEndElement() throws XMLStreamException{
    beforeEndElement();
    out.writeEndElement();
    afterEndElement();
  }

  public void writeEndDocument() throws XMLStreamException{
    try{
      while(depth > 0){
        writeEndElement();
      }
    }catch(Exception ignored){
    }
    out.writeEndDocument();
    afterEndDocument();
  }

  protected void beforeMarkup(){
    int soFar = stack[depth];
    if((soFar & WROTE_DATA) == 0 && (depth > 0 || soFar != 0)){
      try{
        writeNewLine(depth);
        if(depth > 0 && getIndent().length() > 0){
          afterMarkup();
        }
      }catch(Exception e){
      }
    }
  }

  protected void afterMarkup(){
    stack[depth] |= WROTE_MARKUP;
  }

  protected void afterData(){
    stack[depth] |= WROTE_DATA;
  }

  protected void beforeStartElement(){
    beforeMarkup();
    if(stack.length <= depth + 1){
      int[] newStack = new int[stack.length * 2];
      System.arraycopy(stack, 0, newStack, 0, stack.length);
      stack = newStack;
    }
    stack[depth + 1] = 0;
  }

  protected void afterStartElement(){
    afterMarkup();
    ++depth;
  }

  protected void beforeEndElement(){
    if(depth > 0 && stack[depth] == WROTE_MARKUP){
      try{
        writeNewLine(depth - 1);
      }catch(Exception ignored){
      }
    }
  }

  protected void afterEndElement(){
    if(depth > 0){
      --depth;
    }
  }

  protected void afterEndDocument(){
    if(stack[depth = 0] == WROTE_MARKUP){
      try{
        writeNewLine(0);
      }catch(Exception ignored){
      }
    }
    stack[depth] = 0;
  }

  protected void writeNewLine(int indentation) throws XMLStreamException{
    final int newLineLength = newLine.length();
    final int prefixLength = newLineLength + (getIndent().length() * indentation);
    if(prefixLength > 0){
      if(linePrefix == null){
        linePrefix = (newLine + getIndent()).toCharArray();
      }
      while(prefixLength > linePrefix.length){
        char[] newPrefix = new char[newLineLength + ((linePrefix.length - newLineLength) * 2)];
        System.arraycopy(linePrefix, 0, newPrefix, 0, linePrefix.length);
        System.arraycopy(linePrefix, newLineLength, newPrefix, linePrefix.length,
            linePrefix.length - newLineLength);
        linePrefix = newPrefix;
      }
      out.writeCharacters(linePrefix, 0, prefixLength);
    }
  }

  @Override
  public void close() throws XMLStreamException{
    out.close();
  }

  @Override
  public void flush() throws XMLStreamException{
    out.flush();
  }

  @Override
  public void writeAttribute(String localName, String value) throws XMLStreamException{
    out.writeAttribute(localName, value);
  }

  @Override
  public void writeAttribute(String prefix, String namespaceURI, String localName, String value)
      throws XMLStreamException{
    out.writeAttribute(prefix, namespaceURI, localName, value);
  }

  @Override
  public void writeAttribute(String namespaceURI, String localName, String value)
      throws XMLStreamException{
    out.writeAttribute(namespaceURI, localName, value);
  }

  @Override
  public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException{
    out.writeNamespace(prefix, namespaceURI);
  }

  @Override
  public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException{
    out.writeDefaultNamespace(namespaceURI);

  }

  @Override
  public String getPrefix(String uri) throws XMLStreamException{
    return out.getPrefix(uri);
  }

  @Override
  public void setPrefix(String prefix, String uri) throws XMLStreamException{
    out.setPrefix(prefix, uri);
  }

  @Override
  public void setDefaultNamespace(String uri) throws XMLStreamException{
    out.setDefaultNamespace(uri);
  }

  @Override
  public void setNamespaceContext(NamespaceContext context) throws XMLStreamException{
    out.setNamespaceContext(context);
  }

  @Override
  public NamespaceContext getNamespaceContext(){
    return out.getNamespaceContext();
  }

  @Override
  public Object getProperty(String name) throws IllegalArgumentException{
    return out.getProperty(name);
  }

}

package com.zblog.core.security;

/**
 * Base64编解码器 from http://www.source-code.biz/base64coder/java/
 * 
 * @author zhou
 * 
 */
public class Base64Codec{
  private static final String lineSeparator = System.getProperty("line.separator");
  private static final char[] encodeMap = new char[64];

  static{
    int i = 0;
    for(char c = 'A'; c <= 'Z'; c++)
      encodeMap[i++] = c;
    for(char c = 'a'; c <= 'z'; c++)
      encodeMap[i++] = c;
    for(char c = '0'; c <= '9'; c++)
      encodeMap[i++] = c;
    encodeMap[i++] = '+';
    encodeMap[i++] = '/';
  }

  private static final byte[] decodeMap = new byte[128];

  static{
    for(int i = 0; i < decodeMap.length; i++)
      decodeMap[i] = -1;
    for(int i = 0; i < 64; i++)
      decodeMap[encodeMap[i]] = (byte) i;
  }

  public static String encode(String s){
    return new String(encode(s.getBytes()));
  }

  /**
   * 兼容sun.misc.BASE64Encoder.encodeBuffer(byte[]) 每76个字符加一个换行
   * 
   * @param in
   * @return
   */
  public static String encodeLines(byte[] in){
    return encodeLines(in, 0, in.length, 76, lineSeparator);
  }

  public static String encodeLines(byte[] in, int iOff, int iLen, int lineLen, String lineSeparator){
    int blockLen = (lineLen * 3) / 4;
    if(blockLen <= 0)
      throw new IllegalArgumentException();

    int lines = (iLen + blockLen - 1) / blockLen;
    int bufLen = ((iLen + 2) / 3) * 4 + lines * lineSeparator.length();
    StringBuilder buf = new StringBuilder(bufLen);
    int ip = 0;
    while(ip < iLen){
      int l = Math.min(iLen - ip, blockLen);
      buf.append(encode(in, iOff + ip, l));
      buf.append(lineSeparator);
      ip += l;
    }
    return buf.toString();
  }

  public static char[] encode(byte[] in){
    return encode(in, 0, in.length);
  }

  /**
   * No blanks or line breaks are inserted in the output.
   * 
   * @param in
   * @param offset
   * @param len
   * @return
   */
  public static char[] encode(byte[] in, int offset, int len){
    int oDataLen = (len * 4 + 2) / 3; // output length without padding
    int oLen = ((len + 2) / 3) * 4; // output length including padding
    char[] out = new char[oLen];
    int ip = offset;
    int iEnd = offset + len;
    int op = 0;
    while(ip < iEnd){
      int i0 = in[ip++] & 0xff;
      int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
      int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
      int o0 = i0 >>> 2;
      int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
      int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
      int o3 = i2 & 0x3F;
      out[op++] = encodeMap[o0];
      out[op++] = encodeMap[o1];
      out[op] = op < oDataLen ? encodeMap[o2] : '=';
      op++;
      out[op] = op < oDataLen ? encodeMap[o3] : '=';
      op++;
    }
    return out;
  }

  public static String decodeString(String s){
    return new String(decode(s));
  }

  /**
   * 兼容sun.misc.BASE64Decoder.decodeBuffer(String)<br>
   * 忽略 line separators, tabs and blanks, CR, LF, Tab and Space characters
   */
  public static byte[] decodeLines(String s){
    char[] buf = new char[s.length()];
    int p = 0;
    for(int ip = 0; ip < s.length(); ip++){
      char c = s.charAt(ip);
      if(c != ' ' && c != '\r' && c != '\n' && c != '\t')
        buf[p++] = c;
    }
    return decode(buf, 0, p);
  }

  public static byte[] decode(String s){
    return decode(s.toCharArray());
  }

  public static byte[] decode(char[] in){
    return decode(in, 0, in.length);
  }

  public static byte[] decode(char[] in, int offset, int len){
    if(len % 4 != 0)
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    while(len > 0 && in[offset + len - 1] == '=')
      len--;
    int oLen = (len * 3) / 4;
    byte[] out = new byte[oLen];
    int ip = offset;
    int iEnd = offset + len;
    int op = 0;
    while(ip < iEnd){
      int i0 = in[ip++];
      int i1 = in[ip++];
      int i2 = ip < iEnd ? in[ip++] : 'A';
      int i3 = ip < iEnd ? in[ip++] : 'A';
      if(i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      int b0 = decodeMap[i0];
      int b1 = decodeMap[i1];
      int b2 = decodeMap[i2];
      int b3 = decodeMap[i3];
      if(b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      int o0 = (b0 << 2) | (b1 >>> 4);
      int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
      int o2 = ((b2 & 3) << 6) | b3;
      out[op++] = (byte) o0;
      if(op < oLen)
        out[op++] = (byte) o1;
      if(op < oLen)
        out[op++] = (byte) o2;
    }
    return out;
  }

  private Base64Codec(){
  }

}
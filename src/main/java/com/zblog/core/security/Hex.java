package com.zblog.core.security;

/**
 * 字节数组工具类
 * 
 * @author zhou
 *
 */
public class Hex{
  private static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  private Hex(){
  }

  /**
   * 字节数组转十六进制
   * 
   * @param bytes
   * @return 大写形式十六进制
   */
  public static String bytes2Hex(byte[] bytes){
    char[] result = new char[bytes.length << 1];
    for(int i = 0; i < bytes.length; i++){
      result[2 * i] = digits[bytes[i] >>> 4 & 0x0F];
      result[2 * i + 1] = digits[bytes[i] & 0x0F];
    }

    return new String(result);
  }

  /**
   * 十六进制转字节数组
   * 
   * @param hex
   * @return
   */
  public static byte[] hex2Bytes(String hex){
    byte[] result = new byte[hex.length() >> 1];
    for(int i = 0; i < hex.length(); i += 2){
      int digit = Character.digit(hex.charAt(i), 16);
      result[i >> 1] = (byte) (digit << 4 | Character.digit(hex.charAt(i + 1), 16));
    }

    return result;
  }

  public static int bytes2Int(byte[] buf){
    return ((buf[0] & 0xff) << 24) | ((buf[1] & 0xff) << 16) | ((buf[2] & 0xff) << 8) | (buf[3] & 0xff);
  }

  public static byte[] int2Bytes(int value){
    byte[] result = new byte[4];
    result[0] = (byte) (value >> 24);
    result[1] = (byte) (value >> 16 & 0xFF);
    result[2] = (byte) (value >> 8 & 0xFF);
    result[3] = (byte) (value & 0xFF);

    return result;
  }

}

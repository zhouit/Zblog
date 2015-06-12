package com.zblog.core.security;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 消息摘要计算,支持
 * <ul>
 * <li>CRC32</li>
 * <li>MD2,MD5</li>
 * <li>SHA-1,SHA-2系列算法(SHA-256,SHA-384,SHA-512)</li>
 * <li>Hmac系列算法(HmacMD5,HmacSHA1,HmacSHA256,HmacSHA384,HmacSHA512)</li>
 * </ul>
 * 
 * @author zhou
 *
 */
public class HashCalculator{
  public static final String MD2 = "MD2";
  public static final String MD5 = "MD5";

  public static final String SHA1 = "SHA-1";
  public static final String SHA256 = "SHA-256";
  public static final String SHA384 = "SHA-384";
  public static final String SHA512 = "SHA-512";

  public static final String HmacMD5 = "HmacMD5";
  public static final String HmacSHA1 = "HmacSHA1";
  public static final String HmacSHA256 = "HmacSHA256";
  public static final String HmacSHA384 = "HmacSHA384";
  public static final String HmacSHA512 = "HmacSHA512";

  private HashCalculator(){
  }

  public static long crc32(byte[] input){
    CRC32 crc32 = new CRC32();
    crc32.update(input);
    return crc32.getValue();
  }

  public static byte[] md5(byte[] input){
    return hash(input, MD5);
  }

  /**
   * 执行md系列算法
   * 
   * @param input
   * @param mdType
   * @return
   */
  public static byte[] md(byte[] input, String mdType){
    return hash(input, mdType);
  }

  /**
   * 执行sha系列算法
   * 
   * @param input
   * @param shaType
   * @return
   */
  public static byte[] sha(byte[] input, String shaType){
    return hash(input, shaType);
  }

  private static byte[] hash(byte[] input, String algorithm){
    try{
      MessageDigest digest = MessageDigest.getInstance(algorithm);
      return digest.digest(input);
    }catch(NoSuchAlgorithmException e){
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * 执行hmac系列算法
   * 
   * @param input
   * @param hmacType
   * @param key
   * @return
   */
  public static byte[] hmac(byte[] input, String hmacType, byte[] key){
    try{
      SecretKey secretKey = new SecretKeySpec(key, hmacType);
      Mac mac = Mac.getInstance(secretKey.getAlgorithm());
      mac.init(secretKey);
      return mac.doFinal(input);
    }catch(NoSuchAlgorithmException | InvalidKeyException e){
      throw new IllegalArgumentException(e);
    }
  }

}

package com.zblog.core.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密
 * <p>
 * 支持DES(java支持56位密钥),AES(java默认128位、支持192、256位密钥),DESede(3DES,java支持112,168位密钥)
 * , Blowfish(密钥长度32-448位,必须为8的整数), RC2(40-1024位密钥长度), RC4(40-1024位密钥长度).
 * </p>
 * <p>
 * 注：支持设置ebc/cbc模式,除RC4外均支持padding参数, 具体请参考:<br>
 * http://docs.oracle.com/javase/7/docs/technotes/guides/security
 * /SunProviders.html
 * 
 * </p>
 * 
 * @author zhou
 *
 */
public class SymmetryCrypt{
  public static final String DES = "DES";
  public static final String AES = "AES";
  public static final String DESede = "DESede";
  public static final String Blowfish = "Blowfish";
  public static final String RC2 = "RC2";
  public static final String RC4 = "RC4";

  private String algorithm;
  private boolean padding = false;
  private byte[] input;
  private byte[] key;
  private byte[] iv;

  public SymmetryCrypt(){
  }

  public SymmetryCrypt(String algorithm){
    this.algorithm = algorithm;
  }

  public SymmetryCrypt algorithm(String algorithm){
    this.algorithm = algorithm;
    return this;
  }

  /**
   * 启用ebc模式
   * 
   * @param ivparam
   *          ebc模式需要的iv向量参数
   * @return
   */
  public SymmetryCrypt ebc(byte[] ivparam){
    this.iv = ivparam;
    return this;
  }

  /**
   * 默认nopadding
   * 
   * @param padding
   * @return
   */
  public SymmetryCrypt padding(boolean padding){
    this.padding = padding;
    return this;
  }

  public SymmetryCrypt data(byte[] input, byte[] key){
    this.input = input;
    this.key = key;
    return this;
  }

  public byte[] encrypt(){
    return crypt(Cipher.ENCRYPT_MODE);
  }

  public byte[] decrypt(){
    return crypt(Cipher.DECRYPT_MODE);
  }

  private byte[] crypt(int opmode){
    try{
      SecretKeySpec sks = new SecretKeySpec(key, algorithm);
      String transformation = algorithm + "/" + (iv == null ? "ECB" : "CBC") + "/"
          + (padding ? "PKCS5Padding" : "NoPadding");
      Cipher cipher = Cipher.getInstance(transformation);
      if(iv == null){
        cipher.init(opmode, sks);
      }else{
        cipher.init(opmode, sks, new IvParameterSpec(iv));
      }

      return cipher.doFinal(input);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public static byte[] genKey(String algorithm){
    return genKey(algorithm, -1);
  }

  public static byte[] genKey(String algorithm, int keyLength){
    return genKey(algorithm, keyLength, null);
  }

  public static byte[] genKey(String algorithm, int keyLength, byte[] seed){
    try{
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      if(keyLength > 1){
        kg.init(keyLength, seed == null ? new SecureRandom() : new SecureRandom(seed));
      }

      SecretKey secretKey = kg.generateKey();
      return secretKey.getEncoded();
    }catch(NoSuchAlgorithmException e){
      throw new IllegalArgumentException(e);
    }
  }

}

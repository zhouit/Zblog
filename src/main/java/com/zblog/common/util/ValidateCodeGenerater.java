package com.zblog.common.util;

import java.util.Random;

public class ValidateCodeGenerater{

  public synchronized static String generate(int userId, String target, String type){
    StringBuilder sb = new StringBuilder();
    sb.append(userId);
    sb.append(target);
    sb.append(type);
    sb.append(System.nanoTime());
    sb.append(getRandomSalt(10));

    return Md5.md5(sb.toString()).toLowerCase();
  }

  public synchronized static String generate(){
    return getRandomSalt(6);
  }

  public synchronized static String generateSid(){
    StringBuilder sb = new StringBuilder();
    sb.append(System.nanoTime());
    sb.append(getRandomSalt(10));

    return Md5.md5(sb.toString()).toLowerCase();
  }

  static String getRandomSalt(final int num){
    final StringBuilder saltString = new StringBuilder();
    for(int i = 1; i <= num; i++){
      saltString.append(new Random().nextInt(10));
    }
    return saltString.toString();
  }

}
package com.zblog.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NumberUtils{
  private static final Random random = new Random();

  final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
      'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
      'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

  final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();

  static{
    for(int i = 0; i < digits.length; i++){
      digitMap.put(digits[i], (int) i);
    }
  }

  /**
   * 支持的最大进制数
   */
  public static final int MAX_RADIX = digits.length;

  /**
   * 支持的最小进制数
   */
  public static final int MIN_RADIX = 2;

  /**
   * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
   * 
   * @param i
   * @param radix
   * @return
   */
  public static String toString(long i, int radix){
    if(radix < MIN_RADIX || radix > MAX_RADIX)
      radix = 10;
    if(radix == 10)
      return Long.toString(i);

    final int size = 65;
    int charPos = 64;

    char[] buf = new char[size];
    boolean negative = (i < 0);

    if(!negative){
      i = -i;
    }

    while(i <= -radix){
      buf[charPos--] = digits[(int) (-(i % radix))];
      i = i / radix;
    }
    buf[charPos] = digits[(int) (-i)];

    if(negative){
      buf[--charPos] = '-';
    }

    return new String(buf, charPos, (size - charPos));
  }

  private static NumberFormatException forInputString(String s){
    return new NumberFormatException("For input string: \"" + s + "\"");
  }

  /**
   * 将字符串转换为长整型数字
   * 
   * @param s
   *          数字字符串
   * @param radix
   *          进制数
   * @return
   */
  public static long toNumber(String s, int radix){
    if(s == null){
      throw new NumberFormatException("null");
    }

    if(radix < MIN_RADIX){
      throw new NumberFormatException("radix " + radix + " less than Numbers.MIN_RADIX");
    }
    if(radix > MAX_RADIX){
      throw new NumberFormatException("radix " + radix + " greater than Numbers.MAX_RADIX");
    }

    long result = 0;
    boolean negative = false;
    int i = 0, len = s.length();
    long limit = -Long.MAX_VALUE;
    long multmin;
    Integer digit;

    if(len > 0){
      char firstChar = s.charAt(0);
      if(firstChar < '0'){
        if(firstChar == '-'){
          negative = true;
          limit = Long.MIN_VALUE;
        }else if(firstChar != '+')
          throw forInputString(s);

        if(len == 1){
          throw forInputString(s);
        }
        i++;
      }
      multmin = limit / radix;
      while(i < len){
        digit = digitMap.get(s.charAt(i++));
        if(digit == null){
          throw forInputString(s);
        }
        if(digit < 0){
          throw forInputString(s);
        }
        if(result < multmin){
          throw forInputString(s);
        }
        result *= radix;
        if(result < limit + digit){
          throw forInputString(s);
        }
        result -= digit;
      }
    }else{
      throw forInputString(s);
    }
    return negative ? result : -result;
  }

  /**
   * 字符串转int,无法转换时返回Integer.MIN_VALUE
   * 
   * @param str
   * @return
   */
  public static int toInteger(String str){
    return toInteger(str, Integer.MIN_VALUE);
  }

  public static int toInteger(String str, int defaults){
    int result = defaults;
    try{
      result = Integer.parseInt(str);
    }catch(Exception e){
    }

    return result;
  }

  /**
   * 字符串转long,无法转换时返回Long.MIN_VALUE
   * 
   * @param str
   * @return
   */
  public static long toLong(String str){
    long result = Long.MIN_VALUE;
    try{
      result = Long.parseLong(str);
    }catch(Exception e){
    }

    return result;
  }

  /**
   * 汉字转数字
   * 
   * @param c
   *          如'一二三四五六七八九'
   * @return
   */
  public static int toNumber(char c){
    char[] numbers = { '一', '二', '三', '四', '五', '六', '七', '八', '九' };
    for(int i = 0; i < numbers.length; i++){
      if(numbers[i] == c)
        return i;
    }

    return -1;
  }

  public static int randomInt(int min, int max){
    return min + random.nextInt(max - min);
  }

  public static int randomInt(int limit){
    return randomInt(0, limit);
  }
}
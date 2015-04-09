package com.zblog.core.util;

/**
 * 通用正则表达式
 * 
 * @author zhou
 * 
 */
public class CommRegular{
  /**
   * 首位不为数字,字符长度为2-15的用户名
   */
  public static final String USERNAME = "^[a-zA-Z\u4e00-\u9fa5][_a-zA-Z0-9\u4e00-\u9fa5]{1,14}$";

  /**
   * 必须要包含数字和大小写字母6-16位密码
   * <ul>
   * <li>^开始标记</li>
   * <li>(?=.*\\d) 保证字符串包含一个数字</li>
   * <li>(?=.*[a-z]) 保证字符串一个小写字母</li>
   * <li>(?=.*[A-Z]) 保证字符串包含一个大写字母</li>
   * <li>[0-9a-zA-Z]{6,16} 保证字符串长度为6-16</li>
   * <li>$结束标记</li>
   * </ul>
   */
  public static final String COMPLEX_PASSWD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,16}$";

  public static final String PASSWD = "^\\S{6,16}$";

  /**
   * md5正则
   */
  public static final String MD5 = "[0-9a-z-A-Z]{32}";

  /**
   * 邮箱正则
   */
  public static final String EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

  /**
   * http域名正则,暂不支持ip
   */
  public static final String DOMAIN = "^https?://[a-zA-Z0-9\\._-]+(:\\d{2,4})?(/\\w+/?)?$";

}

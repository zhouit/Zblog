package com.zblog.common.util.constants;

public final class Constants{
  private Constants(){
  }

  public static final String ENCODING_UTF_8 = "UTF-8";

  public static final String COOKIE_CONTEXT_ID = "c_id";
  public static final String COOKIE_USER_NAME = "un";

  /**
   * csrf表单提交token名称
   */
  public static final String CSRF_TOKEN = "CSRFToken";
  /**
   * csrf的cookie名称
   */
  public static final String COOKIE_CSRF_TOKEN = "x-csrf-token";

}

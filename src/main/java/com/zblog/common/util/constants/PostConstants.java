package com.zblog.common.util.constants;

public class PostConstants{
  private PostConstants(){
  }

  /* 文章类型 */
  public static final String TYPE_POST = "post";
  public static final String TYPE_PAGE = "page";
  /* 文章评论状态 */
  public static final String COMMENT_POST = "open";
  public static final String COMMENT_CLOSE = "close";

  public static final String POST_PUBLISH = "publish";
  public static final String POST_TRASH = "trash";

  /**
   * 默认文章的parent
   */
  public static final String DEFAULT_PARENT = "Root";
  public static final String OPTION_POSTID_NAME = "POSTID_NAME";

  /* 文章摘要长度 (此为post表中excerpt的varchar(350)) */
  public static final int EXCERPT_LENGTH = 350;

  /**
   * 初始化postid
   */
  public static final int INIT_POST_ID = 5;

  /**
   * 当添加文章时,防止用户上传没有用到的文件
   */
  public static final String UPLOAD_TOKEN = "uploadToken";

}

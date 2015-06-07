package com.zblog.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcStruct;

import com.zblog.core.dal.entity.Category;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.entity.Upload;
import com.zblog.core.dal.entity.User;
import com.zblog.core.plugin.MapContainer;
import com.zblog.core.util.JsoupUtils;
import com.zblog.core.util.PostTagHelper;
import com.zblog.core.util.StringUtils;
import com.zblog.core.util.constants.CategoryConstants;
import com.zblog.core.util.constants.OptionConstants;
import com.zblog.core.util.constants.PostConstants;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.service.CategoryService;
import com.zblog.service.OptionsService;
import com.zblog.service.PostService;
import com.zblog.service.TagService;
import com.zblog.service.UserService;

/**
 * 
 * @author zhou
 * 
 */
@Component
public class MetaWeblogManager{
  @Autowired
  private UserService userService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private PostService postService;
  @Autowired
  private PostManager postManager;
  @Autowired
  private UploadManager uploadManager;
  @Autowired
  private OptionManager optionManager;
  @Autowired
  private OptionsService optionsService;
  @Autowired
  private TagService tagService;

  public Object getPost(String postid, String username, String pwd){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    Post post = postService.loadById(postid);
    MapContainer mc = new MapContainer();
    mc.put("dateCreated", post.getCreateTime()).put("userid", user.getId());
    mc.put("postid", post.getId()).put("description", post.getContent());
    mc.put("title", post.getTitle()).put("link", WebConstants.getDomainLink("/posts/" + postid))
        .put("permaLink", WebConstants.getDomainLink("/posts/" + postid));
    Category category = categoryService.loadById(post.getCategoryid());
    mc.put("categories", Arrays.asList(category.getName()));
    mc.put("mt_keywords", StringUtils.join(tagService.listTagsByPost(postid), ","));
    mc.put("post_status", "public");

    return mc;
  }

  public Object newMediaObject(String blogid, String username, String pwd, XmlRpcStruct file) throws XmlRpcException{
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    byte[] bits = file.getBinary("bits");
    String name = file.getString("name");// Windows-Live-Writer/123123_A453/file_1_8.jpg
    int slash = name.lastIndexOf("/");
    name = name.substring(slash + 1);
    String type = file.getString("type");// image/jpeg

    if(StringUtils.isBlank(type) || !type.startsWith("image/")){
      return new MapContainer().put("faultCode", HttpServletResponse.SC_FORBIDDEN).put("faultString",
          "img_file_not_accept");
    }

    Upload upload = uploadManager.insertUpload(bits, name, user.getId());
    return new MapContainer("url", WebConstants.getDomainLink(upload.getPath()));
  }

  public Object newPost(String blogid, String username, String pwd, XmlRpcStruct param, boolean publish){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    Post post = new Post();
    post.setId(optionManager.getNextPostid());
    /* param.getDate("dateCreated") */
    post.setCreateTime(new Date());
    post.setLastUpdate(post.getCreateTime());
    post.setType(PostConstants.TYPE_POST);
    post.setTitle(HtmlUtils.htmlEscape(param.getString("title")));
    post.setCreator(user.getId());
    XmlRpcArray categories = param.getArray("categories");
    if(categories != null && !categories.isEmpty()){
      post.setCategoryid(categoryService.loadByName(categories.getString(0)).getId());
    }else{
      post.setCategoryid(optionsService.getOptionValue(OptionConstants.DEFAULT_CATEGORY_ID));
    }

    String content = param.getString("description");
    post.setContent(JsoupUtils.filter(content));
    String cleanTxt = JsoupUtils.plainText(content);
    post.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0,
        PostConstants.EXCERPT_LENGTH) : cleanTxt);
    post.setParent(PostConstants.DEFAULT_PARENT);

    String tags = param.getString("mt_keywords");
    postManager.insertPost(post, PostTagHelper.from(post, tags, post.getCreator()));

    return post.getId();
  }

  public Object deletePost(String appKey, String postid, String username, String pwd, boolean publish){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    postManager.removePost(postid, PostConstants.TYPE_POST);
    return postid;
  }

  public Object editPost(String postid, String username, String pwd, XmlRpcStruct param, boolean publish){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    Post post = new Post();
    post.setId(postid);
    post.setTitle(HtmlUtils.htmlEscape(param.getString("title")));
    post.setLastUpdate(new Date());
    post.setType(PostConstants.TYPE_POST);
    // param.getString("tags_input");
    String content = param.getString("description");
    post.setContent(JsoupUtils.filter(content));
    String cleanTxt = JsoupUtils.plainText(content);
    post.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0,
        PostConstants.EXCERPT_LENGTH) : cleanTxt);
    XmlRpcArray categories = param.getArray("categories");
    if(categories != null && !categories.isEmpty())
      post.setCategoryid(categoryService.loadByName(categories.getString(0)).getId());

    String tags = param.getString("mt_keywords");
    postManager.updatePost(post, PostTagHelper.from(post, tags, user.getId()));
    return postid;
  }

  public Object getUsersBlogs(String key, String username, String pwd){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    MapContainer mc = new MapContainer("isAdmin", false);
    mc.put("blogid", user.getId()).put("blogName", optionsService.getOptionValue(OptionConstants.TITLE));
    mc.put("xmlrpc", WebConstants.getDomainLink("/xmlrpc")).put("url", WebConstants.getDomainLink("/"));

    return new MapContainer[]{ mc };
  }

  public Object getCategories(String blogid, String username, String pwd){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    List<MapContainer> categories = categoryService.list();
    List<MapContainer> result = new ArrayList<>(categories.size() - 1);
    for(MapContainer category : categories){
      if(CategoryConstants.ROOT.equals(category.getAsString("text")))
        continue;

      MapContainer mc = new MapContainer("categoryid", category.getAsString("id"))
          .put("title", category.getAsString("text")).put("description", category.getAsString("text"))
          .put("htmlUrl", WebConstants.getDomainLink("/categorys/" + category.getAsString("text"))).put("rssUrl", "");
      result.add(mc);
    }
    return result;
  }

  public Object getTags(String blogid, String username, String pwd){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    List<MapContainer> tags = new ArrayList<>();
    for(MapContainer mc : tagService.list()){
      MapContainer tag = new MapContainer("tag_id", mc.get("name"));
      tag.put("name", mc.get("name")).put("count", tag.get("count"));
      tags.add(tag);
    }

    return tags;
  }

  public Object getRecentPosts(String blogid, String username, String pwd, int numberOfPosts){
    User user = userService.login(username, pwd);
    if(user == null)
      loginError();

    List<MapContainer> list = postManager.listRecent(numberOfPosts);
    MapContainer[] result = new MapContainer[list.size()];
    for(int i = 0; i < list.size(); i++){
      MapContainer temp = list.get(i);
      result[i] = new MapContainer("dateCreated", temp.getAsDate("createTime"))
          .put("userid", temp.getAsString("creator")).put("postid", temp.getAsString("id"))
          .put("description", temp.getAsString("content")).put("title", temp.getAsString("title"))
          .put("link", WebConstants.getDomainLink("/posts/" + temp.get("id")))
          .put("permaLink", WebConstants.getDomainLink("/posts/" + temp.get("id")))
          .put("categories", Arrays.asList(temp.getAsString("categoryName"))).put("post_status", "publish");
    }
    return result;
  }

  private static void loginError() throws XmlRpcException{
    throw new XmlRpcException("FORBIDDEN");
  }

}

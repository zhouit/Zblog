package com.zblog.biz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.zblog.core.WebConstants;
import com.zblog.core.dal.entity.Post;
import com.zblog.core.dal.entity.Upload;
import com.zblog.core.dal.entity.User;
import com.zblog.core.plugin.PageModel;
import com.zblog.core.util.DateUtils;
import com.zblog.core.util.FileUtils;
import com.zblog.core.util.IdGenerator;
import com.zblog.core.util.StringUtils;
import com.zblog.service.PostService;
import com.zblog.service.UploadService;
import com.zblog.service.UserService;
import com.zblog.service.vo.UploadVO;

@Component
public class UploadManager{
  @Autowired
  private UploadService uploadService;
  @Autowired
  private PostService postService;
  @Autowired
  private UserService userService;

  public PageModel<UploadVO> list(int pageIndex, int pageSize){
    PageModel<UploadVO> result = uploadService.list(pageIndex, pageSize);
    for(UploadVO upload : result.getContent()){
      if(!StringUtils.isBlank(upload.getPostid())){
        Post post = postService.loadById(upload.getPostid());
        upload.setPost(post);
      }
      User user = userService.loadById(upload.getCreator());
      upload.setUser(user);
    }

    return result;
  }

  /**
   * 添加上传记录并存储文件
   * 
   * @param resource
   * @param create
   * @param fileName
   * @param userid
   * @return 当前上传对象
   */
  public Upload insertUpload(Resource resource, Date create, String fileName, String userid){
    Upload upload = null;
    OutputStream out = null;
    try{
      String yearMonth = DateUtils.formatDate("yyyy/MM", create);
      File parent = new File(WebConstants.APPLICATION_PATH + "/post/uploads", yearMonth);
      if(!parent.exists())
        parent.mkdirs();

      File savePath = FileUtils.determineFile(parent, fileName);
      IOUtils.copy(resource.getInputStream(), out = new FileOutputStream(savePath));

      upload = new Upload();
      upload.setId(IdGenerator.uuid19());
      upload.setCreateTime(create);
      upload.setCreator(userid);
      upload.setName(fileName);
      upload.setPath("/post/uploads/" + yearMonth + "/" + savePath.getName());

      uploadService.insert(upload);
    }catch(Exception e){
      e.printStackTrace();
      upload = null;
    }finally{
      IOUtils.closeQuietly(out);
    }

    return upload;
  }

  /**
   * 删除记录，同时删除文件
   * 
   * @param uploadid
   */
  public void removeUpload(String uploadid){
    Upload upload = uploadService.loadById(uploadid);
    uploadService.deleteById(uploadid);
    File file = new File(WebConstants.APPLICATION_PATH, upload.getPath());
    if(file.exists())
      file.delete();
    
    /* 注:当前目录为空同时删除父目录,如果父目录包含子文件/夹，会删除失败(File.delete中决定) */
    File parent = file.getParentFile();
    for(int i = 0; i < 2 && parent.list().length == 0; i++){
      parent.delete();
      parent = parent.getParentFile();
    }
  }

}

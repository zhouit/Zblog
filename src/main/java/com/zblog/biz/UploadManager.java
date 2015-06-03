package com.zblog.biz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zblog.core.dal.entity.Upload;
import com.zblog.core.util.DateUtils;
import com.zblog.core.util.FileUtils;
import com.zblog.core.util.IdGenarater;
import com.zblog.core.util.ServletUtils;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.core.util.web.ServletRequestReader;
import com.zblog.core.util.web.WebContext;
import com.zblog.core.util.web.WebContextFactory;
import com.zblog.service.UploadService;

@Component
public class UploadManager{
  @Autowired
  private UploadService uploadService;

  /**
   * 添加上传记录并存储文件
   * 
   * @param reader
   * @param fileVal
   * @return 当前上传对象
   */
  public Upload insertUpload(ServletRequestReader reader, String fileVal){
    Upload upload = null;
    MultipartFile file = reader.getFile(fileVal);
    try{
      Date current = new Date();
      String yearMonth = DateUtils.formatDate("yyyy/MM", current);
      File parent = new File(reader.getRealPath("/post/uploads"), yearMonth);
      if(!parent.exists())
        parent.mkdirs();

      File savePath = FileUtils.determineFile(parent, file.getOriginalFilename());
      file.transferTo(savePath);

      upload = new Upload();
      upload.setId(IdGenarater.uuid19());
      upload.setCreator(WebContextFactory.get().getUser().getId());
      upload.setCreateTime(current);
      upload.setName(file.getOriginalFilename());
      upload.setPath("/post/uploads/" + yearMonth + "/" + savePath.getName());

      uploadService.insert(upload);
    }catch(Exception e){
      e.printStackTrace();
      upload = null;
    }

    return upload;
  }

  public Upload insertUpload(byte[] file, String fileName, String userid){
    Upload upload = null;
    OutputStream out = null;
    try{
      Date current = new Date();
      String yearMonth = DateUtils.formatDate("yyyy/MM", current);
      WebContext context = WebContextFactory.get();
      File parent = new File(ServletUtils.getRealPath(context.getRequest(), "/post/uploads"), yearMonth);
      if(!parent.exists())
        parent.mkdirs();

      File savePath = FileUtils.determineFile(parent, fileName);
      IOUtils.write(file, out = new FileOutputStream(savePath));

      upload = new Upload();
      upload.setId(IdGenarater.uuid19());
      upload.setCreateTime(current);
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

  public Upload insertUpload(InputStream in, Date create, String fileName, String userid){
    Upload upload = null;
    OutputStream out = null;
    try{
      String yearMonth = DateUtils.formatDate("yyyy/MM", create);
      File parent = new File(WebConstants.APPLICATION_PATH + "post/uploads", yearMonth);
      if(!parent.exists())
        parent.mkdirs();

      File savePath = FileUtils.determineFile(parent, fileName);
      IOUtils.copy(in, out = new FileOutputStream(savePath));

      upload = new Upload();
      upload.setId(IdGenarater.uuid19());
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
    new File(WebConstants.APPLICATION_PATH, upload.getPath()).delete();
  }

}

package com.zblog.biz;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zblog.core.dal.entity.Upload;
import com.zblog.core.util.DateUtils;
import com.zblog.core.util.FileUtils;
import com.zblog.core.util.IdGenarater;
import com.zblog.core.util.constants.WebConstants;
import com.zblog.core.util.web.ServletRequestReader;
import com.zblog.core.util.web.WebContextHolder;
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
      String year = DateUtils.currentDate("yyyy");
      File parent = new File(reader.getRealPath("/post/uploads"), year);
      if(!parent.exists())
        parent.mkdirs();

      File savePath = determineFile(parent, file.getOriginalFilename());
      file.transferTo(savePath);

      upload = new Upload();
      upload.setId(IdGenarater.uuid19());
      upload.setCreateTime(new Date());
      upload.setCreator(WebContextHolder.get().getUser().getNickName());
      upload.setName(file.getOriginalFilename());
      upload.setPath("/post/uploads/" + year + "/" + savePath.getName());

      uploadService.insert(upload);
    }catch(Exception e){
      e.printStackTrace();
      upload = null;
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

  /**
   * 生成文件存储名
   * 
   * @param parent
   * @param fileName
   * @return
   */
  private File determineFile(File parent, String fileName){
    String name = FileUtils.getFileName(fileName);
    String ext = FileUtils.getFileExt(fileName);
    File temp = new File(parent, fileName);
    for(int i = 1; temp.exists(); i++){
      temp = new File(parent, name + i + "." + ext);
    }

    return temp;
  }

}

package com.zblog.biz;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zblog.common.dal.entity.Upload;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.FileUtils;
import com.zblog.common.util.IdGenarater;
import com.zblog.common.util.constants.PostConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.common.util.web.ServletRequestReader;
import com.zblog.service.UploadService;

@Component
public class UploadManager{
  @Autowired
  private UploadService uploadService;

  public MapContainer insertUpload(ServletRequestReader reader, String fileVal){
    MapContainer result = new MapContainer("state", "SUCCESS");
    MultipartFile file = reader.getFile(fileVal);
    try{
      String year = DateUtils.currentDate("yyyy");
      File parent = new File(reader.getRealPath("/post/uploads"), year);
      if(!parent.exists())
        parent.mkdirs();

      File savePath = determineFile(parent, file.getOriginalFilename());
      file.transferTo(savePath);
      result.put("original", file.getOriginalFilename());
      result.put("title", file.getOriginalFilename());
      result.put("url", WebConstants.getDomain() + "/post/uploads/" + year + "/" + savePath.getName());

      Upload upload = new Upload();
      upload.setId(IdGenarater.uuid19());
      upload.setCreateTime(new Date());
      upload.setName(file.getOriginalFilename());
      upload.setToken(reader.getAsString(PostConstants.UPLOAD_TOKEN));
      upload.setPath("/post/uploads/" + year + "/" + savePath.getName());

      uploadService.insert(upload);
    }catch(Exception e){
      e.printStackTrace();
      result.put("state", "文件上传失败");
    }

    return result;
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
   * 生成文件存储文件名
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

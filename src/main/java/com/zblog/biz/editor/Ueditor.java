package com.zblog.biz.editor;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.zblog.common.dal.entity.Upload;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.DateUtils;
import com.zblog.common.util.FileUtils;
import com.zblog.common.util.IdGenarater;
import com.zblog.common.util.StringUtils;
import com.zblog.common.util.constants.CategoryConstants;
import com.zblog.common.util.constants.WebConstants;
import com.zblog.common.util.web.ServletRequestReader;
import com.zblog.service.UploadService;

/**
 * ueditor上传参数见:http://fex-team.github.io/ueditor/#dev-request_specification
 * 
 * @author zhou
 * 
 */
@Component
public class Ueditor{
  @Autowired
  private UploadService uploadService;

  public MapContainer server(HttpServletRequest request){
    ServletRequestReader reader = new ServletRequestReader(request);
    String action = reader.getAsString("action");

    MapContainer result = null;
    if("config".equals(action)){
      result = config();
    }else if("uploadimage".equals(action)){
      result = uploadImage(reader);
    }else if("listimage".equals(action)){

    }else if("uploadfile".equals(action)){

    }else{
      result = new MapContainer("state", "SUCCESS");
    }

    return result;
  }

  private MapContainer config(){
    MapContainer config = new MapContainer();
    /* 上传图片配置项 */
    config.put("imageActionName", "uploadimage");
    config.put("imageFieldName", "upfile");
    config.put("imageMaxSize", 2048000);
    config.put("imageUrlPrefix", "");
    config.put("imageAllowFiles", Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp"));

    /* 上传文件配置 */
    config.put("fileActionName", "uploadfile");
    config.put("fileFieldName", "upfile");
    config.put("fileMaxSize", 51200000);
    config.put("fileAllowFiles", Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp", ".zip", ".tar", ".gz", ".7z"));

    /* 上传视频配置 */
    config.put("videoActionName", "uploadvideo");
    config.put("videoFieldName", "upfile");
    config.put("videoMaxSize", 102400000);
    config.put("videoAllowFiles",
        Arrays.asList(".flv", ".swf", ".mkv", ".avi", ".rmvb", ".mpeg", ".mpg", ".mov", ".wmv", ".mp4", ".webm"));

    /* 列出指定目录下的文件 */
    config.put("fileManagerActionName", "listfile");

    /* 列出指定目录下的图片 */
    config.put("imageManagerActionName", "listimage");

    return config;
  }

  public MapContainer uploadImage(ServletRequestReader reader){
    String uploadToken = reader.getAsString(CategoryConstants.UPLOAD_TOKEN);
    if(StringUtils.isBlank(uploadToken))
      return new MapContainer("state", "非法请求");

    MultipartFile file = reader.getFile("upfile");
    MapContainer result = new MapContainer("state", "SUCCESS");
    try{
      String yearMonth = DateUtils.currentDate("yyyy/MM");
      File parent = new File(reader.getRealPath("/post/uploads"), yearMonth);
      if(!parent.exists())
        parent.mkdirs();

      String fileName = DateUtils.currentDate("yyyyMMddhhmmss") + "."
          + FileUtils.getFileExt(file.getOriginalFilename());
      file.transferTo(new File(parent, fileName));
      result.put("original", file.getOriginalFilename());
      result.put("title", file.getOriginalFilename());
      result.put("url", WebConstants.DOMAIN + "/post/uploads/" + yearMonth + "/" + fileName);

      Upload upload = new Upload();
      upload.setId(IdGenarater.uuid19());
      upload.setCreateTime(new Date());
      upload.setName(file.getOriginalFilename());
      upload.setToken(uploadToken);
      upload.setPath("/post/uploads/" + yearMonth + "/" + fileName);

      uploadService.insert(upload);
    }catch(Exception e){
      result.put("state", "文件上传失败");
    }

    return result;
  }

}

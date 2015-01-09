package com.zblog.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.biz.UploadManager;
import com.zblog.biz.editor.Ueditor;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.web.ServletRequestReader;
import com.zblog.service.UploadService;

@Controller
@RequestMapping("/backend/uploads")
public class UploadController{
  @Autowired
  private Ueditor ueditor;
  @Autowired
  private UploadService uploadService;
  @Autowired
  private UploadManager uploadManager;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", uploadService.list(page, 15));
    return "backend/upload/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(HttpServletRequest request){
    uploadManager.insertUpload(new ServletRequestReader(request), "file");
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(){
    return "backend/upload/edit";
  }

  @ResponseBody
  @RequestMapping(value = "/{uploadid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("uploadid") String uploadid){
    uploadManager.removeUpload(uploadid);
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(value = "/ueditor")
  public Object ueditor(HttpServletRequest request){
    return ueditor.server(request);
  }

}

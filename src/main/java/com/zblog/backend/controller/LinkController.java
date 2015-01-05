package com.zblog.backend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zblog.backend.form.validator.LinkFormValidator;
import com.zblog.common.dal.entity.Link;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.IdGenarater;
import com.zblog.service.LinkService;

@Controller
@RequestMapping("/backend/links")
public class LinkController{
  @Autowired
  private LinkService linkService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", linkService.list(page, 15));
    return "backend/link/list";
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(Link link){
    MapContainer form = LinkFormValidator.validate(link);
    if(!form.isEmpty()){
      return form.put("success", false);
    }

    link.setLastUpdate(new Date());
    link.setCreator("admin");
    link.setId(IdGenarater.uuid19());
    link.setVisible(true);
    linkService.insert(link);
    return new MapContainer("success", true);
  }

  @ResponseBody
  @RequestMapping(value = "/{linkid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("linkid") String linkid){
    linkService.deleteById(linkid);
    return new MapContainer("success", true);
  }

  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(Model model){
    return "backend/link/edit";
  }

}

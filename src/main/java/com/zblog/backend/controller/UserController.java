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

import com.zblog.common.dal.entity.User;
import com.zblog.common.plugin.MapContainer;
import com.zblog.common.util.IdGenarater;
import com.zblog.service.UserService;

@Controller
@RequestMapping("/backend/users")
public class UserController{
  @Autowired
  private UserService userService;

  @RequestMapping(method = RequestMethod.GET)
  public String index(@RequestParam(value = "page", defaultValue = "1") int page, Model model){
    model.addAttribute("page", userService.list(page, 15));
    return "backend/user/list";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String insert(User user){
    user.setId(IdGenarater.uuid19());
    user.setCreateTime(new Date());
    user.setLastUpdate(new Date());
    return "backend/user/list";
  }

  @ResponseBody
  @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
  public Object remove(@PathVariable("userid") String userid){
    userService.deleteById(userid);
    return new MapContainer("success", true);
  }
  
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public String edit(){
    return "backend/user/edit";
  }

}

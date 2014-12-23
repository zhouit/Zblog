package com.zblog.backend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public String index(@RequestParam(value = "page", defaultValue = "1") int page,Model model){
    model.addAttribute("users", userService.list(page, 15));
    return "backend/users/list";
  }
  
  @ResponseBody
  @RequestMapping(method = RequestMethod.POST)
  public Object insert(User user){
    user.setId(IdGenarater.uuid19());
    user.setCreateTime(new Date());
    user.setLastUpdate(new Date());
    return new MapContainer("success", userService.insert(user));
  }

}

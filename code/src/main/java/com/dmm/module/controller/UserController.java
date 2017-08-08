package com.dmm.module.controller;

import com.dmm.module.domain.User;
import com.dmm.module.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户控制器Mode
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = {"list", ""},method = RequestMethod.GET)
    public String list(Model model){
        return "user/user_list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model model){
        return "user/user_add";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void add(User user){

        int insert = userService.insert(user);

    }

}
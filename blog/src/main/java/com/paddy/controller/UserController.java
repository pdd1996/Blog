package com.paddy.controller;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.User;
import com.paddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }
    @PutMapping ("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
}

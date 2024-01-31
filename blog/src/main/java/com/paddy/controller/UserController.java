package com.paddy.controller;

import com.paddy.annotation.SystemLog;
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
    @SystemLog(businessName = "查询个人信息")
    public ResponseResult userInfo() {
        return userService.userInfo();
    }
    @PutMapping ("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    @SystemLog(businessName = "用户注册")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }
}

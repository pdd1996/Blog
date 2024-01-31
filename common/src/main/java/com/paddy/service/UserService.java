package com.paddy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-12-06 17:33:14
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

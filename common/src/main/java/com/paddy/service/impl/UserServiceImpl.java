package com.paddy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.User;
import com.paddy.domain.vo.UserInfoVo;
import com.paddy.mapper.UserMapper;
import com.paddy.service.UserService;
import com.paddy.utils.BeanCopyUtils;
import com.paddy.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-12-06 17:33:15
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        // 获取当前用户的id
        Long userId = SecurityUtils.getUserId();
        // 根据用户id 去获取用户信息
        User user = getById(userId);
        // 封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
}


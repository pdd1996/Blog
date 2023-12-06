package com.paddy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.paddy.domain.entity.User;
import com.paddy.mapper.UserMapper;
import com.paddy.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-12-06 17:33:15
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}


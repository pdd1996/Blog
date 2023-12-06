package com.paddy.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paddy.domain.entity.User;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-19 19:42:04
 */
public interface UserMapper extends BaseMapper<User> {

    org.springframework.security.core.userdetails.User selectOne(LambdaQueryWrapper<org.springframework.security.core.userdetails.User> queryWrapper);
}


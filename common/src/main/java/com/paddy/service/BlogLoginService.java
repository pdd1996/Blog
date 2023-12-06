package com.paddy.service;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
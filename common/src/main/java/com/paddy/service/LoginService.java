package com.paddy.service;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.User;

public interface LoginService {

    ResponseResult login(User user);

}
package com.paddy.service.impl;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.LoginUser;
import com.paddy.domain.entity.User;
import com.paddy.service.LoginService;
import com.paddy.utils.JwtUtil;
import com.paddy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        System.out.println(authenticate+"authenticate");
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取 userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        System.out.println(loginUser+"loginUser");
        String userId = loginUser.getUser().getId().toString();
        System.out.println(userId+"userId");
        String jwt = JwtUtil.createJWT(userId);
        System.out.println(jwt+"jwt");
        // 把用户信息存入 redis
        redisCache.setCacheObject("login"+userId, loginUser);

        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

}

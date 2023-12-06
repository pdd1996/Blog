package com.paddy.service.impl;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.LoginUser;
import com.paddy.domain.entity.User;
import com.paddy.domain.vo.BlogUserLoginVo;
import com.paddy.domain.vo.UserInfoVo;
import com.paddy.service.BlogLoginService;
import com.paddy.utils.BeanCopyUtils;
import com.paddy.utils.JwtUtil;
import com.paddy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;



@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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
        redisCache.setCacheObject("bloglogin"+userId, loginUser);
        LoginUser abc = redisCache.getCacheObject("bloglogin:" + userId);
        System.out.println(abc+"abc");
        // 把 token 和 userInfo 封装
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        // 获取 token   解析 userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取 userId
        Long userId = loginUser.getUser().getId();
        // 删除 redis 中的用户信息
        redisCache.deleteObject("bloglogin" + userId);
        return ResponseResult.okResult();
    }
}

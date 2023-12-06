package com.paddy.filter;


import com.alibaba.fastjson.JSON;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.LoginUser;
import com.paddy.enums.AppHttpCodeEnum;
import com.paddy.utils.JwtUtil;
import com.paddy.utils.RedisCache;
import com.paddy.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的Token
        String token = request.getHeader("token");
        logger.info(token+"token");
        logger.info(StringUtils.hasText(token)+"StringUtils.hasText(token)");
        if (!StringUtils.hasText(token)){
            //说明该接口不需要登录  直接放行
            //filterChain.doFilter(request,response);
            filterChain.doFilter(request,response);
            return;
        }
        //解析获取userid
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
            logger.info(claims+"claims");
        } catch (Exception e) {
            e.printStackTrace();
            //token超时  token非法
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        logger.info(userId+"userId");
        //从redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin" + userId);
        logger.info(loginUser+"loginUser");
        //如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登录过期 提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        filterChain.doFilter(request,response);
    }
}
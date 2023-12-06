package com.paddy.handler.exception;

import com.paddy.domain.ResponseResult;
import com.paddy.enums.AppHttpCodeEnum;
import com.paddy.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){

        //打印异常信息
        log.error("出现了异常! {}",e);

        //从异常对象中获取提示信息封装，然后返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult systemExceptionHandler(Exception e){

        //打印异常信息
        log.error("出现了异常! {}",e);

        //从异常对象中获取提示信息封装，然后返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
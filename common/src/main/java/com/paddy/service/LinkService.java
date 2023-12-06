package com.paddy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-10-17 18:14:55
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}


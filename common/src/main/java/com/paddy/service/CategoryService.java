package com.paddy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-10-09 19:34:49
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}


package com.paddy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}

package com.paddy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.constants.SystemCanstants;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Article;
import com.paddy.domain.entity.Category;
import com.paddy.mapper.CategoryMapper;
import com.paddy.service.ArticleService;
import com.paddy.service.CategoryService;
import com.paddy.utils.BeanCopyUtils;
import com.paddy.domain.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-10-09 19:34:50
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        // 查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        // 获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        // 查询分类表
        List<Category> categories = listByIds(categoryIds);

        categories.stream().filter(category -> SystemCanstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}


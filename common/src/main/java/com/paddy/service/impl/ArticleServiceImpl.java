package com.paddy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.constants.SystemCanstants;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Article;
import com.paddy.domain.entity.Category;
import com.paddy.domain.vo.ArticleDetailVo;
import com.paddy.domain.vo.ArticleListVo;
import com.paddy.domain.vo.PageVo;
import com.paddy.mapper.ArticleMapper;
import com.paddy.service.ArticleService;
import com.paddy.service.CategoryService;
import com.paddy.utils.BeanCopyUtils;
import com.paddy.domain.vo.HotArticleVo;
import com.paddy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章 封装成ResponseResult 返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 必须是正式文章
        queryWrapper.eq(Article::   getStatus, 0);
        // 按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 最多只查询10条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);

        List<Article> articles = page.getRecords();
        // bean 拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        // 查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果有categoryId 查询时要和传入相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId > 0,Article::getCategoryId, categoryId);
        // 查询的是正式的
        lambdaQueryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        // 分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        // 查询categoryName

        // articleId去查询articleName进行设置
//        for (Article article: articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        articles.stream()
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        // 获取分类id 查询分类信息  获取分类名称
                        Category category = categoryService.getById(article.getCategoryId());
                        String name = category.getName();
                        // 把分类名称设置article
                        article.setCategoryName(name);
                        return article;
                    }
                })
                .collect(Collectors.toList());


        // 封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据 id 查询文章
        Article article = getById(id);
        // 从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("Article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        // 转化成 vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类 id 查询分类名称
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        // 封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        // 更新redis中对应id的浏览量
        redisCache.incrementCacheMapValue("Article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }
}

package com.paddy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-11-29 17:52:18
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}


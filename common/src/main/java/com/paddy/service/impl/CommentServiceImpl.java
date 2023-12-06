package com.paddy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.vo.CommentVo;
import com.paddy.domain.vo.PageVo;
import com.paddy.mapper.CommentMapper;
import com.paddy.domain.entity.Comment;
import com.paddy.service.CommentService;
import com.paddy.service.UserService;
import com.paddy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-11-29 17:52:18
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // 查询对应文章的根评论


        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        // 对articleId进行判断
        queryWrapper.eq(Comment::getArticleId, articleId);
        // 根评论 rootId -1
        queryWrapper.eq(Comment::getRootId, -1);
        // 分页查询
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(page.getRecords(), CommentVo.class);
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        // 遍历Vo
        for (CommentVo commentVo : commentVos) {
            // 通过creatBy查询用户的昵称

        }


        return  commentVos;
    }
}


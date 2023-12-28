package com.paddy.controller;

import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Comment;
import com.paddy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(articleId, pageNum, pageSize);
    }
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }
}

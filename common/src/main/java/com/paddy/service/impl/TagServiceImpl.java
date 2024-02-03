package com.paddy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.domain.entity.Tag;
import com.paddy.mapper.TagMapper;
import com.paddy.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-02-03 18:51:21
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}


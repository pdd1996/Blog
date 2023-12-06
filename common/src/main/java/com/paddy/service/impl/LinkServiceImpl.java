package com.paddy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.constants.SystemCanstants;
import com.paddy.domain.ResponseResult;
import com.paddy.domain.entity.Link;
import com.paddy.domain.vo.LinkVo;
import com.paddy.mapper.LinkMapper;
import com.paddy.service.LinkService;
import com.paddy.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-10-17 18:14:56
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        // 查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemCanstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换成 VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}


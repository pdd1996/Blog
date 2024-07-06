package com.paddy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.domain.entity.Role;
import com.paddy.service.RoleService;
import com.paddy.mapper.RoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}





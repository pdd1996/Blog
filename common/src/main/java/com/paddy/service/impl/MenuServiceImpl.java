package com.paddy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paddy.domain.entity.Menu;
import com.paddy.service.MenuService;
import com.paddy.mapper.MenuMapper;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}





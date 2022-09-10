package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entiry.Dish;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 19:25
 */
@Service("DishService")
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}

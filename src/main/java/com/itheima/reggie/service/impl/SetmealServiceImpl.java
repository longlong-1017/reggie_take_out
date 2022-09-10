package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entiry.Setmeal;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 19:29
 */
@Service("SetmealService")
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService{
}

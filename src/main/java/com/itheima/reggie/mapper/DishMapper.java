package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entiry.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 19:23
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

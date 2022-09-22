package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entiry.Dish;
import com.itheima.reggie.dto.DishDto;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 19:24
 */
public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void removeWithIds(List<Long> ids);
}

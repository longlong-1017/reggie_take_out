package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entiry.Setmeal;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 19:28
 */
public interface SetmealService extends IService<Setmeal> {
    SetmealDto getByIdWithDish(Long id);

    void saveWithDish(SetmealDto setmealDto);

    void updateWthDish(SetmealDto setmealDto);
}

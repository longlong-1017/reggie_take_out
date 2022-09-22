package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entiry.Setmeal;
import com.itheima.reggie.entiry.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 19:29
 */
@Service("SetmealService")
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public SetmealDto getByIdWithDish(Long id) {
        Setmeal setmeal=this.getById(id);

        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        //查询菜品信息，从setmeal_dish表中查
        LambdaQueryWrapper<SetmealDish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes=setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品关联信息
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public void updateWthDish(SetmealDto setmealDto) {
        this.updateById(setmealDto);

        List<SetmealDish> setmealDishes=setmealDto.getSetmealDishes();
        setmealDishes= setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //更新套餐和菜品关联信息
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);
        setmealDishService.saveOrUpdateBatch(setmealDishes);
    }
}

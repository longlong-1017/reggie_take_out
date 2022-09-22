package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entiry.Category;
import com.itheima.reggie.entiry.Setmeal;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: YunLong
 * @Date: 2022/9/22 19:33
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;


    /*
    * 分页查询 http://localhost:8080/setmeal/page?page=1&pageSize=10&name=maing
    * */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage=new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records=pageInfo.getRecords();
        List<SetmealDto> list=records.stream().map((item)->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId=item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category!=null){
                String categoryName=category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /*
    * 根据id查询套餐数据
    * http://localhost:8080/setmeal/1415580119015145474
    * */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        log.info(id.toString());
        SetmealDto setmealDto=setmealService.getByIdWithDish(id);
        return R.success(setmealDto);
    }

    /*
    http://localhost:8080/setmeal
    * */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);
        setmealService.updateWthDish(setmealDto);
        return R.success("更新套餐成功");
    }
}

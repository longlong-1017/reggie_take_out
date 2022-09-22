package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entiry.Category;
import com.itheima.reggie.entiry.Dish;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entiry.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: YunLong
 * @Date: 2022/9/14 23:34
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器
        Page<Dish> pageInfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage=new Page<>();

        //添加过滤条件
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records=pageInfo.getRecords();
        List<DishDto> list=records.stream().map((item)->{
            DishDto dishDto=new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId=item.getCategoryId();//分类id
            //根据id查询分类条件
            Category category=categoryService.getById(categoryId);

            if (category!=null){
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /*
    * 根据id查询菜品信息和对应的口味信息
    * */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto=dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /*
    * http://localhost:8080/dish/status/0?ids=1571168279446228994
    * 停售和启售
    * */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status,@RequestParam List<Long> ids){
        log.info(status.toString()+ids.toString());
        List<Dish> dishes=ids.stream().map((item)->{
           Dish dish=new Dish();
           dish.setId(item);
           dish.setStatus(status);
           return dish;
        }).collect(Collectors.toList());
        dishService.updateBatchById(dishes);
        return R.success("停售成功");
    }

    //http://localhost:8080/dish?ids=1570347703152484354
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("删除菜品，ids为{}",ids);
        dishService.removeWithIds(ids);
        return R.success("删除菜品成功");
    }

    /*
    * http://localhost:8080/dish/list?categoryId=1568557684465090562
    * */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list=dishService.list(queryWrapper);
        List<DishDto> dishDtoList=list.stream().map((item)->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            Category category=categoryService.getById(categoryId);
            if (category!=null){
                dishDto.setCategoryName(category.getName());
            }
            //当前菜品的id
            Long dishId=item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }
}

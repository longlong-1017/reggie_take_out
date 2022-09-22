package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entiry.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 17:41
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /*
    * 新增类 http://localhost:8080/category
    * */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /*
    * 分页查询
    * http://localhost:8080/category/page?page=1&pageSize=10
    * */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo=new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /*
    * 根据ID修改分类
    * */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息:{}",category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /*
    * 根据ID删除分类
    * */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类,id为：{}",ids);
        categoryService.removeById(ids);
        return R.success("删除成功");
    }

    //http://localhost:8080/category/list?type=1
    @GetMapping("list")
    public R<List<Category>> list(Category category){
        //查寻条件构造
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());

        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list=categoryService.list(queryWrapper);
        return R.success(list);
    }

}

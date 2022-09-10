package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entiry.Category;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 17:36
 */
public interface CategoryService extends IService<Category> {
    public void removeById(Long id);
}

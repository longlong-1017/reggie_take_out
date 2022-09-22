package com.itheima.reggie.dto;

import com.itheima.reggie.entiry.Dish;
import com.itheima.reggie.entiry.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/9/14 23:47
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors=new ArrayList<>();

    private String categoryName;

    private Integer copies;
}

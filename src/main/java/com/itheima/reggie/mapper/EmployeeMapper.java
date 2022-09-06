package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entiry.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: YunLong
 * @Date: 2022/9/4 23:21
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

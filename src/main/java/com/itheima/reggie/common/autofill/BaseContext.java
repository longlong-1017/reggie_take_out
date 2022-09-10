package com.itheima.reggie.common.autofill;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: YunLong
 * @Date: 2022/9/8 19:32
 */
@Slf4j
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    /*
    * 设置值
    * @param id
    * */
    public static void setCurrentId(Long id) {
        log.info("线程id{}",Thread.currentThread().getId());
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

package com.itheima.test;

import org.junit.jupiter.api.Test;

/**
 * @Author: YunLong
 * @Date: 2022/9/10 23:21
 */
public class UploadFileTest {
    @Test
    public void test1(){
        String fileName="ererewe.jpg";
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}

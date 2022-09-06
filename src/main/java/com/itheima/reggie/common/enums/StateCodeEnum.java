package com.itheima.reggie.common.enums;

/**
 * @Author: YunLong
 * @Date: 2022/9/4 23:06
 */
public enum StateCodeEnum {
    /**
     * 用户名密码不正确
     */
    PASSWORD_ERROR(1001,"用户名密码不正确"),

    /**
     * 用户不存在
     */
    USER_EMPTY(1002,"用户不存在"),

    /**
     * 请求失败
     */
    FAIL(0,"请求失败"),
    /**
     * 请求成功
     */
    SUCCESS(1,"请求成功");


    private  int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StateCodeEnum(int code , String msg){
        this.code = code;
        this.msg = msg;
    }
}

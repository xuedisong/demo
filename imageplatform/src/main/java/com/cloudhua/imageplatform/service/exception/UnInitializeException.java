package com.cloudhua.imageplatform.service.exception;

/**
 * Created by yangchao on 2017/8/16.
 * 未初始化错误
 */
public class UnInitializeException extends Exception{
    private String stat;

    private String message;

    public UnInitializeException(String stat, String message) {
        this.stat = stat;
        this.message = message;
    }

    public String getStat() {
        return stat;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

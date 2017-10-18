package com.cloudhua.imageplatform.service.exception;

/**
 * Created by yangchao on 2017/8/18.
 */
public class ParamException extends LogicalException {

    private static final long serialVersionUID = -2434647353423193324L;

    public ParamException(String msg) {
        super(StatusCode.ERR_BAD_PARAMS, msg);
    }
}

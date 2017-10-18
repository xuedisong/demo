package com.cloudhua.imageplatform.service.exception;

/**
 * Created by yangchao on 2017/8/21.
 */
public class RightException extends LogicalException {

    public RightException(String stat) {
        super(stat, "Permission denied.");
    }

    public RightException(String stat, String message) {
        super(stat, message);
    }
}

package com.cloudhua.imageplatform.service.exception;

/**
 * Created by yangchao on 2017/8/16.
 * 状态码信息集合
 */
public class StatusCode {

    /**
     * 服务端出现异常
     */
    public static final String ERR_SERVER_EXCEPTION = "ERR_SERVER_EXCEPTION";

    /**
     * 线程池未初始化
     */
    public static final String SCHEDULE_TASK_EXIST = "SCHEDULE_TASK_EXIST";

    /**
     * ufa工具类未初始化
     */
    public static final String UFA_NOT_INIT = "UFA_NOT_INIT";

    /**
     *  提交文件信息到ufa时出现错误（ufa服务器返回错误）
     */
    public static final String UFA_COMMIT_SERVER_EXCEPTION = "UFA_COMMIT_SERVER_EXCEPTION";

    /**
     *  mysql-update.sql格式出现错误
     */
    public static final String PARSER_UPDATE_SQL_ERROR = "PARSER_UPDATE_SQL_ERROR";

    /**
     * 获取服务端版本号出错
     */
    public static final String GET_XSERVER_REVERSION_ERROR = "GET_XSERVER_REVERSION_ERROR";

    /**
     * 资源文件未找到
     */
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";

    /**
     * 解析reqest中post数据为json时错误
     */
    public static final String ANALYSE_POST_JSON_DATA_ERROR = "ANALYSE_POST_JSON_DATA_ERROR";

    /**
     * 请求参数错误
     */
    public static final String ERR_BAD_PARAMS = "ERR_BAD_PARAMS";

    /**
     * 服务端返回response出现错误
     */
    public static final String RESPONSE_IO_ERROR = "RESPONSE_IO_ERROR";

    /**
     * 用户登录验证token失败
     */
    public static final String TOKEN_NOT_FOUND = "TOKEN_NOT_FOUND";

    /**
     * token过期
     */
    public static final String TOKEN_EXPIRES = "TOKEN_EXPIRES";

    /**
     * 无法找到用户信息
     */
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

    /**
     * 无权限访问
     */
    public static final String PERMISSION_DENIED = "PERMISSION_DENIED";

    /**
     * 方法参数出现空对象
     */
    public static final String PARAMS_IS_NULL = "PARAMS_IS_NULL";

    /**
     * 密码加密错误
     */
    public static final String PASSWORD_ENCODE_ERROR = "PASSWORD_ENCODE_ERROR";

    /**
     * 事件中断异常
     */
    public static final String EVENT_INTERRUPT = "EVENT_INTERRUPT";

    /********客户端请求数据出现错误*********/

    /**
     *  客户端格式不正确
     */
    public static final String CLIENT_PARAMS_ERROR = "CLIENT_PARAMS_ERROR";
}

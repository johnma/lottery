package com.iware.lottery;

/**
 * Created by johnma on 2016/11/2.
 */
public final class Constants {

    /**
     * prefix of REST API
     */
    public static final String URI_API = "/api";

    public static final String URI_TOKEN= "/token";

    public static final String URI_USERS = "/users";

    public static final String URI_COMMENTS = "/comments";

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHENTICATION = "Authentication";

    private Constants() {
        throw new InstantiationError( "Must not instantiate this class" );
    }

}
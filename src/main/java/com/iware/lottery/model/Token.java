package com.iware.lottery.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wuhao on 16/11/6.
 */


public class Token implements Serializable {

    //用户id
    private long userId;

    //随机生成的uuid
    private String token;

    public Token(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return  "Token{" + "userId=" + userId +", token=" + token +"}";
    }
}

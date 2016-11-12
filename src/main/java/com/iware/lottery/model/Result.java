package com.iware.lottery.model;

/**
 * 自定义返回结果
 * @author XieEnlong
 * @date 2015/7/14.
 */
public class Result {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public void setCode(int code){this.code = code;}
    public int getCode() {
        return code;
    }

    public void setMessage(String msg){this.message = msg;}
    public String getMessage() {
        return message;
    }
    public void setContent(Object cnt){this.content = cnt;}
    public Object getContent() {
        return content;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public Result(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }
}

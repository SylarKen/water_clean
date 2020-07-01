package com.sylar.water_clean.models;

import lombok.Data;

import java.io.Serializable;

/**
 * 异步返回结果封装
 */
@Data
public class Result implements Serializable {


    private final static int SUCCESS = 0;
    private final static int ERROR = 1;
    private final static int TIMEOUT = 2;
    private final static int NOAUTH = 3;
    private int code;//状态码
    private boolean result;//结果
    private String message;//提示信息
    private String action;
    private long total; // 总记录数
    private int count; // 总记录数
    private Object rows;// 返回给表格插件的数据
    private Object data;//返回数据

    public static Result success() {
        Result result = new Result();
        result.result = true;
        return result;
    }

    public static Result success(String message) {
        Result result = new Result();
        result.result = true;
        result.message = message;
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.result = true;
        result.data = data;
        return result;
    }

    public static Result confirm(String message) {
        Result result = new Result();
        result.result = false;
        result.message = message;
        result.action = "confirm";
        return result;
    }

    public static Result table(Object data,int count) {
        Result result = new Result();
        result.result = true;
        result.code = SUCCESS;
        result.count = count;
        result.data = data;
        return result;
    }

    public static Result charts(Object data) {
        Result result = new Result();
        result.code = SUCCESS;
        result.data = data;
        return result;
    }

    public static Result error(String message) {
        Result result = new Result();
        result.result = false;
        result.message = message;
        return result;
    }

    public static Result noAuth() {
        Result result = new Result();
        result.code = NOAUTH;
        return result;
    }

    public static Result dataList(Object rows, long total) {
        Result result = new Result();
        result.result = true;
        result.rows = rows;
        result.total = total;
        return result;
    }

    public static Result timeout() {
        Result result = new Result();
        result.code = TIMEOUT;
        return result;
    }
}

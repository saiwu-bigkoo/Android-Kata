package com.bigkoo.kataframework.bean;

import com.bigkoo.kataframework.http.constants.HttpStatusConstants;

/**
 * Created by Sai on 2018/3/15.
 */

public class HttpResult<T>  {
    /**
     * 默认约定返回 格式 ： {"code":0,"msg":"提示消息","data":{}}
     * status : 0
     * msg : 提示消息
     * data : {}
     */
    private int code = HttpStatusConstants.CODE_DEFAULT;//防止返回格式不给code
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

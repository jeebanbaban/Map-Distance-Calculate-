package com.jeeban.map.model;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    private int status;
    private String response;
    private String msg;
    private T data = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", response='" + response + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

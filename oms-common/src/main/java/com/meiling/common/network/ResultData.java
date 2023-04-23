package com.meiling.common.network;

public class ResultData<T> extends BaseBean {

    private T data;
 
    public T getData() {
        return data;
    }
 
    public void setData(T data) {
        this.data = data;
    }
 
}
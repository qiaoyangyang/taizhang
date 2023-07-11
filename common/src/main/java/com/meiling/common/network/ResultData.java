package com.meiling.common.network;

public class ResultData<T> extends BaseBean {

    private T result;
 
    public T getData() {
        return result;
    }
 
    public void setData(T result) {
        this.result = result;
    }
 
}
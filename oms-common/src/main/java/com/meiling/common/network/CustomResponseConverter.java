package com.meiling.common.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

//https://blog.csdn.net/qq_23900685/article/details/95899979z
//自定义响应体转换器
public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private TypeAdapter<T> adapter;
    private Type mType;

    CustomResponseConverter(Gson gson, TypeAdapter<T> mAdapter, Type mType) {
        this.gson = gson;
        this.adapter = mAdapter;
        this.mType = mType;
 
    }
 
    @Override
    public T convert(ResponseBody value) throws IOException {

        ResultData response=new ResultData();
        try {
            String body = value.string();
 
            JSONObject json = new JSONObject(body);
 
            int ret = json.optInt("code");
            String msg = json.optString("msg", "");
 
            if (ret == 200) {
                return gson.fromJson(body, mType);
            } else {
                response.setCode(ret);
                response.setMsg(msg);
                response.setData(json.opt("data"));
                return (T) response;

            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
package com.meiling.common.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zk on 2017/7/6.
 */

public class GsonUtils {

    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            Log.i("utils", e.toString());
            e.printStackTrace();
        }
        return t;
    }

    public static  <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            //创建解析对象
            JsonParser parser = new JsonParser();
            JsonArray jsonArray;
            Gson gson = new Gson();
            //得到message下的各个数据
            jsonArray = parser.parse(jsonString).getAsJsonArray();
            //遍历拿到每一个数据
            for (JsonElement message : jsonArray) {
                T messageCenterDTO = gson.fromJson(message, cls);
                list.add(messageCenterDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("utils", e.toString());
        }
        return list;
    }

    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return list;
    }

    public static String getJsonData(String jsonData) {
        String data="";
        try {
            JSONObject obj = new JSONObject(jsonData);
            data = obj.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}

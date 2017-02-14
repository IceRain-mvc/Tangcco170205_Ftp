package com.tangcco170205_ftp.utils;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2016/12/28.
 */

public class GsonUtils<T> {
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T t = gson.fromJson(json, clazz);
        return t;
    }
}

package com.example.myprojectbackend.entity;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

/**
 * ClassName:RestBean
 * Package:com.example.myprojectbackend.entity
 * Description:
 *
 * @Author FelixT
 * @Create 2023/8/14 10:51
 * @Version 1.0
 */
//状态信息
public record RestBean<T>(int code,T data,String message) {
    public static <T> RestBean<T> success(T data){
        return new RestBean<>(200,data,"请求成功");
    }

    public static <T> RestBean<T> success(){
        return success(null);
    }

    public static <T> RestBean<T> failure(int code,String message){
        return new RestBean<>(code,null,message);
    }

    public static <T> RestBean<T> unauthorized(String message){
        return failure(401,message);
    }

    public static <T> RestBean<T> forbidden(String message){
        return failure(403,message);
    }

    public String asJsonString(){
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}

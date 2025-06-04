package com.whut.xiaomi_work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer code;
    private String message;
    private Object data;

    public static Response success(){
        return new Response(200,"ok",null);
    }

    public static Response success(Object object){
        return new Response(200,"ok",object);
    }

    public static Response error(String message){
        return new Response(400,message,null);
    }
}

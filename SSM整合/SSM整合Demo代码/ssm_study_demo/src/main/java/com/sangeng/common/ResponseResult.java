package com.sangeng.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 统一响应格式
 * 所有响应给前端的数据都统一成这个格式进行返回
 * 便于前端对取到的数据进行统一处理
 * @param <T> 响应的数据类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // JSON字符串中只包含不为null的值
public class ResponseResult<T> {
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应的数据
     * 由于响应的数据类型可能有很多种，可能是User类, Car类, List类...
     * 因此使用泛型
     */
    private T data;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}

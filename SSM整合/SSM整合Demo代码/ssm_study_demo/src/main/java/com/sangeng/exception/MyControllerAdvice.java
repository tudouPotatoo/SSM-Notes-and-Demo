package com.sangeng.exception;

import com.sangeng.common.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

// 1.创建类加上@ControllerAdvice注解进行标识
@ControllerAdvice
// 3.将异常处理类注入Bean容器
// 实际上@ControllerAdvice注解本身就包含@Component的语义 因此这里的@Component可以省略
@Component
// @ResponseBody注解表明该类中所有方法的返回值直接作为响应体，而不是视图名。
// 确保异常处理方法返回的对象会被转换为响应体，而不是被视图解析器解析成视图。
@ResponseBody
public class MyControllerAdvice {
    // 2.定义异常处理方法，使用@ExceptionHandler标识进行处理的异常。
    @ExceptionHandler({Exception.class})
    public ResponseResult exceptionHandler(Exception ex) {
        return new ResponseResult(500, ex.getMessage());
    }
}

package com.sangeng.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// 1. 定义切面类 添加@Aspect注解
@Aspect
@Component
public class MyAspect {
    // 2. 编写切点
    /**
     * 切点
     * execution(* com.sangeng.service..*.*(..))
     * 表示第一个*表示任意返回值
     * com.sangeng.service..*.*(..)表示对com.sangeng.service包及其子包下的所有类的所有方法都生效
     * (..)表示任意参数
     */
    @Pointcut("execution(* com.sangeng.service..*.*(..))")
    private void pointcut() {

    }

    // 3. 编写增强逻辑
    /**
     * 增强逻辑
     * 对pointcut()配置的所有满足条件的方法 在执行之前都输出before
     */
    @Before("pointcut()")
    private void before() {
        System.out.println("before");
    }
}

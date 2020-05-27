package com.example.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 【说明】：app生命周期初始化注解
 *
 * @author daijun
 * @version 2.0
 * @date 2020/5/27 10:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterAppService {
    String value();
}

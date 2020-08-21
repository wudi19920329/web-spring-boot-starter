package com.cywetc.spring.boot.autoconfigure.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启web切面，包含异常处理、环绕增强controller日志处理
 *
 * @author wudi
 * @date 2020/8/18 11:52
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WebAopConfigurationSelector.class)
public @interface EnableWebAop {

}

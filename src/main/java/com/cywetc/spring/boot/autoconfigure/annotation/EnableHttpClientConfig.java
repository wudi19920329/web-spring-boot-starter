package com.cywetc.spring.boot.autoconfigure.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

/**
 * 开启httpclient配置
 *
 * @author wudi
 * @date 2020/8/18 11:52
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HttpClientConfigurationSelector.class)
@EnableScheduling
public @interface EnableHttpClientConfig {

}

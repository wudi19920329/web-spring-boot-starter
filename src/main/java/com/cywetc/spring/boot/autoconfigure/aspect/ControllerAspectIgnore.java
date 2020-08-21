package com.cywetc.spring.boot.autoconfigure.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author wudi
 * @date 2019/11/30 15:18
 * @since 1.0.0
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerAspectIgnore {
}

package com.cywetc.spring.boot.autoconfigure.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 选择导入
 *
 * @author wudi
 * @date 2020/8/18 14:26
 * @since 1.0.0
 */
public class WebAopConfigurationSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] { "com.cywetc.spring.boot.autoconfigure.aspect.LoggingAspect", "com.cywetc.spring.boot.autoconfigure.exception.GlobalExceptionAdvice" };
	}
}

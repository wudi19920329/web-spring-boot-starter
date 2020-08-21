package com.cywetc.spring.boot.autoconfigure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * mapper定制化
 *
 * @author wudi
 * @date 2020/8/20 11:12
 * @since 1.0.0
 */
public class ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {
	@Override
	public void customize(Jackson2ObjectMapperBuilder builder) {
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Override
	public int getOrder() {
		return 1;
	}
}

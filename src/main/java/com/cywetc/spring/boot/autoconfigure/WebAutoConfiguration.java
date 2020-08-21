package com.cywetc.spring.boot.autoconfigure;

import com.cywetc.spring.boot.autoconfigure.config.GracefulShutdownTomcatConfig;
import com.cywetc.spring.boot.autoconfigure.config.ObjectMapperBuilderCustomizer;
import com.cywetc.spring.boot.autoconfigure.properties.WebProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * web自动配置
 *
 * @author woody
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(WebProperties.class)
@Import({ GracefulShutdownTomcatConfig.class })
public class WebAutoConfiguration {

	@Bean
	@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
	ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer() {
		return new ObjectMapperBuilderCustomizer();
	}

}

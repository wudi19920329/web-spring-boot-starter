package com.cywetc.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自动配置属性
 *
 * @author wudi
 * @date 2020/8/3 16:11
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = WebProperties.WEB_PREFIX)
@Data
public class WebProperties {
	public static final String WEB_PREFIX = "web";

	private boolean openErrorPageExceptionHandle = false;
	/**
	 * 系统异常
	 */
	private String errorPage = "error";
	/**
	 * 温馨提示
	 */
	private String errorTipPage = "errorTip";

}

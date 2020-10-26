package com.cywetc.spring.boot.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * httpclient连接池路由配置
 *
 * @author wudi
 * @date 2020/5/28 18:21
 * @since 1.0.0
 */
@ConfigurationProperties("httpclient.pool")
@Data
public class HttpClientPoolingProperties {
	/**
	 * 连接池中最多持有maxTotal个连接
	 */
	private int maxTotal;
	/**
	 * 每个目标服务器最多持有maxPerRoute个连接
	 */
	private int maxPerRoute;

	/**
	 * 空闲多久关闭连接池中的连接，单位毫秒
	 */
	private long idleTimeout;

}

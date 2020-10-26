package com.cywetc.spring.boot.autoconfigure.config;

import com.cywetc.spring.boot.autoconfigure.properties.HttpClientPoolingProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author wudi
 * @date 2020/3/24 8:31
 * @since 1.0.0
 */
@Slf4j
public class HttpClientConfig {

	private final static int GET_CONNECTION_FROM_POOL_TIME_OUT = 5000;
	private final static int HTTP_TIME_OUT_MILLISECOND = 10000;

	public HttpClientConfig() {
		log.info("初始化httpClient连接池配置");
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, CloseableHttpClient httpClient) {
		RestTemplate restTemplate = restTemplateBuilder.requestFactory(() -> {
			HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
			clientHttpRequestFactory.setHttpClient(httpClient);
			return clientHttpRequestFactory;
		}).build();
		return restTemplate;
	}

	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager(HttpClientPoolingProperties httpClientPoolingProperties) {
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
		poolingConnectionManager.setMaxTotal(httpClientPoolingProperties.getMaxTotal());
		poolingConnectionManager.setDefaultMaxPerRoute(httpClientPoolingProperties.getMaxPerRoute());
		return poolingConnectionManager;
	}

	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingConnectionManager) {
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(GET_CONNECTION_FROM_POOL_TIME_OUT).setConnectTimeout(HTTP_TIME_OUT_MILLISECOND)
				.setSocketTimeout(HTTP_TIME_OUT_MILLISECOND).build();
		return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(poolingConnectionManager).build();
	}
}

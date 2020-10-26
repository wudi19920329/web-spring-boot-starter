package com.cywetc.spring.boot.autoconfigure.config;

import com.cywetc.spring.boot.autoconfigure.properties.HttpClientPoolingProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * 不使用httpclient自带的关闭线程，控制精度不够
 * httpclient关闭空闲连接，如果每次获取连接时都要去判断连接是否过期或者关闭，会造成一定的性能损耗。
 * 源码见AbstractConnPool的getPoolEntryBlocking方法中的validateAfterInactivity
 *
 * @author wudih
 * @date 2019-12-06 9:51
 * @since master
 */
@Slf4j
public class IdleConnectionEvictorConfig {

	@Bean
	public Runnable getIdleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager, HttpClientPoolingProperties httpClientPoolingProperties) {
		return new Runnable() {
			@Override
			@Scheduled(fixedDelay = 10 * 1000)
			public void run() {
				try {
					if (connectionManager != null) {
						connectionManager.closeExpiredConnections();
						connectionManager.closeIdleConnections(httpClientPoolingProperties.getIdleTimeout(), TimeUnit.MILLISECONDS);
						log.debug("pool stat {}", connectionManager.getTotalStats().toString());
						return;
					}
					log.info("httpclient连接管理器未初始化...");
				} catch (Exception e) {
					log.error("httpclient关闭空闲连接异常,", e);
				}
			}
		};
	}
}

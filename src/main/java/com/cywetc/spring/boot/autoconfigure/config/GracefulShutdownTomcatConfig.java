package com.cywetc.spring.boot.autoconfigure.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优雅关闭tomcat容器配置,防止直接kill -9正在执行的线程
 * 请使用kill -15进行关闭
 *
 * @author wudi
 * @date 2020/5/18 15:13
 * @since 1.0.0
 */
@Slf4j
public class GracefulShutdownTomcatConfig {

	@Bean
	public GracefulShutdown gracefulShutdown() {
		return new GracefulShutdown();
	}

	static class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
		private static final int TIMEOUT = 30;

		private volatile Connector connector;

		@Override
		public void customize(Connector connector) {
			this.connector = connector;
		}

		@Override
		public void onApplicationEvent(ContextClosedEvent event) {
			log.info("开始优雅关闭tomcat容器...");
			this.connector.pause();
			Executor executor = this.connector.getProtocolHandler().getExecutor();
			if (executor instanceof ThreadPoolExecutor) {
				try {
					ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
					threadPoolExecutor.shutdown();
					if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
						log.warn("Tomcat线程池{}秒内没有优雅关闭,准备强制关闭", TIMEOUT);

						threadPoolExecutor.shutdownNow();

						if (!threadPoolExecutor.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
							log.error("Tomcat线程池没有停止");
						}
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}

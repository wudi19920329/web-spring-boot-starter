package com.cywetc.spring.boot.autoconfigure.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务异常
 *
 * @author wudi
 * @date 2020/3/23 10:04
 * @since 1.0.0
 */
@Getter
@Slf4j
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 5229782376299647090L;

	private int code;

	public ServiceException(int code, String message) {
		super(message);
		this.code = code;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

}

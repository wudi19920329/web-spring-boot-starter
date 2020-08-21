package com.cywetc.spring.boot.autoconfigure.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局响应码
 *
 * @author wudi
 * @date 2020/3/20 15:42
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum GlobalCode {

	SUCCESS(200, "成功"),

	UNAUTHORIZED(401, "请先登录"),

	SYSTEM_ERROR(500, "系统异常");

	private int code;
	private String message;
}

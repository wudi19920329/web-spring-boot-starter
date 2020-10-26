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

	SUCCESS("000000", "成功"),
	ARGS_VALID_ERROR("999998", "参数校验异常"),
	SYSTEM_ERROR("999999", "系统异常");

	private String code;
	private String message;
}

package com.cywetc.spring.boot.autoconfigure.exception;

import com.cywetc.spring.boot.autoconfigure.dto.BaseResEntity;
import com.cywetc.spring.boot.autoconfigure.enums.GlobalCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author wudi
 * @date 2019/11/29 10:53
 * @since 1.0.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(value = ServiceException.class)
	public BaseResEntity<Void> handleRuntimeException(ServiceException e) {
		final String defaultMessage = "业务异常";
		log.error(defaultMessage, e);
		String errMessage = e.getMessage();
		return BaseResEntity
				.exception(StringUtils.isBlank(e.getCode()) ? GlobalCode.SYSTEM_ERROR.getCode() : e.getCode(), StringUtils.isBlank(errMessage) ? defaultMessage : errMessage);
	}

	@ResponseBody
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public BaseResEntity<Void> handleValidationException(MethodArgumentNotValidException e) {
		log.error("参数校验异常:", e);
		String error = e.getBindingResult().getAllErrors().stream().findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("参数校验失败");
		return BaseResEntity.exception(GlobalCode.ARGS_VALID_ERROR.getCode(), error);
	}

	@ResponseBody
	@ExceptionHandler({ Exception.class })
	public BaseResEntity<Void> handleException(Exception e) {
		log.error("系统异常:", e);
		return BaseResEntity.exception(GlobalCode.SYSTEM_ERROR.getCode(), GlobalCode.SYSTEM_ERROR.getMessage());
	}

}

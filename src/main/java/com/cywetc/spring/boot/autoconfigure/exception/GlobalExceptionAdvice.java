package com.cywetc.spring.boot.autoconfigure.exception;

import com.cywetc.spring.boot.autoconfigure.dto.BaseResEntity;
import com.cywetc.spring.boot.autoconfigure.enums.GlobalCode;
import com.cywetc.spring.boot.autoconfigure.properties.WebProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

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
	@Autowired
	private WebProperties properties;
	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(value = ServiceException.class)
	public String handleRuntimeException(HttpServletRequest request, HttpServletResponse response, ServiceException e) throws Exception {
		final String message = "业务异常";
		log.error(message, e);
		String errMessage = e.getMessage();
		if (!properties.isOpenErrorPageExceptionHandle() && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
			BaseResEntity<Void> exceptionInfo =
					BaseResEntity.exception(e.getCode() == 0 ? HttpStatus.BAD_REQUEST.value() : e.getCode(), StringUtils.isBlank(errMessage) ? message : errMessage);

			response.getOutputStream().write(objectMapper.writeValueAsString(exceptionInfo).getBytes(Charset.defaultCharset()));
			response.setStatus(510);
			response.getOutputStream().flush();
			return null;
		}
		request.setAttribute("errMessage", errMessage);
		return properties.getErrorTipPage();
	}

	@ExceptionHandler(value = Exception.class)
	public String handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
		log.error("系统异常:", e);

		if (!properties.isOpenErrorPageExceptionHandle() && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
			BaseResEntity<Void> exceptionInfo = BaseResEntity.exception(GlobalCode.SYSTEM_ERROR.getCode(), GlobalCode.SYSTEM_ERROR.getMessage());
			response.getOutputStream().write(objectMapper.writeValueAsString(exceptionInfo).getBytes(Charset.defaultCharset()));
			response.setStatus(510);
			response.getOutputStream().flush();
			return null;
		}

		request.setAttribute("errMessage", GlobalCode.SYSTEM_ERROR.getMessage());
		return properties.getErrorPage();
	}

}

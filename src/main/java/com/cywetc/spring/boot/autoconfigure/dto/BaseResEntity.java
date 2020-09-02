package com.cywetc.spring.boot.autoconfigure.dto;

import com.cywetc.spring.boot.autoconfigure.enums.GlobalCode;
import com.cywetc.spring.boot.autoconfigure.exception.ServiceException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wudi
 * @date 2020/3/23 9:00
 * @since 1.0.0
 */
@Data
public class BaseResEntity<T>  {
	private int code;
	private String message;
	private T data;

	public static BaseResEntity<Void> exception(int code, String message) {
		BaseResEntity<Void> baseResEntity = new BaseResEntity<>();
		baseResEntity.setCode(code);
		baseResEntity.setMessage(message);
		return baseResEntity;
	}

	public static <T> BaseResEntity<T> ok(T data) {
		BaseResEntity<T> baseResEntity = new BaseResEntity<>();
		baseResEntity.setCode(GlobalCode.SUCCESS.getCode());
		baseResEntity.setMessage(GlobalCode.SUCCESS.getMessage());
		baseResEntity.setData(data);
		return baseResEntity;
	}

	public static <T> BaseResEntity<Void> ok() {
		BaseResEntity<Void> baseResEntity = new BaseResEntity<>();
		baseResEntity.setCode(GlobalCode.SUCCESS.getCode());
		baseResEntity.setMessage(GlobalCode.SUCCESS.getMessage());
		return baseResEntity;
	}

	public void checkResponseVoidCode() {
		if (this.code != GlobalCode.SUCCESS.getCode()) {
			throw new ServiceException(StringUtils.isBlank(message) ? GlobalCode.SYSTEM_ERROR.getMessage() : message);
		}
	}

}

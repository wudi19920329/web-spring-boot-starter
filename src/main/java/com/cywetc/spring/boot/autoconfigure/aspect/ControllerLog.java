package com.cywetc.spring.boot.autoconfigure.aspect;

import lombok.Data;

import java.util.List;

@Data
public class ControllerLog {
	private String url;
	private List<Object> args;
	private String queryString;
	private Long time;
	private String result;
}

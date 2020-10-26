package com.cywetc.spring.boot.autoconfigure.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author woody
 * 控制器拦截
 */
@Aspect
@Slf4j
public class LoggingAspect {

	@Autowired
	private ObjectMapper objectMapper;

	public LoggingAspect() {
	}

	@Around("execution(public * com.cywetc..*Controller.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		StopWatch watch = new StopWatch();
		watch.start();
		String url = request.getRequestURI();
		String queryString = request.getQueryString();
		List<Object> params = new ArrayList();
		Method method = getPjpMethod(pjp);
		if (null == method.getAnnotation(ControllerAspectIgnore.class)) {
			for(int i = 0; i < method.getParameterAnnotations().length; ++i) {
				boolean b = false;
				Annotation[] var10 = method.getParameterAnnotations()[i];
				int var11 = var10.length;

				for(int var12 = 0; var12 < var11; ++var12) {
					Annotation ann = var10[var12];
					if (ControllerAspectIgnore.class.isInstance(ann)) {
						b = true;
					}
				}

				if (!b) {
					params.add(pjp.getArgs()[i]);
				}
			}
		}

		ControllerLog controllerLog = new ControllerLog();
		controllerLog.setUrl(url);
		controllerLog.setArgs(params);
		controllerLog.setQueryString(queryString);

		Object result;
		try {
			result = pjp.proceed();
		} catch (Throwable var17) {
			throw var17;
		} finally {
			watch.stop();
			controllerLog.setTime(watch.getTotalTimeMillis());
			log.info("request info :{}", this.objectMapper.writeValueAsString(controllerLog));
		}

		return result;
	}

	private static Method getPjpMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
		Class<?> clazz = methodSignature.getDeclaringType();
		Method method = clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		return method;
	}

}

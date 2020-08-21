package com.cywetc.spring.boot.autoconfigure.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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

	@Pointcut("within(@org.springframework.stereotype.Controller *)" + " || within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}

	@Around("springBeanPointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		StopWatch watch = new StopWatch();
		watch.start();
		String url = request.getRequestURI();
		String queryString = request.getQueryString();
		List<Object> params = new ArrayList<>();
		Method method = getPjpMethod(pjp);
		if (null == method.getAnnotation(ControllerAspectIgnore.class)) {
			for (int i = 0; i < method.getParameterAnnotations().length; ++i) {
				boolean b = false;
				for (final Annotation ann : method.getParameterAnnotations()[i]) {
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
		} catch (Throwable throwable) {
			throw throwable;
		} finally {
			watch.stop();
			controllerLog.setTime(watch.getTotalTimeMillis());
			log.info("request info :{}", objectMapper.writeValueAsString(controllerLog));
		}
		return result;
	}

	/**
	 * 获取反映的方法
	 *
	 * @param pjp
	 * @return
	 * @throws NoSuchMethodException
	 */
	private static Method getPjpMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Class<?> clazz = methodSignature.getDeclaringType();
		Method method = clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		return method;
	}

}

package com.jluque.w2m.utils.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class MetricsAspect {

	private StopWatch watch;

	@Pointcut("@annotation(com.jluque.w2m.utils.anotation.CustomRequestTimed)")
	public void allResources() {
	}

	@Before("allResources()")
	public void requestTimed(JoinPoint joinPoint) {
		watch = new StopWatch();
		watch.start();
		StringBuilder logInfo = new StringBuilder();
		logInfo.append(">>>>>>>> START REQUEST >>>>>>>> ");
		logInfo.append("Class: ".concat(joinPoint.getSignature().getDeclaringTypeName()));
		logInfo.append("::".concat(joinPoint.getSignature().getName()));
		log.info(logInfo.toString());
	}

	@AfterReturning(pointcut = "allResources()", returning = "response")
	public void responseLog(JoinPoint jp, Object response) {
		watch.stop();
		StringBuilder logInfo = new StringBuilder();
		logInfo.append("<<<<<<<< END RESPONSE <<<<<<<< ");
		logInfo.append("Request Duration: ".concat(String.valueOf(watch.getTotalTimeMillis())).concat(" ms. "));
		logInfo.append("Response: ".concat(response.toString()));
		log.info(logInfo.toString());
	}
}

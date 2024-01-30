package com.liqu.wiki.config;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.liqu.wiki.dto.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AOP 적용 클래스
 */
@Component
@Aspect
public class BindingAdvice {
	private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);
	
	/**
	 * 전체 Controller 공통 전처리
	 */
	/*
	@Before("execution(* com.liqu.wiki..*Controller.*(..))")
	public void preProcController() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	*/
	
	/**
	 * 전체 Controller 공통 후처리
	 */
	/*
	@After("execution(* com.liqu.wiki..*Controller.*(..))")
	public void aftProcController() {
		
	}
	*/
	
	/**
	 * 전체 Controller 공통 전처리 + 후처리
	 * @param proceedingJoinPoint : request를 처리할 메소드 리플렉션 
	 * @throws Throwable 
	 */
	@Around("execution(* com.liqu.wiki..*Controller.*(..))")
	public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		String type 	= proceedingJoinPoint.getSignature().getDeclaringTypeName(); 	// type name. 클래스 이름.
		String method 	= proceedingJoinPoint.getSignature().getName(); 				// 메소드 이름.
		Object[] args 	= proceedingJoinPoint.getArgs();
		
		for(Object arg : args) {
			
			// request 파라미터 validation 체크 - DTO 필드 체크
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult)arg;
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()){
						errorMap.put(error.getField(), error.getDefaultMessage());
						log.warn("bindingResult.hasErrors() : " + type + "." + method + "() => 필드 : " + error.getField() + ", message : " + error.getDefaultMessage());
					}
					
					return new ResponseDto<Map>(HttpStatus.BAD_REQUEST.value(), errorMap);
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
}

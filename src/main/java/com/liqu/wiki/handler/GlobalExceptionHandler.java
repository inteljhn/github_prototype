package com.liqu.wiki.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.dto.ResponseDto;

/**
 * Global Exception Handler
 * 레이어와 관계없이 별도로 제어하지 않으면 Exception 발생시 이 클래스에서 핸들링함 
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		log.debug("GlobalExceptionHandler.handleArgumentException(Exception) Run..");
		
		//return "<h1>" + e.getMessage() + "</h1>";
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
		log.debug("GlobalExceptionHandler.handleArgumentException(IllegalArgumentException) Run..");
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "IllegalArgumentException !!! : " + e.getMessage());
	}
}

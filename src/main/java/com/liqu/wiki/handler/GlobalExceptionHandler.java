package com.liqu.wiki.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.dto.ResponseDto;

/**
 * Global Exception Handler
 * 레이어와 관계없이 메소드에 별도로 throws 작성하지 않으면 Exception 발생시 이 클래스에서 핸들링함 
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	//@ExceptionHandler(value = IllegalArgumentException.class)
	@ExceptionHandler(value = Exception.class)
	//public String handleArgumentException(IllegalArgumentException e) {
	//public String handleArgumentException(Exception e) {
	public ResponseDto<String> handleArgumentException(Exception e) {
		System.out.println("GlobalExceptionHandler Run Exception !!");
		
		//return "<h1>" + e.getMessage() + "</h1>";
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseDto<String> handleArgumentException(IllegalArgumentException e) {
		System.out.println("GlobalExceptionHandler Run IllegalArgumentException !!");
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "IllegalArgumentException !!! : " + e.getMessage());
	}
}

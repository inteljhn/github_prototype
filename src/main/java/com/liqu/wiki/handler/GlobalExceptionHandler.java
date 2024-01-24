package com.liqu.wiki.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.dto.ResponseDto;


@ControllerAdvice // @ControllerAdvice : 모든 익셉션이 발생했을 때 이 컨트롤러가 핸들링하게 하는 어노테이션 이라고 함..
@RestController
public class GlobalExceptionHandler {

	//@ExceptionHandler(value = IllegalArgumentException.class)
	@ExceptionHandler(value = Exception.class)
	//public String handleArgumentException(IllegalArgumentException e) {
	//public String handleArgumentException(Exception e) {
	public ResponseDto<String> handleArgumentException(Exception e) {
		
		System.out.println("GlobalExceptionHandler Run !!");
		
		//return "<h1>" + e.getMessage() + "</h1>";
		
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}

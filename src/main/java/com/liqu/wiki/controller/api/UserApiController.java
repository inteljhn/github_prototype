package com.liqu.wiki.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.config.auth.PrincipalDetail;
import com.liqu.wiki.dto.JoinReqDto;
import com.liqu.wiki.dto.ResponseDto;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserApiController {
	private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// HttpSession 객체를 @Autowired 하면 각 메서드마다 매개변수로 받아올 필요가 없음.
	// @Autowired
	// private HttpSession session;

	/**
	 * 로그인 처리
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/auth/joinProc")
	public ResponseDto<?> regJoinUser(@Valid  @RequestBody JoinReqDto user, BindingResult bindingResult){
		log.debug("UserApiController.regJoinUser() run !!");

		userService.regJoinUser(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	// 2024-01-13. planthoon.
	// Spring Security를 활용한 방법으로 로그인을 구현하기 위해 주석처리
//	@PostMapping("/api/user/login")
//	//public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//	public ResponseDto<Integer> login(@RequestBody User user) {
//		System.out.println("UserApiController.login() run !!");
//		
//		User principal = userService.로그인(user); // principal : 접근 주체
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
//	}
	

	/**
	 * 
	 * @param user
	 * @param principal
	 * @return
	 */
	@PutMapping("/user")
	public ResponseDto<Integer> updUser(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal) {
		log.debug("UserApiController.updUser() run !!");

		userService.updUser(user);
		
		// 2024-01-19. planthoon. TODO
		// 아래 내용은 세션에 개발자가 직접 접근하여 세션을 수정한다기 보다는 로그인 절차를 다시 진행하는 것과 비슷한 의미가 있음
		// 따라서.. 패스워드를 변경하지 않았다던지 하는 경우에 대한 고려가 추가로 필요함.
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}

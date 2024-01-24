package com.liqu.wiki.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.config.auth.PrincipalDetail;
import com.liqu.wiki.dto.ResponseDto;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// HttpSession 객체를 이런 식으로 DI하면 각 메소드마다 매개변수로 받아올 필요가 없음.
//	@Autowired
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController.save() run !!");

		//user.setRole(RoleType.USER);
		
		// 39강에서는.. 나중에 실제로 DB에 insert를 하고 아래에서 갱신된 row 수를 return 하도록 할 예정이라고 했음
		//int result = userService.회원가입(user);
		userService.회원가입(user);
		
		//return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	// 2024-01-13. planthoon. 아래와 같이 하는 방법은 전통적인 로그인/세션 처리.
	// 메타코딩 강좌간에 스프링 시큐리티를 활용한 방법으로 로그인을 구현하기 위해 주석처리
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
	
	
	// 화면단에서 ajax 호출할 때 ```data: JSON.stringify(data)```로 보낸 json 데이터를 수신하기 위해서 @RequestBody 사용
	// @RequestBody 없으면 key / value 형식의 form을 서버로 보내야 Object 타입으로 받아줄 수 있음
	@PutMapping("/user")
	public ResponseDto<Integer> update(
		@RequestBody User user,
		@AuthenticationPrincipal PrincipalDetail principal
		//HttpSession session
	) {
		System.out.println("UserApiController.update() run !!");

		userService.회원수정(user);
		
		// 서비스가 종료되는 시점에 트랜잭션이 종료되기 때문에 DB에 저장된 데이터는 변경되었지만
		// 세션값은 변경되지 않은 상태이기 때문에 직접 세션값을 변경
		// 2024-01-19. planthoon. 61강에서 아래와 같이 세션 내부의 객체를 직접 수정하는 방법을 시도하였으나 
		// 정상적으로 작동하지 않고 에러가 발생하여 다른 방법을 사용함
		/*
		Authentication authentication 	= new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		// 세션 내부에 스프링 시큐리티 컨텍스트
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		*/
		
		// 그냥 이렇게만 해도 되긴 하는데..
		//principal.getUser().setEmail(user.getEmail());
		// SecurityConfig에 AuthenticationManager @Bean 등록하고
		//서비스단에서 처리하는 방법으로 진행되었음 
		// -> 서비스단에서 하면 패스워드 변경 이후 commit이 끝나지 않은 상태로 로그인 진행이 불가한 문제가 있어서 컨트롤러로 다시 이동
		
		
		// 2024-01-19. planthoon. TODO
		// 아래 내용은 세션에 개발자가 직접 접근하여 세션을 수정한다기 보다는 로그인 절차를 다시 진행하는 것과 비슷한 의미가 있음
		// 따라서.. 패스워드를 변경하지 않았다던지 하는 경우에 대한 고려가 추가로 필요함.
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}

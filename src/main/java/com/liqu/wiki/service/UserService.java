package com.liqu.wiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liqu.wiki.dto.JoinReqDto;
import com.liqu.wiki.entity.RoleType;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.repository.UserRepository;




// 서비스가 필요한 이유
// 1. 트랜잭션 관리
// 2. 특정 프로그램이 '서비스' 프로그램이라는 것을 관리해야 하기 때문
// 서비스 : 최종 목표 결과를 도출하기 위한 코드와 로직들을 하나의 메소드에 담는 것.
// 서비스 레이어에서 여러개의 트랜잭션을 묶어서 하나의 트랜잭션으로 관리한다...

// @Service 어노테이션 사용으로 스프링이 컴포넌트 스캔을 통해 Bean에 등록을 해줌. (IOC)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//@Autowired
	//private AuthenticationManager authenticationManager;
	
	
	public void dummyTest(User user) {
		throw new IllegalArgumentException("고의 에러 발생");
	}
	
	@Transactional
	//public int 회원가입(User user) {
	//public void 회원가입(User user) {
	public void 회원가입(JoinReqDto joinReqDto) {
		String rawPassword = joinReqDto.getPassword(); 			// password 원문
		String encPassword = encoder.encode(rawPassword); 	// 해쉬된 password
		
		User user = new User();
		user.setUserName(joinReqDto.getUserName());
		user.setRole(RoleType.USER); 	
		user.setPassword(encPassword);
		user.setEmail(joinReqDto.getEmail());
		
		/*
		try {
			userRepository.save(user);
			
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("UserService 회원가입() 에러 : " + e.getMessage());
		}
		
		return -1;
		*/
		
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); 			// password 원문
		String encPassword = encoder.encode(rawPassword); 	// 해쉬된 password
		
		user.setRole(RoleType.USER); 	
		user.setPassword(encPassword); 	

		userRepository.save(user);
	}
	
	// Spring Security를 활용한 로그인을 구현하기 위해 주석처리
	// @Transactional(readOnly = true) : Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 확보)
//	@Transactional(readOnly = true)
//	public User 로그인(User user) {
//		return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
//	}


	@Transactional
	public void 회원수정(User user) {
		// 데이터 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		// Select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 Update
		
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
		}); // Board 영속화
		
		// Validate 체크 => oauth 필드에 값이 없을때만 수정 가능
		if(persistance.getOauth() == null || persistance.getOauth().equals(persistance)) {
		
			if(user.getPassword() != null && !user.getPassword().trim().isEmpty()){
				String rawPassword = user.getPassword(); 			// password 원문
				String encPassword = encoder.encode(rawPassword); 	// 해쉬된 password
				
				persistance.setPassword(encPassword);
			}
			
			persistance.setEmail(user.getEmail());
		}
		
		
		
		
		
		// 세션 등록
		// 서비스단에서 하니까 트랜잭션이 종료되지 않은 상황에서 password가 불일치하여 BadCredentialsException 발생.
		// org.springframework.security.authentication.BadCredentialsException: 자격 증명에 실패하였습니다.
		// 강의에서 위와같이 이야기 하는데 기존과 같은 패스워드를 넣어도 동일한 오류 발생. 암호화를 적용한 문자열을 넣어도 마찬가지.
		// 컨트롤러로 옮기도록 하길래 일단 따라감
		//Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		//Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), encoder.encode(user.getPassword())));
		//SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		// 회원수정 메소드 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 수행
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌
	}


	@Transactional(readOnly = true)
	public User 회원찾기(String userName) {
		
		// orElseGet : 데이터가 조회되지 않으면 내부 return을 수행
		User user = userRepository.findByUserName(userName).orElseGet(()->{
			return new User();
		});
		
		
		// 이렇게 하면 데이터가 없으면 null을 반환함
		// 강의중에 해봤는데 오류나서 위 코드로 다시 바꿈
		//User user = userRepository.findByUserName(userName).get();
		
		return user;
	}
	
}

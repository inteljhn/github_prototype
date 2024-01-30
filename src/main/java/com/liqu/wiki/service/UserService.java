package com.liqu.wiki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liqu.wiki.dto.JoinReqDto;
import com.liqu.wiki.entity.RoleType;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.repository.UserRepository;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void dummyTest(User user) {
		throw new IllegalArgumentException("고의 에러 발생");
	}
	
	/**
	 * 사용자 등록 - 회원가입
	 * 
	 * @param joinReqDto
	 */
	@Transactional
	public void regJoinUser(JoinReqDto joinReqDto) {
		String rawPassword = joinReqDto.getPassword(); 		// password 원문
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
	
	/**
	 * OAuth를 통한 회원 등록
	 * 
	 * @param user
	 */
	@Transactional
	public void regOauthJoinUser(User user) {
		String rawPassword = user.getPassword(); 			// password 원문
		String encPassword = encoder.encode(rawPassword); 	// 해쉬된 password
		
		user.setRole(RoleType.USER); 	
		user.setPassword(encPassword); 	

		userRepository.save(user);
	}
	
	// Spring Security를 활용한 로그인을 구현하기 위해 주석처리
//	@Transactional(readOnly = true)
//	public User login(User user) {
//		return userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
//	}


	@Transactional
	public void updUser(User user) {
		// 데이터 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화 시키고, 영속화된 User 오브젝트를 수정
		// Select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 Update
		
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
		}); // User 영속화
		
		// Validate 체크 => oauth 필드에 값이 없을때만 수정 가능
		if(persistance.getOauth() == null || persistance.getOauth().equals(persistance)) {
		
			if(user.getPassword() != null && !user.getPassword().trim().isEmpty()){
				String rawPassword = user.getPassword(); 			// password 원문
				String encPassword = encoder.encode(rawPassword); 	// 해쉬된 password
				
				persistance.setPassword(encPassword);
			}
			
			persistance.setEmail(user.getEmail());
		}
		
		// 회원수정 메소드 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 수행
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌
	}

	@Transactional(readOnly = true)
	public User getUser(String userName) {
		
		User user = userRepository.findByUserName(userName).orElseGet(()->{
			return new User();
		});
		
		return user;
	}
}
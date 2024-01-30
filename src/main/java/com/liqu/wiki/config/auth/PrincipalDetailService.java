package com.liqu.wiki.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.liqu.wiki.entity.User;
import com.liqu.wiki.repository.UserRepository;

// UserDetailsService : 스프링 시큐리티에서 AuthenticationManager의 사용자 인증 요청을 받아서 처리해주는 인터페이스
// 시큐리티 설정(SecurityFilterChain)에서 loginProcessingUrl 내부에 설정된 URL 요청이 들어오면
// 자동으로 IoC되어있는 UserDetailsService 구현체의 loadUserByUsername() 함수가 실행됨
@Service
public class PrincipalDetailService implements UserDetailsService{
	private static final Logger log = LoggerFactory.getLogger(PrincipalDetailService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	// Spring Security가 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	// password 부분 처리는 스프링이 알아서 함.
	// username이 DB에 있는지만 확인해 주면 됨.
	// @Override된 아래 메소드에 username이 유효한지를 확인하는 코드를 작성하면 로그인 시도시 처리됨.
	// 아래 메소드를 @Override 하여 구현하지 않으면 Spring Security의 기본 로그인만 사용할 수 있음 
	// (user / 서버 기동시 나오는 password) 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("PrincipalDetailService.loadUserByUsername() Run..");
		
		User principal = userRepository.findByUserName(username).orElseThrow(()->{
			return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
		});
		
		// 아래 return이 실행될 때 시큐리티의 세션에 유저 정보가 저장됨.
		// Session - SecurityContextHolder - SecurityContext - Authentication - UserDetails
		return new PrincipalDetail(principal); 	
	}

}

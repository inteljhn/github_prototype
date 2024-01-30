package com.liqu.wiki.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import com.liqu.wiki.config.auth.PrincipalDetailService;
import com.liqu.wiki.config.auth.PrincipalOauth2UserService;

import jakarta.servlet.DispatcherType;

/*
 * 프로젝트의 Spring Security 설정을 위한 클래스
 * 
 *  Spring Security 버전이 올라가면서 
 *  각종 강좌나 블로그에서 쉽게 찾아볼 수 있는 WebSecurityConfigurerAdapter를 상속받아 구현하는 방법을 사용할 수 없게 되어
 *  (기타 클래스나 메소드도 사용하지 못하게 되거나 deprecated 됨) 
 *  Spring Security 6.2.1 기준 최신 사양으로 구현함.
 */
@Configuration 			// IoC
@EnableWebSecurity 		// 시큐리티 필터 추가(request가 컨트롤러로 넘어가기 전에 필터링) 또한 해당 필터의 세부 설정을 이 클래스에서 하겠다.
@EnableMethodSecurity 	// 메서드에 권한 처리 어노테이션을 붙일 수 있게할지 말지에 관한 설정을 담당.
public class SecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private PrincipalDetailService principalDetailService; 

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	/*
	@Test
	public void enc() {
		String encPwd = new BCryptPasswordEncoder().encode("1234");
		System.out.println("encPwd ??? : " + encPwd);
	}
	*/
	
	/**
	 * 암호화 @Bean
	 * 
	 * @return
	 */
	@Bean 
	BCryptPasswordEncoder encoder() {
		// BCryptPasswordEncoder : Spring Seurity에서 제공하는 비밀번호를 암호화하는 데 사용할 수 있는 메서드를 가진 클래스
		return new BCryptPasswordEncoder();
	}
	
	/**
     * AuthenticationManager @Bean
     * 
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /**
     * DelegatingSecurityContextRepository @Bean
     * 
     * 강제 로그인 처리를 위해 추가한 메서드.
     * @Bean 등록하여 SecurityContextRepository 객체를 어디서든 @Autowired 하여 사용할 수 있도록 함.
     * (UserController에서 로그인 처리간 SecurityContextRepository를 사용)
     * 
     * @return
     */
    @Bean
	public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
	    return new DelegatingSecurityContextRepository(
	            new RequestAttributeSecurityContextRepository(),
	            new HttpSessionSecurityContextRepository()
	    );
	}
	
	/**
	 * SecurityFilterChain 등록
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
    @Bean 	// IoC
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	// 스프링 시큐리티 6.0부터 forward 방식 페이지 이동에도 default로 인증이 걸리도록 변경되어 설정 생략시 무한 redirect 발생.
    	// 따라서 사용자의 request가 아니라 서버 내부의 자체적인 요청, 즉 DispatcherType이 FORWARD인 경우는 모두 인가.
    	// .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
    	
    	// Spring Security는 기본적으로 CSRF 토큰을 검증하여 인가를 막아줌. 테스트간 csrf 비활성화.
    	
		// 인증되지 않은 사용자들이 출입할 수 있는 경로를 /auth/** 로 허용
		// 요청 URL이 / 이면 index.jsp 허용
		// static 이하에 있는 /js/**, /css/**, /image/** 허용
    	
        http
        	.csrf().disable() 	// csrf 토큰 비활성화
        	.cors().disable()
            .authorizeHttpRequests(request -> request
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				//.requestMatchers("/status", "/images/**", "/view/join", "/auth/join").permitAll()
				.requestMatchers("/", "/auth/**", "/js/**", "/css/**", "/images/**", "/dummy/**", "/favicon.ico", "/test/**").permitAll()
				.anyRequest().authenticated()
            )
            .formLogin(login -> login
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") 	// Spring Security가 해당 주소로 요청하는 로그인을 가로채서 대신 로그인 해준다.
				.usernameParameter("userName")
				.passwordParameter("password")
				.defaultSuccessUrl("/", true) 				// 로그인(인증)이 완료되면 돌아갈 URL
				.failureUrl("/") 							// 로그인 실패시 돌아갈 URL
				.permitAll()
            )
            .logout(withDefaults()) // import static org.springframework.security.config.Customizer.withDefaults; 하지 않으면 오류 발생. TODO - 추가 스터디 필요
            
            // 구글 OAuth 로그인을 위한 추가 설정
            //.oauth2Login().loginPage("/auth/loginForm")
            //.userInfoEndpoint().userService(principalOauth2UserService) 	// OAuth 로그인 이후 후처리
           ;
    	
        return http.build();
    }
}
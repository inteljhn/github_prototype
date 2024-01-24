package com.liqu.wiki.config;

import static org.springframework.security.config.Customizer.withDefaults;

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
 *  - 동영상 강의
 *  - 댓글 수정 버전
 *  - 최신 버전
 *  의 구현 방법이 각각 달라져서 결과적으로 최신 버전으로 구현해야 할 필요가 생겼음
 *  참고로 동영상 댓글에 누가 대충 된다고 써놓은 코드도 그대로 따라해봤는데 역시 안됨
 *  (클래스나 메소드를 아예 사용하지 못하게 되거나 deprecated 됨)
 *
 *  
 *  인증되지 않은 사용자들이 출입할 수 있는 경로를 /auth/** 로 허용
 *  그냥 주소가 / 이면 index.jsp 허용
 *  static 이하에 있는 /js/**, /css/**, /image/** 허용
 */


// 2024-01-14. planthoon. 메타코딩 강좌에서는 WebSecurityConfigurerAdapter를 상속받아 구현하는 것으로 나왔으나 최신 버전에서는 해당 클래스를 사용할 수 없음
// 1. 동영상 강의 버전
/*
@Configuration 										// IoC
@EnableWebSecurity 									// 시큐리티 필터 추가 = 스프링 시큐리티가 활성화 되어있는데.. 어떤 설정을 이 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true) 	// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻. 
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests() 				// 요청이 들어오면
				.antMathers("/auth/**") 		// 이러한 url 패턴일 때는 (문자열을 여러개 넣는 방법으로도 사용 가능. ex : "/auth/loginForm","/auth/joinForm")
				.permitAll() 					// 모두 허락
				.anyRequest() 					// 위 조건에 해당하지 않는 나머지 모든 요청은
				.authenticated()				// 인증이 필요하다
			.and()
				.formLogin() 					 
				.loginPage("/auth/loginForm") 	// 인증이 필요할 때 넘어갈 페이지 URL 지정
		; 		
	}
}
*/
	
// 2. 동영상 강좌에 댓글로 추가해준 버전
// 2024-01-14. planthoon.
// 메타코딩 강좌에 2023년도 버전으로 아래와 같이 올라왔으나 아래 내용도 deprecated 
/*
@Configuration // IoC
public class SecurityConfig {

	@Bean
	BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	// authorizeRequests() 메소드가 deprecated
	// antMatchers() 메소드가 사라짐
	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/auth/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/auth/signin");
		return http.build();
	}

}
*/
	

	
// 3. 자체 구현 버전
@Configuration 			// IoC
@EnableWebSecurity 		// 시큐리티 필터 추가(request가 컨트롤러로 넘어가기 전에 필터링) 또한 해당 필터의 세부 설정을 이 클래스에서 하겠다.
@EnableMethodSecurity 	// 함수에 권한 처리 애노테이션을 붙일 수 있게할지 말지에 관한 설정을 담당.
public class SecurityConfig {
	
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
	 * 암호화
	 * 
	 * @return
	 */
	@Bean // IoC. 즉, 해당 @Bean 메소드가 return하는 BCryptPasswordEncoder 객체를 @Autowired 하여 어디서든 사용할 수 있다는 뜻.
	BCryptPasswordEncoder encode() {
		// BCryptPasswordEncoder : 스프링 시큐리티(Spring Seurity) 프레임워크에서 제공하는 클래스 중 하나로 비밀번호를 암호화하는 데 사용할 수 있는 메서드를 가진 클래스
		return new BCryptPasswordEncoder();
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
    	
    	
    	// 메타코딩 강좌 그대로 따라했을 때 무한 리다이렉트가 발생하는 이유가..
    	// 스프링 시큐리티 6.0부터 forward 방식 페이지 이동에도 default로 인증이 걸리도록 변경되었기 때문..
    	// 따라서 사용자의 request가 아니라 서버 내부의 자체적인 요청, 즉 DispatcherType이 FORWARD인 경우는 모두 인가
    	// .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
    	
    	// Spring Security는 기본적으로 CSRF 토큰을 검증하여 인가를 막아줌
    	// 2024-01-17. 메타코딩 51강 까지는 일단 csrf().disable() 하여 csrf 체크를 비활성화 하고 진행
    	
    	
    	
        http
        	.csrf().disable() 	// csrf 토큰 비활성화 (테스트시에는 걸어두는게 좋음..? 일단 이거 없으니까 회원가입 실패)
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
            
            // OAuth 연동 이후 강제 로그인 세션 유지를 위해 추가한 코드
         	// 출처 : https://velog.io/@goniieee/Spring-Security%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9D%B8%EC%A6%9D%EB%90%9C-%EC%82%AC%EC%9A%A9%EC%9E%90%EB%A5%BC-%EA%B8%B0%EC%96%B5%ED%95%A0%EA%B9%8C
            // 아래 내용 없어도 세션 유지되는 것을 확인하여 일단 주석처리
            /*
            .securityContext((securityContext) -> {
                securityContext.securityContextRepository(delegatingSecurityContextRepository());
                securityContext.requireExplicitSave(true);
            })
            */
            
            
            .logout(withDefaults()) // import static org.springframework.security.config.Customizer.withDefaults; 하지 않으면 오류 발생. TODO - 추가 스터디 필요
            
            
            
            
            // 구글 OAuth 로그인을 위한 추가 설정
            //.oauth2Login().loginPage("/auth/loginForm")
            //.userInfoEndpoint().userService(principalOauth2UserService) 	// OAuth 로그인 이후 후처리
           ;
        
            
        
        

    	// formLogin()
    	// Spring Security는 로그인 요청을 지켜보다가 userName 값과 password를 가로챈다.
    	// 그 이후 내부적으로 로그인을 진행시키고
    	// 로그인 진행이 완료되면 Spring Security 전용 세션으로 user 정보를 등록(IoC)한다.
    	// 그 이후 필요할 떄마다 DI로 유저 정보를 제공한다.
    	// 유저 정보의 타입은 Spring Security에서 제공하는 UserDetails 타입
    	// 또한 password 파라미터가 해쉬 암호화 되어있지 않으면 로그인이 불가능하도록 Spring Security에서 막아준다.
    	
        return http.build();
    }
    
    /**
     * AuthenticationManager를 싱글톤으로 가져오기 위한 메소드 선언
     * 
     * 메타코딩 강좌 61강에서 
     * 회원정보 수정 이후 세션에 있는 데이터를 수정하기 위한 방법으로 제안되었음.
     * 강좌는 Spring Security 구버전으로 진행되어 상속받은 WebSecurityConfigurerAdapter의 authenticationManagerBean()을 선언하는 방법으로 진행되었으나
     * Spring Security 최신 버전에서 WebSecurityConfigurerAdapter를 사용하지 않도록 되어, 댓글 힌트를 참고하여 아래와 같이 구현
     * 
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    // 강제 로그인 처리를 위해 추가한 코드
 	// https://velog.io/@goniieee/Spring-Security%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9D%B8%EC%A6%9D%EB%90%9C-%EC%82%AC%EC%9A%A9%EC%9E%90%EB%A5%BC-%EA%B8%B0%EC%96%B5%ED%95%A0%EA%B9%8C
    @Bean
	public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
	    return new DelegatingSecurityContextRepository(
	            new RequestAttributeSecurityContextRepository(),
	            new HttpSessionSecurityContextRepository()
	    );
	}
    
    // Spring Security가 대신 로그인을 해주는데 password를 가로채는데
    // 해당 password가 어떤 방식으로 해쉬되어 있는지 알아야
    // DB에 저장된 password와 같은 해쉬로 암호화해서 비교할 수 있음
    // 2024-01-17. planthoon.
    // 스프링부트 강좌 블로그 프로젝트 52강에서 configure를 @Override 하여 구현하는데
    // Spring Security 최신버전에서 WebSecurityConfigurerAdapter를 상속받지 못하기 때문에 @Override 할 수 없으므로 별도 구현 필요
    
    // 2024-01-17. planthoon. 아래 내용 없어도 패스워드 비교 잘 해서 잘 인증 됨..
    // TODO - AuthenticationManagerBuilder 관련 추가 스터디 필요.. 아마도 BCryptPasswordEncoder가 Spring Security 기본 인코더라서 그런거 같긴 함..
    /*
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encode());
	}
	*/
    
    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    */
    
    /*
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			
			
			http
					.addFilter(corsConfig.corsFilter())
					.addFilter(new JwtAuthenticationFilter(authenticationManager))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository))
			;
		}
	}
	*/
   
   
   
}
package com.liqu.wiki.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liqu.wiki.config.auth.PrincipalDetail;
import com.liqu.wiki.dto.KakaoProfile;
import com.liqu.wiki.dto.OAuthToken;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 인증되지 않은 사용자들이 출입할 수 있는 경로를 /auth/** 로 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

	// application_secu.yml에 작성한 값을 가져옴
	@Value("${oauth.masterKey}")
	private String COS_KEY;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * 회원가입 화면
	 * @return
	 */
	//@GetMapping("/user/joinForm")
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		// 맨 앞에 슬러시를 넣든 빼든 다 정상 작동함.
		// 2024-01-14. planthoon. Spring Security 적용 이후 맨 앞에 슬러시를 넣으면 오류발생
		return "user/joinForm";
		//return "/user/joinForm";
	}
	
	/**
	 * 로그인 화면
	 * @return
	 */
	//@GetMapping("/user/loginForm")
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	/**
	 * 회원정보 수정 화면
	 * @return
	 */
	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principals) {
		return "user/updateForm";
	}
	
	// return 타입에 @ResponseBody 어노테이션을 붙이면 Data를 리턴해주는 컨트롤러 메소드가 됨
	@GetMapping("/auth/kakao/callback")
	//public @ResponseBody String kakaoCallback(String code) {
	//public String kakaoCallback(String code, @AuthenticationPrincipal PrincipalDetail principal) {
	public String kakaoCallback(String code, HttpServletRequest req, HttpServletResponse res) {
		
		// https://kauth.kakao.com/oauth/token <- 여기로
		// POST 방식 &
		// Content-type: application/x-www-form-urlencoded;charset=utf-8 <- key=value 형식
		// 으로 데이터를 전송하여 카카오쪽으로 보안 토큰을 발급받아야 함
		// grant_type="authorization_code" (하드코딩값 고정)
		// client_id = be65533eb806952c5d4a31b3438f729b (앱 REST API 키 [내 애플리케이션] > [앱 키]에서 확인 가능)
		// redirect_uri = "/auth/kakao/callback" (인가 코드가 리다이렉트된 URI. 즉, this 컨트롤러의 URI)
		// code = this 요청의 파라미터로 들어온 code 
		
		// 1. 개인정보 액세스 토큰 발급받기
		// HTTP 요청을 편리하게 할 수 있는 클래스..
		RestTemplate restTemplate 	= new RestTemplate();
		HttpHeaders httpHeaders 	= new HttpHeaders(); 	// Http Header 오브젝트 생성
		httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// Http Body 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "be65533eb806952c5d4a31b3438f729b");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// Http Header와 Http Body를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);
		
		// Http 요청하기 - Post 방식으로 - 그리고 response 변수에 응답 받음.
		ResponseEntity<String> response = restTemplate.exchange(
			"https://kauth.kakao.com/oauth/token", 
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
		);
		
		// RestTemplate 관련.. 아래 내용들이 유사한 기능을 제공한다고 함.. 
		// 2024-01-21. planthoon. TODO - HttpsURLConnection만 알고 있었는데.. 추가 스터디 요망 
		// HttpsURLConnection
		// Retrofit2 (안드로이드에서 많이 쓴다고 함)
		// OkHttp
		
		// Gson, Json Simple, ObjectMapper 등을 사용하여 json String을 Object로 만들 수 있음
		ObjectMapper objectMapper 	= new ObjectMapper();
		OAuthToken oauthToken 		= null;
		
		try {
			oauthToken 		= objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("oauthToken getAccess_token : " + oauthToken.getAccess_token());
		
		
		// 2. 발급받은 개인정보 액세스 토큰으로 개인정보를 가져오기 
		//https://kapi.kakao.com/v2/user/me
		// GET / POST 둘 다 지원됨
		// Authorization : 액세스 토큰 값
		// Content-type : application/x-www-form-urlencoded;charset=utf-8
		
		RestTemplate restTemplate2 	= new RestTemplate();
		HttpHeaders httpHeaders2 	= new HttpHeaders(); 	// Http Header 오브젝트 생성
		httpHeaders2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		httpHeaders2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// Http Header와 Http Body를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(httpHeaders2);
		
		// Http 요청하기 - Post 방식으로 - 그리고 response 변수에 응답 받음.
		ResponseEntity<String> response2 = restTemplate2.exchange(
			"https://kapi.kakao.com/v2/user/me", 
			HttpMethod.POST,
			kakaoProfileRequest,
			String.class
		);
		
		ObjectMapper objectMapper2 	= new ObjectMapper();
		//objectMapper2.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE); // 네이밍 전략 추가 (Snake -> Camel)
		KakaoProfile kakaoProfile = new KakaoProfile(); 
		
		try {
			kakaoProfile 		= objectMapper.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 여기까지 뽑았으니까 우리 사이트에 필요한 정보를 가져다 활용하면 됨
		System.out.println("kakaoProfile.getId() : " + kakaoProfile.getId());
		System.out.println("kakaoProfile.getId() : " + kakaoProfile.getKakao_account().getEmail());
		
		// 카카오에서 가져온 정보를 활용하여 User - username, password, email만 세팅해서 사용자 정보 등록
		System.out.println("User.userName : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("User.email : " + kakaoProfile.getKakao_account().getEmail());
		
		// UUID -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘 (난수 생성)
		//UUID gabagePassword = UUID.randomUUID();
		//System.out.println("User.password : " + gabagePassword);
		System.out.println("User.password : " + COS_KEY);
		
		User kakaoUser = User.builder()
			.userName(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
			.password(COS_KEY)
			.email(kakaoProfile.getKakao_account().getEmail())
			.oauth("kakao")
			.build();
		
		// 3. 기존 가입자 체크하고, 기존 회원이 아니면 회원가입 처리
		User originUser = userService.회원찾기(kakaoUser.getUserName());
		
		if(originUser == null || originUser.getUserName() == null) {
			System.out.println("기존 회원이 아닙니다. 회원가입을 진행합니다.");
			userService.회원가입(kakaoUser);
		}else {
			System.out.println("기존 회원입니다.");
		}
		
		// 4. 로그인 처리
		// 2024-01-21. planthoon. 아래와 같이 했는데 로그인 세션이 생성되지 않음... 
		// return index 했을때는 생기는데... redirect 하면 사라짐.
		// OAuth Client 라이브러리를 사용하여 다시 구현하면서 별도 스터디 필요할듯
		System.out.println("자동 로그인을 진행합니다.");
		
		// Spring Boot 3.0.5 부터 아래 방법만 가지고 강제 로그인 처리 정상 작동하지 않는다고 함..
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUserName(), COS_KEY));
		//Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("planthoon", "1234"));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 강제 로그인 처리를 위해 추가한 코드
		// 출처 : https://velog.io/@goniieee/Spring-Security%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9D%B8%EC%A6%9D%EB%90%9C-%EC%82%AC%EC%9A%A9%EC%9E%90%EB%A5%BC-%EA%B8%B0%EC%96%B5%ED%95%A0%EA%B9%8C
		//SecurityContextHolder.setContext(securityContext);
		SecurityContextHolder.setContext(SecurityContextHolder.getContext());
		securityContextRepository.saveContext(SecurityContextHolder.getContext(), req, res);
		
		
		
		
		
		
		
		//System.out.println("authentication.getPrincipal.getClass() : " + authentication.getPrincipal().getClass());
		
		//PrincipalDetail pd = (PrincipalDetail)authentication.getPrincipal();
		
		//System.out.println("pd.getUsername() : " + pd.getUsername());
		//System.out.println("authentication.isAuthenticated() : " + authentication.isAuthenticated());
		
		
		//System.out.println("authentication.getDetails : " + authentication.getDetails()); <- null
		
		//System.out.println("authentication.class : " + authentication.getDetails().getClass());
		
		System.out.println("로그인 완료!");
		
		//return "카카오 토큰 요청 완료. 응답 : " + response;
		//return "카카오 토큰 요청 완료. 응답 : " + response.getBody();
		//return "카카오 토큰 요청 완료. 응답 : " + response2.getBody();
		//return "회원가입 완료";
		return "redirect:/";
		//return "forward:/";
		//return "";
		//return "index";
	}
	
	// 강제 로그인 처리를 위해 의존성 주입
	// 출처 : https://velog.io/@goniieee/Spring-Security%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9D%B8%EC%A6%9D%EB%90%9C-%EC%82%AC%EC%9A%A9%EC%9E%90%EB%A5%BC-%EA%B8%B0%EC%96%B5%ED%95%A0%EA%B9%8C
	@Autowired
	private final SecurityContextRepository securityContextRepository;
	
	public UserController(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }
	
	/*
    private void saveContext(String username, HttpServletRequest request, HttpServletResponse response) {
        //Token token = new Token(username);
    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, COS_KEY));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        //context.setAuthentication(token);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }
    */
}

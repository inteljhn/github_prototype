package com.liqu.wiki.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // <- view 매핑을 위한 클래스의 어노테이션
public class TempController {

	@GetMapping("/temp/home")
	public String tempHome() {
		
		System.out.println("tempHome Run !!");
		
		// static 파일 리턴 기본경로 : /src/main/resources/static
		// 위 경로는 static(정적) 파일들을 관리하는 기본 경로이기 때문에 당연히 .jsp 등을 넣으면 정상적으로 작동하지 않음. 

		// 리턴 String에는 "/" + "파일명"을 넣어야 함. 파일명만 넣어서는 작동하지 않음
		//return "home.html"; <- 작동하지 않음
		
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/basilicata-1978.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		
		System.out.println("tempJsp Run !!!");
		
		// application.yml 파일에 설정을 아래와 같이 한 다음에는...
		// prefix: /WEB-INF/views/
		// suffix: .jsp
		// 메소드 리턴에 들어있는 String 앞에 prefix, 뒤에 suffix를 자동으로 붙여줌
		
		// ex) return "test";
		// 풀 String : /WEB-INF/views/test.jsp
		// 완전 풀 String : /프로젝트/src/main/webapp/WEB-INF/views/test.jsp
		
		// 위와같이 prefix, suffix를 붙여서 
		// 톰캣이 jsp를 인식+컴파일 할 수 있는 경로에 jsp를 올려야 jsp 사용 가능.
		
		// prefix, suffix 설정을 넣고나서는 
		// 현재 메소드 위에 작성된 tempHome(), tempImg() 메소드는 정상적으로 작동하지 않음.
		// 앞에 아파치 http 서버를 두어서 사전에 처리하게 하던지 해야할듯... 
		
		
		//return "/test.jsp"; 	// <- 위와같이 설정되었기 때문에 이것은 작동하지 않음
		//return "/test";  		// <- 이것은 작동
		return "test";  		// <- Best
	}
}

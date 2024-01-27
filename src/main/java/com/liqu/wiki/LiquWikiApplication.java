package com.liqu.wiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Starter Project 생성시 자동으로 생성되는 클래스.
 * main() 메소드 실행시 IoC를 위한 컴포넌트 스캔 등 스프링 부트 초기화 작업이 실행된다.  
 */
@SpringBootApplication
public class LiquWikiApplication {

	// application 설정파일 추가
	// application.yml 외 나머지는 공개 레파지토리에 올라가지 않도록 주의하여 관리.
	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/application_secu.yml");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LiquWikiApplication.class, args);
	}
}

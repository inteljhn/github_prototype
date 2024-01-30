package com.liqu.wiki.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.vo.LiquInfo;

// 클라이언트의 요청에 대해서 응답을 돌려주는 클래스
// @RestController -> 데이터 응답
@RestController
public class HttpController {
	
	@PostMapping("/http/lombok")
	public String lombokTest() {
		
		LiquInfo liqInfo = new LiquInfo();
		LiquInfo.builder();
		
		return "";
	}

	@GetMapping("/http/get")
	public String getTest(LiquInfo liqInfo){
		liqInfo.setLiqId("조작 !");
		
		return "get 요청 !! : " + liqInfo.getLiqId() + " || " + liqInfo.getNameKor() + " || " + liqInfo.getNameEng();
	}
	
	@PostMapping("/http/post")
	public String postTest(@RequestBody LiquInfo liqInfo) { 
		System.out.println("postTest RUN !!");
		
		return "post 요청 !! : " + liqInfo.getLiqId() + " || " + liqInfo.getNameKor() + " || " + liqInfo.getNameEng();
	}
	
	@PutMapping("/http/put")
	public String putTest(@RequestBody LiquInfo liqInfo) {
		return "put 요청 !! : " + liqInfo.getLiqId() + " || " + liqInfo.getNameKor() + " || " + liqInfo.getNameEng();
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}

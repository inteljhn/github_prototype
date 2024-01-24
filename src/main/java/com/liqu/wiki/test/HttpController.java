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
	
	private static final String TAG = "HttpController : ";
	
	@PostMapping("/http/lombok")
	public String lombokTest() {
		
		LiquInfo liqInfo = new LiquInfo();
		LiquInfo.builder();
		
		return "";
	}

	@GetMapping("/http/get")
	//public String getTest(@RequestParam String liqId, @RequestParam String nameKor){ // <- 파라미터 하나씩 받아오는 방식
	public String getTest(LiquInfo liqInfo){ 	// <- MessageConverter를 통해 파라미터를 Object에 맞추어 한번에 받아오는 방식
		
		System.out.println(TAG + "getter 11 : " + liqInfo.getLiqId());
		liqInfo.setLiqId("조작 !");
		System.out.println(TAG + "getter 22 : " + liqInfo.getLiqId());
		
		
		
		return "get 요청 !! : " + liqInfo.getLiqId() + " || " + liqInfo.getNameKor() + " || " + liqInfo.getNameEng();
	}
	
	@PostMapping("/http/post")
	//public String postTest(LiquInfo liqInfo) { 				// <- form 데이터(x-www-form-urlencoded) 받아오는 방식
	//public String postTest(@RequestBody String reqText) { 	// <- Body에 raw text 받아오는 방식
	public String postTest(@RequestBody LiquInfo liqInfo) { 	// <- Body에 JSON 받아오는 방식 
		System.out.println("postTest RUN !!");
		
		return "post 요청 !! : " + liqInfo.getLiqId() + " || " + liqInfo.getNameKor() + " || " + liqInfo.getNameEng();
		//return "post 요청 문자열 : " + reqText;
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

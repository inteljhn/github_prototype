package com.liqu.wiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.liqu.wiki.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	// 컨트롤러 메소드에서 세션을 찾기 위해서는 매개변수에 @AuthenticationPrincipal 타입 변수를 추가
	@GetMapping({"", "/"})
	//public String index() {
	//public String index(@AuthenticationPrincipal PrincipalDetail principal) {
	public String index(
		Model model,
		@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		
		/*
		if(principal != null) {
			System.out.println("Login ID : " + principal.getUsername());
		}else {
			System.out.println("아직 로그인하지 않음..");
		}
		*/
		
		// @Controller의 메소드가 return 될때 viewResolver 작동.
		// model에 담은 객체는 view까지 전달됨
		model.addAttribute("boards", boardService.글목록(pageable));
		
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		
		return "board/updateForm";
	}
	
	@GetMapping("/board/list")
	public String boardList(
		Model model,
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	){
		model.addAttribute("boards", boardService.글목록(pageable));
		
		return "board/list";
	}
}

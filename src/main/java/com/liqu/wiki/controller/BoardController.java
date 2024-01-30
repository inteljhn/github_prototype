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

	/**
	 * 인덱스 페이지 호출
	 * 2024-01-31. planthoon. TODO - 인덱스 페이지 멀티 목록 구현
	 * 
	 * @param model
	 * @param pageable
	 * @return
	 */
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		
		/*
		if(principal != null) {
			System.out.println("Login ID : " + principal.getUsername());
		}else {
			System.out.println("아직 로그인하지 않음..");
		}
		*/
		
		// @Controller의 메소드가 return 될때 viewResolver 작동.
		// model에 담은 객체는 view까지 전달됨
		model.addAttribute("boards", boardService.getListAllBoard(pageable));
		
		return "index";
	}
	
	/**
	 * 글 작성 화면 호출 
	 * @return
	 */
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	/**
	 * 글 세부정보 화면 호출
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.getDetailBoard(id));
		
		return "board/detail";
	}
	
	/**
	 * 글 수정 화면 호출
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.getDetailBoard(id));
		
		return "board/updateForm";
	}
	
	/**
	 * 글 목록 화면 호출
	 * 
	 * @param model
	 * @param pageable
	 * @return
	 */
	@GetMapping("/board/list")
	public String boardList(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		model.addAttribute("boards", boardService.getListAllBoard(pageable));
		
		return "board/list";
	}
}

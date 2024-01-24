package com.liqu.wiki.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.config.auth.PrincipalDetail;
import com.liqu.wiki.dto.ReplySaveRequestDto;
import com.liqu.wiki.dto.ResponseDto;
import com.liqu.wiki.entity.Board;
import com.liqu.wiki.service.BoardService;

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService; 
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(
		@RequestBody Board board,
		@AuthenticationPrincipal PrincipalDetail principal
	) {
		System.out.println("BoardApiController.save() run !!");
		boardService.글쓰기(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		System.out.println("BoardApiController.deleteById() run !!");
		
		//model.addAttribute("board", boardService.글상세보기(id));
		boardService.글삭제(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(
		@PathVariable int id,
		@RequestBody Board board
		//@AuthenticationPrincipal PrincipalDetail principal
	) {
		System.out.println("BoardApiController.update() run !!");
		boardService.글수정(id, board);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	// 데이터 받을 때 Entity를 그냥 받는게 아니고.. 컨트롤러에서 dto를 만들어서 받는게 좋다.
	// 강의 진행간 dto를 사용하지 않은 이유는.. 프로젝트가 작기 때문에..
	@PostMapping("/api/board/{boardId}/reply")
	/*
	public ResponseDto<Integer> replySave(
		@PathVariable int boardId,
		@RequestBody Reply reply,
		@AuthenticationPrincipal PrincipalDetail principal
	) {
	*/
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){
		//boardService.댓글쓰기(principal.getUser(), boardId, reply);
		boardService.댓글쓰기(replySaveRequestDto);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}

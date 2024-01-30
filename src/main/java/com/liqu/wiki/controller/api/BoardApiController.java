package com.liqu.wiki.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(BoardApiController.class);

	@Autowired
	private BoardService boardService; 
	
	/**
	 * 글 등록
	 * 
	 * @param board
	 * @param principal
	 * @return
	 */
	@PostMapping("/api/board")
	public ResponseDto<Integer> regBoard(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		log.debug("BoardApiController.regBoard() run..");
		
		boardService.regBoard(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	/**
	 * 글 삭제
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> delBoardById(@PathVariable int id) {
		log.debug("BoardApiController.delBoardById() run..");
		
		boardService.delBoardById(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> updBoard(@PathVariable int id, @RequestBody Board board) {
		log.debug("BoardApiController.updBoard() run..");
		
		boardService.updBoard(id, board);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	/**
	 * 댓글 등록
	 * 
	 * @param replySaveRequestDto
	 * @return
	 */
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> regReply(@RequestBody ReplySaveRequestDto replySaveRequestDto){
		log.debug("BoardApiController.regReply() run..");
		
		boardService.regReply(replySaveRequestDto);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * @param replyId
	 * @return
	 */
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> delReply(@PathVariable int replyId){
		log.debug("BoardApiController.delReply() run..");
		
		boardService.delReply(replyId);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}

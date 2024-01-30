package com.liqu.wiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liqu.wiki.dto.ReplySaveRequestDto;
import com.liqu.wiki.entity.Board;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.repository.BoardRepository;
import com.liqu.wiki.repository.ReplyRepository;
import com.liqu.wiki.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository; 
	
	@Autowired
	private ReplyRepository replyRepository;
	
	//@Autowired
	//private UserRepository userRepository;
	
	/**
	 * 게시글 등록
	 * 
	 * @param board
	 * @param user
	 */
	@Transactional
	public void regBoard(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		
		boardRepository.save(board);
	}
	
	/**
	 * 전체 게시글 가져오기 
	 * 
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	//public List<Board> getListAllBoard() {
	public Page<Board> getListAllBoard(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	/**
	 * 게시글 상세정보 가져오기
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Board getDetailBoard(int id){
		
		return 
			boardRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("해당 글 ID를 찾을 수 없습니다.");
			});
	}
	
	/**
	 * 게시글 삭제
	 * 
	 * @param id
	 */
	@Transactional
	public void delBoardById(int id) {
		boardRepository.deleteById(id);
	}
	
	/**
	 * 게시글 수정 저장
	 * 
	 * @param id
	 * @param reqBoard
	 */
	@Transactional
	public void updBoard(int id, Board reqBoard) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 글 ID를 찾을 수 없습니다.");
		}); // Board 영속화
		
		// 영속화된 객체에 데이터를 setXxx() 해주고, 해당 메소드 종료시 (Service 종료될 때) 트랜잭션이 종료됨
		// 트랜잭션이 종료될 때 더티체킹 발생하여 자동 업데이트가 됨. db flush
		board.setTitle(reqBoard.getTitle());
		board.setContent(reqBoard.getContent());
		
		//boardRepository.save(board);
	}
	

	/**
	 * 댓글 등록
	 * 
	 * @param replySaveRequestDto
	 */
	@Transactional
	//public void regReply(User user, int boardId, Reply reqReply) {
	public void regReply(ReplySaveRequestDto replySaveRequestDto){
		// 2024-01-22. planthoon. 
		// TODO - 보안적으로 문제가 될 수 있으므로 사용자 정보는 클라이언트에서 받지 말고 서버에서 처리하도록 수정필요
		
		//board.setCount(0);
		//board.setUser(user);
		
		//boardRepository.save(board);
		
		/*
		reply.setUser(principal.getUser());
		reply.setBoard(null);
		
		boardService.글상세보기(boardId);
		*/
		
		/*
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 해당 사용자 ID를 찾을 수 없습니다.");
		});
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 해당 글 ID를 찾을 수 없습니다.");
		});
		
		Reply reply = Reply.builder().
			content(replySaveRequestDto.getContent()).
			user(user).
			board(board).
		build();
		
		
		//reqReply.setUser(user);
		//reqReply.setBoard(board);
		
		
		replyRepository.save(reply);
		*/
		
		replyRepository.insertReply(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	/**
	 * 댓글 삭제
	 * 
	 * @param replyId
	 */
	@Transactional
	public void delReply(int replyId){
		replyRepository.deleteById(replyId);
	}
}

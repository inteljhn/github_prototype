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
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	//public List<Board> 글목록() {
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id){
		
		return 
			boardRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("해당 글 ID를 찾을 수 없습니다.");
			});
	}
	
	@Transactional
	public void 글삭제(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정(int id, Board reqBoard) {
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 글 ID를 찾을 수 없습니다.");
		}); // Board 영속화
		
		// 영속화된 객체에 데이터를 setXxx() 해주고, 해당 메소드 종료시 (Service 종료될 때) 트랜잭션이 종료됨
		// 트랜잭션이 종료될 때 더티체킹 발생하여 자동 업데이트가 됨. db flush
		board.setTitle(reqBoard.getTitle());
		board.setContent(reqBoard.getContent());
		
		//boardRepository.save(board);
	}
	
	// 2024-01-22. planthoon. 
	// TODO - 강좌에서는 user 정보까지 dto로 넘겨받아서 처리하는 방법을 알려주었으나, 보안적으로 문제가 될 수 있으므로 사용자 정보는 서버에서 처리하도록 수정필요
	@Transactional
	//public void 댓글쓰기(User user, int boardId, Reply reqReply) {
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto){
		//board.setCount(0);
		//board.setUser(user);
		
		//boardRepository.save(board);
		
		/*
		reply.setUser(principal.getUser());
		reply.setBoard(null);
		
		boardService.글상세보기(boardId);
		*/
		
		/*
		System.out.println("replySaveRequestDto.getUserId() : " + replySaveRequestDto.getUserId());
		
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 해당 사용자 ID를 찾을 수 없습니다.");
		}); // Board 영속화
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 해당 글 ID를 찾을 수 없습니다.");
		}); // Board 영속화
		
		Reply reply = Reply.builder().
			content(replySaveRequestDto.getContent()).
			user(user).
			board(board).
		build();
		
		
		//reqReply.setUser(user);
		//reqReply.setBoard(board);
		
		
		replyRepository.save(reply);
		*/
		
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId){
		replyRepository.deleteById(replyId);
	}
}

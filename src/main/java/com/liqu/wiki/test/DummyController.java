package com.liqu.wiki.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.liqu.wiki.entity.Board;
import com.liqu.wiki.entity.Reply;
import com.liqu.wiki.entity.RoleType;
import com.liqu.wiki.entity.User;
import com.liqu.wiki.repository.BoardRepository;
import com.liqu.wiki.repository.ReplyRepository;
import com.liqu.wiki.repository.UserRepository;
import com.liqu.wiki.service.UserService;

import jakarta.transaction.Transactional;

// @RestController : html 파일이 아니라 data를 리턴해주는 Controller
@RestController
public class DummyController {

	// @Autowired : Ioc로 메모리에 올라가있는 객체를 DI하는 어노테이션
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private UserService userService;
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		// 전체 데이터 가져오기
		//userService.dummyTest(null);
		return userRepository.findAll();
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id){
		
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "존재하지 않는 ID";
		}
		
		return "삭제되었습니다. id : " + id;
	}
	
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("데이터 수정 실패. 존재하지 않는 id... : " + id);
		});
		
		user.setPassword(reqUser.getPassword());
		user.setEmail(reqUser.getEmail());
		
		return user;
	}
	
	// 아래와같이 코딩하면 Get 방식으로 'page' 파라미터를 인지하여 해당 페이지를 조회하도록 함.
	// ex : http://localhost:8000/blog/dummy/user?page=0
	// page 파리터가 없는 경우 기본적으로 0페이지를 조회하도록 되어있음
	@GetMapping("/dummy/user")
	//public Page<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		// 아래와 같이 하면 페이징 관련된 추가정보가 response에 포함됨
		// pageNumber, pageSize, totalElements, totalPages 등등..
		//Page<User> users = userRepository.findAll(pageable);  
		
		// 아래와 같이 하면 순수 목록 데이터만 returns 
		List<User> users = userRepository.findAll(pageable).getContent();
		
		return users;
	}
	
	// {id} = 주소로 들어오는 파라미터 매핑
	// ex : http://localhost:8000/blog/dummy/user/3 <- 맨 끝에 3이 파라미터
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		/*
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : " + id);
			}
		});
		*/
		
		// 위 로직을 람다식으로 아래와 같이 작성할 수 있음
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 존재하지 않습니다... id : " + id);
		});
		
		return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		
		return "회원가입 완료";
	}
	
	@GetMapping("/test/board/{id}")
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get();
	}
	
	@GetMapping("/test/reply/{id}")
	public Reply getReply(@PathVariable int id) {
		return replyRepository.findById(id).get();
	}
	
	@GetMapping("/test/reply")
	public List<Reply> getReply() {
		return replyRepository.findAll();
	}
}

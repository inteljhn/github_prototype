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
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		// 전체 데이터 가져오기
		return userRepository.findAll();
	}
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id){
		
		// 2024-01-08. planthoon
		// 강좌에서는 아래와 같이 예외처리를 해줬는데
		// 하이버네이트에서 deleteById 할 때 데이터가 존재하지 않아도 에러가 발생하지 않도록 메소드의 정책이 바뀐것으로 보임
		// 메소드 설명에 If the entity is not found in the persistence store it is silently ignored. 라는 문구가 추가되었음.
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "존재하지 않는 ID";
		}
		
		
		
		return "삭제되었습니다. id : " + id;
	}
	
	
	@Transactional // 함수 종료시에 자동 commit이 됨
	// @RequestBody : request body에 포함된 json 메시지를 MessageConverter가 Jackson 라이브러리를 사용하여자동으로 자바 object로 변환해줌
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User reqUser) {
		System.out.println("id : " + id);		
		System.out.println("password : " + reqUser.getPassword());		
		System.out.println("email : " + reqUser.getEmail());
		
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("데이터 수정 실패. 존재하지 않는 id... : " + id);
		});
		
		user.setPassword(reqUser.getPassword());
		user.setEmail(reqUser.getEmail());
		

		// save()
		// id(PK)를 전달하지 않으면 insert
		// id(PK)를 전달 한 경우
		// 해당 데이터가 있으면 update, 없으면 insert
		
		// save() 메소드는 일반적으로 insert 할 때 사용함.. 
		// request에 해당 테이블의 모든 컬럼이 존재하지 않는 경우 null로 바뀌는 문제가 있을 수 있음
		// 그래서 위처럼 DB에서 기존 정보를 가져오도록 선처리가 필요..
		
		//userRepository.save(user);
		
		// @Transactional 어노테이션을 걸어주면
		// '더티체킹'이 작동하여 save() 메소드를 주석처리 해도 데이터가 update 된다.
		// 더티체킹 : 객체의 값이 변경된 것을 감지하여 별도의 저장 메소드 호출이 없어도 commit 작동시 데이터를 update 해줌 
		
		// return에 object를 담으면 json 형태로 변환되어 response 나감
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
		
		// 메타코딩 userRepository.findById() 설명...
		// user 4를 select 했는데 만약에 데이터가 없으면 user가 null이 될 것
		// 그럼 return null이 되므로 프로그램에 문제가 있을 수 있음
		// Optional 클래스로 User 객체를 감싸서 가져올테니 null인지 아닌지 개발자가 판단해서 return
		
		User user;
		// get() : 자동으로 형변환 해줌
		// user = userRepository.findById(id).get();
		
		// orElseGet() : 값이 없으면 아래 로직에 따라 객체를 만들어서 return
		/*
		user = userRepository.findById(id).orElseGet(new Supplier<User>() {
			@Override
			public User get() {
				// TODO Auto-generated method stub
				return new User();
			}
		});
		*/
		
		// orElseThrow() : 데이터를 가져오지 못했을 때 Exception 처리하는 방법.
		/*
		user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 존재하지 않습니다. id : " + id);
			}
		});
		*/
		
		// 위 로직을 람다식으로 아래와 같이 작성할 수 있음
		user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 유저는 존재하지 않습니다... id : " + id);
		});
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트.
		// 변환 필요(웹브라우저가 이해할 수 있는 데이터로) -> json (Gson 라이브러리)
		// 스프링부트 = MessageConverter가 응답시에 자동으로 작동함.
		// 만약에 자바오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		
		return user;
	}
	
	// @RequestParam("username") 이라고 하지 않아도 변수명만으로 
	// http의 body에 username, password, email 파라미터를 받아올 수 있음 
	@PostMapping("/dummy/join")
	//public String join(String username, String password, String email) {
	public String join(User user) {
		
//		System.out.println("username : " + username);		
//		System.out.println("password : " + password);		
//		System.out.println("email : " + email);		
		
		System.out.println("user.id : " + user.getId());
		System.out.println("user.username : " + user.getUserName());		
		System.out.println("user.password : " + user.getPassword());		
		System.out.println("user.email : " + user.getEmail());
		System.out.println("user.role : " + user.getRole());
		System.out.println("user.createDate : " + user.getCreateDate());
		
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

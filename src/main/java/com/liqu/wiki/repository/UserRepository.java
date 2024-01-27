package com.liqu.wiki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liqu.wiki.entity.User;

/**
 * 
 * JPA Naming 전략
 * findByXxxAndXxx 양식으로 아래와 같이 메소드 이름을 작성한 다음 실행하면 아래 쿼리가 실행됨
 * findByUserNameAndPassword()
 * Select * from user where userName = ?1 AND password = ?2;
 * findBy + 컬럼명 + 컬럼명. 파스칼 형식
 * 
 */

// JpaRepository 두번째 인자는 User의 PK 타입
// JpaRepository를 상속 받음으로서 기본적인 CRUD 함수를 사용할 수 있음. DAO 클래스와 유사한 개념.
// 자동으로 bean으로 등록 됨.(Ioc). @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{

	// JPQ Query Method
	// Select * from user WHERE userName = 1?;
	Optional<User> findByUserName(String userName);
	

	// 2024-01-13. planthoon. TODO
	// 아래와 findByUserNameAndPassword(), login() 메소드와 같이 
	// 2가지 이상의 컬럼을 where절에 활용하여 가져온 데이터가 들어간 User 객체는
	// 영속성 컨텍스트에 어떻게 들어가는지 별도 스터디 필요

	// Spring Security를 활용한 로그인 구현 예정으로 주석처리
	//User findByUserNameAndPassword(String userName, String password);
	
	// 아래와 같이 native 쿼리를 개발자가 직접 작성하여 원하는 쿼리를 사용할 수도 있음
//	@Query(value="Select * from user where user_name = ?1 AND password = ?2", nativeQuery = true)
//	User login(String userName, String password);
}

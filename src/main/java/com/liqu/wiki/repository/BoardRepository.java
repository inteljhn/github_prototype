package com.liqu.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.liqu.wiki.entity.Board;

/**
 * 
 * JPA Naming 전략
 * findByXxxAndXxx 양식으로 아래와 같이 메소드 이름을 작성한 다음 실행하면 아래 쿼리가 실행됨
 * findByUserNameAndPassword()
 * Select * from user where userName = ?1 AND password = ?2;
 * findBy + 컬럼명 + 컬럼명. 파스칼 형식
 * 
 */

public interface BoardRepository extends JpaRepository<Board, Integer>{
	

}

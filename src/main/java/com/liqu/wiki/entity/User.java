package com.liqu.wiki.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메타코딩 강좌 그대로 따라서 만든 클래스
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert // insert시 null 값을 가진 필드는 insert 문에서 제외
public class User {
	
	// @Id : Primary Key 설정
	// @GeneratedValue(strategy = GenerationType.IDENTITY) : 프로젝트에 연결된 DB의 넘버링 전략을 따라가는 방식(시퀀스, auto_increment 등)
	// WAS에서 처리하는 내용이 아니라 테이블에 auto_increment 속성을 넣어줌
	@Id 	
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	// 컬럼 옵션에 unique=true 하게되면 해당 컬럼에 unique 제약조건이 걸림
	@Column(nullable = false, length = 100, unique=true)
	private String userName;
	
	@Column(nullable = false, length = 100) // 해쉬(비밀번호 암호화 적용)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@ColumnDefault("'user'")
	//private String role;
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	private String oauth; // kakao, google
	
	// @CreationTimestamp : 데이터 insert 시간 자동 입력. 
	// 테이블에 default 속성을 주는게 아니라 WAS 에서 처리하는 내용임.
	@CreationTimestamp 	
	private Timestamp createDate;
}

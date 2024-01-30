package com.liqu.wiki.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 현재 클래스가 DB에 테이블로 생성되도록 하는 어노테이션. ORM -> Java(다른 언어도 마찬가지) Object를 테이블로 매핑해주는 기술
public class UserInfo extends BaseEntity{

	@Id // Primary Key로 설정하는 어노테이션
	//@GeneratedValue(strategy = GenerationType.TABLE) 	// 채번테이블을 생성해서 값을 자동 채번하는 방식
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라가는 방식(시퀀스, auto_increment 등)
	private int userId;
	
	@Column(nullable = false, length = 30)
	private String username;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;

	// 2024-01-31. planthoon. TODO - enum 전환 검토
	@ColumnDefault("'user'")
	private String role;
}

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
	
	// 메타코딩 참고..
	// 권한 같은 사전에 값이 정해질 수 있는 코드성 필드는 
	// Enum을 쓰는게 좋다고 제안하고 있음
	// admin, user manager ... 등을 enum으로 구현하여 필드의 도메인(데이터의 범위) 설정을 해줄 수 있으므로
	// 오타 방지 등위 이유를 위해 enum으로 구현하는게 좋지 않겠냐고 함.
	@ColumnDefault("'user'")
	private String role;
	
	
}

package com.liqu.wiki.entity;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity 	// 이 클래스가 DB에 테이블로 생성될 클래스다 라는 것이 제일 마지막에 오는 것이 좋다고 함.. 구체적인 정보는 별도 스터디 필요. 
public class Board {

	// @Id : Primary Key 설정
	// @GeneratedValue(strategy = GenerationType.IDENTITY) : 프로젝트에 연결된 DB의 넘버링 전략을 따라가는 방식(시퀀스, auto_increment 등)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	// 섬머노트 라이브러리 사용하여 <html> 태그를 섞어서 디자인 되도록 할 예정
	// Lob : 대용량 데이터
	// 2024-01-18. planthoon. @Lob 기본적으로 tinytext 타입으로 컬럼 생성되어 이미지 파일 저장하기에 좀 작음
	@Lob 
	//@Column(columnDefinition = "TEXT") 		// 64 Kb
	@Column(columnDefinition = "MEDIUMTEXT") 	// 8 Mb
	//@Column(columnDefinition = "MEDIUMBLOB") 	// 8 Mb. 이걸로 해놓고 저장한 다음 데이터 조회하면 아래 오류 발생
	//[org.springframework.dao.DataIntegrityViolationException: Could not extract column [2] from JDBC ResultSet [Data type BLOB cannot be decoded as Clob] [n/a]; SQL [n/a]]
	private String content;
	
	//@ColumnDefault("0")
	private int count; 	// 조회수
	
	// FetchType.EAGER = Board 데이터를 select 할 때 user 정보를 무조건 함께 select 해서 가져옴
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;
	
	// @OneToMany = 1대다 관계를 표현하는 어노테이션
	// mappedBy = 연관관계의 주인이 아니다. FK 컬럼을 생성하지 않는다. 
	// 이런식으로 참조하려는 객체에서는 현재 클래스를  @ManyToOne / @JoinColumn(name="boardId") 으로 가지고 있어서
	// 현재 클래스의 Id를 FK로 생성해둠.
	// 즉, Board의 서브인 Reply에서 boardId라는 FK 컬럼을 만들기 위해 선언한 변수 board의 변수명을 적으면 됨.
	// @OneToMany에서 fetch = FetchType.LAZY 는 별도 설정하지 않아도 default
	// @ManyToOne(fetch = FetchType.EAGER) 와 달리 무조건 데이터를 가져오지 않음
	//@OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
	// Reply 안에 Board가 있고, Board 안에 Reply가 있어서 jackson 작동하여 json으로 return시 무한 참조가 발생함
	// @JsonIgnoreProperties({"board"}) 어노테이션을 붙여서 Board에 대한 json 생성할 때 설정한 ignore 필드들을 생략하게 만들어서 무한 참조 방지
	// cascade = CascadeType.REMOVE : this가 삭제될 때 foreign key로 this의 pk를 가져간 데이터를 함께 삭제
	// cascade = CascadeType.PERSIST : this를 등록할 때 아래 Object 까지 입력하면 함께 DB Insert
	// cascade = CascadeType.ALL : REMOVE와 PERSIST 등 전체 적용
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	//@JsonIgnoreProperties({"board", "user"})
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp 	// 데이터 insert 시간 자동 입력
	private Timestamp createDate;
}

package com.liqu.wiki.entity;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BoardDtl extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라가는 방식(시퀀스, auto_increment 등)
	private int boardId;
	
	@Column(nullable = false, length = 100)
	private String title;

	// 섬머노트 라이브러리 사용하여 <html> 태그를 섞어서 디자인 되도록 할 예정
	// Lob : 대용량 데이터
	@Lob 
	private String content;
	
	/**
	 * 조회수
	 */
	@ColumnDefault("0")
	private int count;
	
	// DB는 오브젝트를 저장할 수 없다. FK를 사용하여 관계 참조.
	// 자바(ORM)은 오브젝트를 저장할 수 있다.
	// 아래 @ManyToOne 어노테이션 사용하면 테이블 생성시 UserInfo 테이블의 userId를 참조하는 FK가 자동 생성되는 것으로 확인
	// 일단 FK 사용하지 않도록 처리 
	//@ManyToOne // Many = Board, User = One.  다대일관계
	//@JoinColumn(name="userId")
	//private UserInfo user;
	@Column(nullable = false)
	private int userId;
	
	// mappedBy = 연관관계의 주인이 아니다. FK 컬럼을 생성하지 않는다.
	//@OneToMany(mappedBy = "board")
	//private List<ReplyDtl> reply;
}

package com.liqu.wiki.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ReplyDtl extends BaseEntity{

	@Id // Primary Key로 설정하는 어노테이션
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라가는 방식(시퀀스, auto_increment 등)
	private int replyId;
	
	@Column(nullable = false, length = 200)
	private String content;
	
//	@ManyToOne
//	@JoinColumn(name="boardId")
//	private BoardDtl board;
	private int boardId;

//	@ManyToOne
//	@JoinColumn(name="boardId")
	private int userId;
	
//	@CreationTimestamp
//	private Timestamp createDate;
}

package com.liqu.wiki.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Entity // 현재 클래스가 DB에 테이블로 생성되도록 하는 어노테이션
public class LiqCateg {
	
	@Id // Primary Key로 설정하는 어노테이션
	//@GeneratedValue(strategy = GenerationType.TABLE) 	// 채번테이블을 생성해서 값을 자동 채번하는 방식
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라가는 방식(시퀀스, auto_increment 등)
	private int categId;
	
	
	@Column(nullable = false, length = 20)
	private String nameKor;
	
	@Column(nullable = false, length = 20)
	private String nameEng;
	
	/*
	@CreationTimestamp 	// 최초 insert 시간 자동 입력
	private Timestamp regDate;
	
	@UpdateTimestamp
	private Timestamp modDate;
	*/
	
	
}

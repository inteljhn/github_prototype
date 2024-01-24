package com.liqu.wiki.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseVO {

	//@Id
	//private String id;
	
	@CreationTimestamp 	// 최초 insert 시간 자동 입력
	private Timestamp regDate;
	
	@Column(nullable = false)
	private int regId;
	
	@UpdateTimestamp
	private Timestamp modDate;
	
	@Column(nullable = false)
	private int modId;
}

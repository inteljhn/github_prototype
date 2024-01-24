package com.liqu.wiki.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Setter
//@EntityListeners(AuditingEntityListener.class)

@Getter
// @MappedSuperclass 어노테이션이 있어야 이 클래스를 상속받은 자식 Entity 클래스의 테이블이 생성될 때
// 이 클래스에 존재하는 필드들이 컬럼으로 생성됨
@MappedSuperclass 	 
public class BaseEntity {

	//@CreationTimestamp 	// 최초 insert 시간 자동 입력
	//@CreatedDate
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Timestamp regDate;
	
	@Column(nullable = false)
	private int regId;
	
	//@UpdateTimestamp
	//@LastModifiedDate
	@Column(columnDefinition ="datetime(6) ON UPDATE CURRENT_TIMESTAMP")
	@ColumnDefault("CURRENT_TIMESTAMP")
	private Timestamp modDate;
	
	@Column(nullable = false)
	private int modId;
	
}

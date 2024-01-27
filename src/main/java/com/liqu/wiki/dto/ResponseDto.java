package com.liqu.wiki.dto;

import lombok.Builder;
import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class ResponseDto<T> {
	int status;
	T data;
	
	public ResponseDto(int status) {
		super();
		this.status = status;
	}
	
	public ResponseDto(int status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
}

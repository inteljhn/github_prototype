package com.liqu.wiki.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinReqDto {
	@NotNull(message = "사용자명 값이 없습니다.")
	@NotBlank(message = "사용자명을 입력하세요.")
	@Size(max = 20, message = "사용자명 길이를 초과하였습니다(최대 20자리).")
	private String userName;
	
	@NotNull(message = "패스워드값이 없습니다.")
	@NotBlank(message = "패스워드를 입력하세요.")
	private String password;
	
	@NotNull(message = "이메일이 없습니다.")
	@NotBlank(message = "이메일을 입력하세요.")
	@Email(message = "이메일 양식이 올바르지 않습니다.")
	private String email;
}

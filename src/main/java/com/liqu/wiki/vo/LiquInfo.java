package com.liqu.wiki.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter  
//@Setter 	
@Data 						// @Data 어노테이션 사용하면  Geter, Setter 따로 작성할 필요 없이 한번에 됨
//@AllArgsConstructor 		// 모든 필드를 다 사용하는 생성자를 만들어주는 어노테이션
//@RequiredArgsConstructor 	// final 필드들을 사용하는 생성자를 생성하는 어노테이션
@NoArgsConstructor 			// 빈 생성자 생성 어노테이션
public class LiquInfo {
	private String liqId;
	private String nameKor;
	private String nameEng;
	private String categId;
	
	
	@Builder
	public LiquInfo(String liqId, String nameKor, String nameEng, String categId) {
		this.liqId = liqId;
		this.nameKor = nameKor;
		this.nameEng = nameEng;
		this.categId = categId;
	}

//	private final String liqId;
//	private final String nameKor;
//	private final String nameEng;
//	private final String categId;
	
}

package com.liqu.wiki.dto;

import lombok.Data;

@Data
public class KakaoProfile {
	public Long id;
	//public String connectedAt;
	public String connected_at;
	public Properties properties;
	//public KakaoAccount kakaoAccount;
	public KakaoAccount kakao_account;

	@Data
	public class Properties {
		public String nickname;
	}

	@Data
	public class KakaoAccount {
		//public Boolean profileNicknameNeedsAgreement;
		public Boolean profile_nickname_needs_agreement;
		public Profile profile;
		//public Boolean hasEmail;
		public Boolean has_email;
		//public Boolean emailNeedsAgreement;
		public Boolean email_needs_agreement;
		//public Boolean isEmailValid;
		//public Boolean isEmailVerified;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;

		@Data
		public class Profile {
			public String nickname;
		}
	}
}

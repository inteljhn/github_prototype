package com.liqu.wiki.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.liqu.wiki.entity.User;

import lombok.Getter;
import lombok.Setter;

// Spring Security가 로그인 요청을 가로채서 로그인을 진행호가, 완료가 되면 UserDetails 타입의 오브젝트를
// Spring Security의 고유한 세션 저장소에 저장해준다.
// 그 때 세션에 저장시킬 타입을 구현하는 클래스
@Getter
public class PrincipalDetail implements UserDetails{
	private User user; 	// 콤포지션

	public PrincipalDetail(User user){
		this.user = user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}

	/**
	 * 계정이 만료되지 않았는지 return 
	 * true : 만료되지 않음
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 계정이 잠겨있지 않았는지 return 
	 * true : 잠기지 않음
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 비밀번호가 만료되지 않았는지 return
	 * true : 만료되지 않음
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 계정이 활성화(사용가능)인지 return
	 * true : 활성화
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 계정이 가지고 있는 권한을 return
	 * 
	 * 권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 일단 예제에서는 하나만 사용
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		
		/*
		collectors.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				// 메타코딩 스프링부트 블로그 강좌 52강.
				// 권한 문자열 앞에 prefix(접두어) "ROLE_" 필수. Spring Security에서의 규칙이라고 함. 
				
				return "ROLE_" + user.getRole();
			}
		});
		*/
		// 위에 collectors.add() 하는 주석 내용을 아래 람다식으로 대체할 수 있음
		collectors.add(() -> {return "ROLE_" + user.getRole();});
		
		// TODO Auto-generated method stub
		return collectors;
	}	
}

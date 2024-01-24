<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">

	로그인

	<%-- 
	form 내부의 데이터를 json으로 말아서 ajax 서버로 던지는 방식을 사용할때는 form에 action이 필요없음 
	<form action="/user/join" method="POST">

	Spring Security를 활용한 로그인 구현을 위해 form에 action과 method를 설정
	SecurityConfig.java에 아래 action 속성에 들어있는 url을 제어하도록 설정하였음
	<form>
	--%>
	<form action="/auth/loginProc" method="POST">
		<div class="mb-3 mt-3">
			<label for="userNaame" class="form-label">User Name:</label> 
			<input type="text" class="form-control" id="userName" placeholder="Enter User Name" name="userName">
		</div>
		<div class="mb-3">
			<label for="password" class="form-label">Password:</label> 
			<input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
		</div>
		<%--
		<div class="form-check mb-3">
			<label class="form-check-label"> 
				<input class="form-check-input" type="checkbox" name="remember"> Remember me
			</label>
		</div>
		--%>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=be65533eb806952c5d4a31b3438f729b&redirect_uri=http://localhost:8000/auth/kakao/callback">
			<img height="38px" src="/images/kakao_login_medium.png" />
		</a>
	</form>

	<%--
	Spring Security를 활용한 로그인 구현으로 form 안으로 들어감	
	<button id="btn-login" class="btn btn-primary">로그인</button>
	--%>
</div>

<%--
Spring Security를 활용한 로그인 구현을 위해 주석처리
<script src="/js/user.js"></script>
--%>

<%@ include file="../layout/footer.jsp"%>

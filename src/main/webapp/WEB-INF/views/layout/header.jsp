<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	2024-01-14. planthoon
	taglib를 2개 import 하니까 자동완성이 각각 따로 되질 않음.
	< 까지만 치고 자동완성 할 때는 둘 다 나오긴 함...
	먼저 선언된 녀석만 잘 작동하길래.. sec를 위로 올림..
	
	html 태그 안에서는 이상없이 둘 다 자동완성 잘 됨...
--%>
<%-- Spring Security 태그 라이브러리 import. maven dependency --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- JSTL Core 라이브러리 import. maven dependency --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<sec:authorize access="isAuthenticated()">
	<%--
	<script>alert("로그인 된 사용자입니다.");</script>
	--%>
	<%-- 로그인시 세팅된 PrincipalDetail --%>
	<sec:authentication property="principal" var="princip" />
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>planthoon Study</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%-- 
	<link rel="icon" href="/favicon_16.png" type="image/png">
	<link rel="icon" href="/images/favicon-16.png" type="image/png">
	--%>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<%--
		2024-01-10. 인재훈.
		강좌에서는 부트스트랩 4버전을 사용했는데 최신버전인 5버전을 사용했더니 
		jquery.min.js과 popper.min.js 코드가 빠져버려서 자체적으로 추가
	--%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	
	<%-- 
		글쓰기에 Summernote WISIWYG 에디터 적용.
		<link href="/css/summernote-bs5.min.css" rel="stylesheet">
		<script src="/js/summernote-bs5.min.js"></script>
		강좌에서 알려주는 대로 Summernote 공식 홈페이지의 가이드를 따라 위의 참조를 추가하였으나
		정상적으로 작동하지 않아서 구글링하여 다른 예제를 참조하였음
		https://tools.ccusean.com/summernote
	--%>
	<%--
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	integrity="sha256-7ZWbZUAi97rkirk4DcEp4GWDPkWpRMcNaEyXGsNXjLg=" crossorigin="anonymous">
	--%>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css" 
	integrity="sha256-IKhQVXDfwbVELwiR0ke6dX+pJt0RSmWky3WB2pNx9Hg=" crossorigin="anonymous">
	
	<%--
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"
	integrity="sha256-u7e5khyithlIdTpu22PHhENmPcRdFiHRjhAuHcs05RI=" crossorigin="anonymous"></script>
	--%>
	
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.js"
	integrity="sha256-5slxYrL5Ct3mhMAp/dgnb5JSnTYMtkr4dHby34N10qw=" crossorigin="anonymous"></script>
	
	<!-- language pack -->
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/lang/summernote-ko-KR.min.js"
	integrity="sha256-y2bkXLA0VKwUx5hwbBKnaboRThcu7YOFyuYarJbCnoQ=" crossorigin="anonymous"></script>

	<%--
	<style type="text/css">
	input:read-only,
	textarea:read-only {
	  background-color: silver;
	}
	</style>
	--%>
</head>
<body>
	<%--
	<h1>${principal}</h1>
	--%>

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="/">홈</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<c:choose>
					<%--
					UserApiController 클래스의 login() 메소드를 활용한 로그인 처리시에는 sessionScope를 활용
					<c:when test="${empty sessionScope.principal}">
					--%>
					<c:when test="${empty princip}">
						<ul class="navbar-nav">
							<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
							<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="navbar-nav">
							<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li>
							<li class="nav-item"><a class="nav-link" href="/user/updateForm">회원정보</a></li>
							<%-- /logout : Spring Security에서 기본으로 제공하는 URL --%>
							<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</nav>
	
	<br />
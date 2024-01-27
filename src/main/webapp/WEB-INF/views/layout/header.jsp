<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 	<%-- Spring Security 태그 라이브러리 import. maven dependency --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 				<%-- JSTL Core 라이브러리 import. maven dependency --%>

<sec:authorize access="isAuthenticated()">
	<%--
		인증처리 완료 이후 수행할 스크립트 영역
		<script></script>
	--%>
	<%-- 로그인시 세팅된 PrincipalDetail --%>
	<sec:authentication property="principal" var="princip" />
</sec:authorize>

<!DOCTYPE html>

<%--
<html lang="en" data-bs-theme="dark">
--%>
<html lang="en">

<head>
<title>PlantHoon</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%-- 부트스트랩 관련 참조. --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%-- 글쓰기 기능에 적용할 Summernote WISIWYG 에디터 관련 참조. --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.css" integrity="sha256-IKhQVXDfwbVELwiR0ke6dX+pJt0RSmWky3WB2pNx9Hg=" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/summernote-lite.min.js" integrity="sha256-5slxYrL5Ct3mhMAp/dgnb5JSnTYMtkr4dHby34N10qw=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.20/dist/lang/summernote-ko-KR.min.js" integrity="sha256-y2bkXLA0VKwUx5hwbBKnaboRThcu7YOFyuYarJbCnoQ=" crossorigin="anonymous"></script>
<style type="text/css">
	/* offcanvas(모바일 햄버거 메뉴) width 설정 */ 
	.offcanvas {--bs-offcanvas-width: 180px;}
	
	/* footer 하단 고정 */	
	html, body{margin:0; padding:0; height:100%}
	.wrapper{display: flex; flex-direction:column; height: 100%;}
	.main-content{flex:1;}
	
	/* 기타 스타일 */
	.navbar{
		background-color:#92b7d1;
	}
	
	.card-header{
		background-color:#2d5892;
		color:white;
	}
	
	.card-header a {
	  	text-decoration-line: none;
	  	color: white;
	}
	
	.btn_func {
		background-color: #92b7d1;
		border-color: #92b7d1;
	}
	
	.btn_header {
		background-color: #2d5892;
		border-color: #2d5892;
	}
</style>
</head>
<body>
	<%-- 최상위 wrapper div 시작 --%>
	<div class="wrapper">
	
	<%--
	<nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
	--%>
	<nav class="navbar navbar-expand-md" style="background-color:#92b7d1;">
		<div class="container">
			<%-- 로고 영역 --%>
			<a class="navbar-brand" href="/">
				<img src="/images/logo.png" style="width:15px;height:15px;" />
				Plant.
			</a>
			
			<%-- 햄버거 메뉴 아이콘 --%>
			<button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offc_menu">
				<span class="navbar-toggler-icon"></span>
			</button>
			
			<%-- 일반 top 메뉴 영역 --%>
			<div class="collapse navbar-collapse d-md-flex" id="collapsibleNavbar">
				<c:choose>
					<%--
					UserApiController 클래스의 login() 메소드를 활용한 로그인 처리시에는 sessionScope를 활용
					<c:when test="${empty sessionScope.principal}">
					--%>
					<c:when test="${empty princip}">
						<ul class="navbar-nav col-md-10 justify-content-md-start">
							<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
							<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="navbar-nav col-md-8 justify-content-md-start">
							<li class="nav-item"><a class="nav-link" href="javascript:alert('개발중입니다.')">취미생활</a></li>
							<li class="nav-item"><a class="nav-link" href="/board/list">자유게시판</a></li>
							<li class="nav-item"><a class="nav-link" href="javascript:alert('개발중입니다.')">Util</a></li>
							<li class="nav-item"><a class="nav-link" href="javascript:alert('개발중입니다.')">Tech</a></li>
						</ul>
						<div class="d-md-flex col-md-4 justify-content-md-end">
							<button id="btn_myInfo" class="btn btn-primary btn-sm btn_header me-1">내 정보</button>
							<button id="btn_logout" class="btn btn-primary btn-sm btn_header">로그아웃</button>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			
			<%-- 햄버거 메뉴 - offcanvas 영역 --%>
			<div class="offcanvas offcanvas-end" id="offc_menu" style="display:none;">
				<div class="offcanvas-header">
					<h1 class="offcanvas-title">Heading</h1>
					<button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas"></button>
				</div>
				<div class="offcanvas-body">
				<c:choose>
					<c:when test="${empty princip}">
						<p><a class="nav-link" href="/auth/loginForm">로그인</a></p>
						<p><a class="nav-link" href="/auth/joinForm">회원가입</a></p>
					</c:when>
					<c:otherwise>
						<p><a class="nav-link" href="/board/saveForm">글쓰기</a></p>
						<p><a class="nav-link" href="/user/updateForm">회원정보</a></p>
						<p><a class="nav-link" href="/logout">로그아웃</a></p>
					</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>
	</nav>
	
	<script>
		let header = {
			
			init : function(){
				// offcanvas 열었을 때 이벤트
				$('.offcanvas').on('show.bs.offcanvas', function() {
				    $('.offcanvas').show();
				});
	
				// offcanvas 닫았을 때 이벤트
				$('.offcanvas').on('hide.bs.offcanvas', function() {
				    $('.offcanvas').hide();
				});
				
				$("#btn_myInfo").on("click", ()=>{
					location.href = "/user/updateForm";
				});
				
				$("#btn_logout").on("click", ()=>{
					<%-- /logout : Spring Security에서 기본으로 제공하는 URL --%>
					location.href = "/logout";
				});
			}
		}
		
		header.init();
	</script>
	
	<%-- 최상위 wrapper 바로 아래의 main-content div 시작 --%>
	<div class="main-content" style="padding-top:10px;">
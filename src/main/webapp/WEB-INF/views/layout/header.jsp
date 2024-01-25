<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	2024-01-14. planthoon
	taglib를 2개 import 하니까 자동완성이 각각 따로 되질 않음.
	< 까지만 치고 자동완성 할 때는 둘 다 나오긴 함...
	먼저 선언된 녀석만 잘 작동하길래.. sec를 위로 올림..
	
	html 태그 안에서는 이상없이 둘 다 자동완성 잘 됨...
--%>
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

<%--
	부트스트랩 관련 참조.
	
	2024-01-10. 인재훈.
	강좌에서는 부트스트랩 4버전을 사용했는데 최신버전인 5버전을 사용했더니 
	jquery.min.js과 popper.min.js 코드가 빠져버려서 자체적으로 추가
--%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%-- 
		글쓰기에 Summernote WISIWYG 에디터 적용 관련 참조.
		
		<link href="/css/summernote-bs5.min.css" rel="stylesheet">
		<script src="/js/summernote-bs5.min.js"></script>
		강좌에서 알려주는 대로 Summernote 공식 홈페이지의 가이드를 따라 위의 참조를 추가하였으나
		정상적으로 작동하지 않아서 구글링하여 다른 예제를 참조하였음
		https://tools.ccusean.com/summernote
--%>
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
	
	.navbar{
		background-color:#92b7d1;
	}
	
	.card-header{
		background-color:#2d5892;
		color:white;
	}
</style>
</head>
<body>
	<%--
	<h1>${principal}</h1>
	--%>
	
	<%-- 최상위 wrapper div 시작 --%>
	<div class="wrapper">
	
	<%--
	<nav class="navbar navbar-expand-sm bg-secondary navbar-dark">
	--%>
	<nav class="navbar navbar-expand-sm" style="background-color:#92b7d1;">
		<div class="container">
			
			<a class="navbar-brand" href="/"><img src="/images/logo.png" style="width:15px;height:15px;" /> Plant.</a>
			
			<%-- offcanvas 햄버거 메뉴 아이콘 --%>
			<button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offc_menu">
				<span class="navbar-toggler-icon"></span>
			</button>
			
			<%--
			기본 햄버거 메뉴 아이콘
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar" aria-expanded="false" aria-controls="navbarSupportedContent" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			--%>
			
			<%-- 일반 top 메뉴 영역 --%>
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
				})
			}
		}
		
		header.init();
	</script>
	
	<%-- 최상위 wrapper 바로 아래의 main-content div 시작 --%>
	<div class="main-content" style="padding-top:10px;">
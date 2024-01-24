<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 아래 include 경로 상대경로로 찾아야 함 --%>
<%@ include file="layout/header.jsp" %>

	<div class="container-fluid mt-3">
	
	<c:forEach var="board" items="${boards.content}">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
			</div>
		</div>
	</c:forEach>
		<%-- 
			justify-content-center : 부트스트랩에서 flex dom을 가운데로 정렬하는 클래스 
			justify-content-end : 부트스트랩에서 flex dom을 맨 끝(오른쪽)으로 정렬하는 클래스
			justify-content-start : 부트스트랩에서 flex dom을 처음(왼쪽) 정렬하는 클래스
		--%>
		<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:otherwise>
		</c:choose>			
		</ul>
	</div>

<%@ include file="layout/footer.jsp" %>
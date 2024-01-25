<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container">

	<%--
	<c:forEach var="board" items="${boards.content}">
		<div class="card m-2">
			<div class="card-body d-flex justify-content-between">
				<h4 class="card-title">${board.title}</h4>

				<a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
			</div>
		</div>
	</c:forEach>
	--%>

	<div class="row mb-3">
		<div class="col-md-6">
			<div class="card row g-0 border rounded overflow-hidden flex-md-row shadow-sm h-md-250 position-relative">
				<div class="card-header">Liquor Wiki</div>
				<div class="card-body" style="min-height:155px; max-height:155px;">
					<div class="list-group">
						<%--
						<c:forEach var="board" items="${null}">
							<a href="/board/${board.id}" class="list-group-item list-group-item-action">${board.title}</a>
						</c:forEach>
						--%>
						<a href="#" class="list-group-item list-group-item-action">조니워커 블랙</a>
						<a href="#" class="list-group-item list-group-item-action">라가불린 16</a>
						<a href="#" class="list-group-item list-group-item-action">레미 마틴 VSOP</a>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-6">
			<div class="card row g-0 border rounded overflow-hidden flex-md-row shadow-sm h-md-250 position-relative">
				<div class="card-header">자유게시판</div>
				<div class="card-body" style="min-height:155px; max-height:155px;">
					<div class="list-group">
						<c:forEach var="board" items="${boards.content}">
							<a href="/board/${board.id}" class="list-group-item list-group-item-action">${board.title}</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row mb-3">
		<div class="col-md-6">
			<div class="card row g-0 border rounded overflow-hidden flex-md-row shadow-sm h-md-250 position-relative">
				<div class="card-header">Util</div>
				<div class="card-body" style="min-height:155px; max-height:155px;">
					<div class="list-group">
						<a href="#" class="list-group-item list-group-item-action">사용시간 요금 계산기</a>
						<a href="#" class="list-group-item list-group-item-action">...</a>
						<a href="#" class="list-group-item list-group-item-action">...</a>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-6">
			<div class="card row g-0 border rounded overflow-hidden flex-md-row mb-1 shadow-sm h-md-250 position-relative">
				<div class="card-header">Tech</div>
				<div class="card-body" style="min-height:155px; max-height:155px;">
					<div class="list-group">
						<a href="#" class="list-group-item list-group-item-action">Spring Boot</a>
						<a href="#" class="list-group-item list-group-item-action">Spring Security</a>
						<a href="#" class="list-group-item list-group-item-action">OAuth 2.0</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%-- 페이징 영역 --%>
	<%--
	<ul class="pagination justify-content-center mt-5">
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
	--%>
</div>

<%@ include file="layout/footer.jsp"%>
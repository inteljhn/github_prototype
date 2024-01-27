<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<%-- 목록 상단 버튼 영역 --%>
	<div class="row mb-2">
		<div class="col-6">
			<%--
			<span class="align-middle">Count :</span>
			--%>
		</div>
		<div class="col-6 d-flex justify-content-end">
			<a href="/board/saveForm" class="btn btn-primary btn-sm btn_func">글쓰기</a>
		</div>
	</div>

	<%-- 목록 영역 --%>
	<div class="card row g-0 border rounded overflow-hidden flex-md-row shadow-sm h-md-250 position-relative">
		<div class="card-header">자유게시판</div>
		<div class="card-body" style="min-height: 460px; max-height: 460px;">
			<div class="list-group">
				<c:forEach var="board" items="${boards.content}">
					<a href="/board/${board.id}" class="list-group-item list-group-item-action">${board.title}</a>
				</c:forEach>
			</div>
		</div>
	</div>

	<%-- 페이징 영역 --%>
	<ul class="pagination justify-content-center mt-4">
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number - 1}">이전</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">다음</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>

<%@ include file="../layout/footer.jsp"%>
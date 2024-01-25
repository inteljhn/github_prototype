<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 아래 include 경로 상대경로로 찾아야 함 --%>
<%@ include file="../layout/header.jsp"%>
<style type="text/css">


#divContent img {
  max-width: 100%;
  height: auto;
}

	

</style>
<div class="container">
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>

	<c:if test="${board.user.id == princip.user.id}">
		<%--
			<button id="btn-update" class="btn btn-warning">수정</button>
			--%>
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>

	<hr />

	<div>
		글 번호 : <span id="id"><i>${board.id}</i></span><br /> 작성자 : <span>${board.user.userName}</span>
	</div>

	<hr />

	<div class="mb-3 mt-3">
		<h3>제목 : ${board.title}</h3>
	</div>

	<hr />

	<div class="mb-3">
		<div id="divContent">
			${board.content}
		</div>
	</div>

	<hr />

	<%-- 댓글 작성 영역 Begin --%>
	<div class="card">
		<form>
			<input type="hidden" id="userId" value="${princip.user.id}" />
			<input type="hidden" id="boardId" value="${board.id}" />
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
	</div>
	<%-- 댓글 작성 영역 End --%>

	<br />

	<%-- 댓글 조회 영역 Begin --%>
	<div class="card">
		<div class="card-header">댓글 리스트</div>

		<ul id="reply-box" class="list-group">

			<c:forEach var="reply" items="${board.replys}">

				<li id="reply-${reply.id}" class="list-group-item d-flex justify-content-between">
					<div>${reply.content}</div>
					<div class="d-flex">
						<div class="fst-italic">작성자 : ${reply.user.userName}&nbsp;</div>
						<%-- 
							2024-01-22. planthoon. TODO - 내가 작성한 댓글이 아니면 댓글 삭제 불가하도록 처리
							강좌에서 직접 해보라고 하고 끝남
						--%>
						<button class="badge" onClick="board.replyDelete(${board.id}, ${reply.id})">삭제</button>
					</div>
				</li>

			</c:forEach>
		</ul>
	</div>
	<%-- 댓글 조회 영역 End --%>
</div>

<script src="/js/board.js"></script>

<%@ include file="../layout/footer.jsp"%>
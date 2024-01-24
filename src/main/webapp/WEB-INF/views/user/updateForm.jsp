<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">

	회원정보 수정 폼

	<form action="/action_page.php">
		<input type="hidden" id="id" value="${princip.user.id}" />
	
		<div class="mb-3 mt-3">
			<label for="userNaame" class="form-label">User Name:</label> 
			<input type="text" class="form-control" id="userName" placeholder="Enter User Name" readonly name="userName" value="${princip.user.userName}">
		</div>
		<c:if test="${empty princip.user.oauth}">
		<div class="mb-3">
			<label for="pwd" class="form-label">Password:</label> 
			<input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
		</div>
		</c:if>
		<div class="mb-3 mt-3">
			<label for="email" class="form-label">Email:</label> 
			<input type="email" class="form-control" id="email" placeholder="Enter email" name="email" value="${princip.user.email}" <c:if test="${not empty princip.user.oauth}">readonly</c:if> />
		</div>
	</form>

	<button id="btn-update" class="btn btn-primary">회원정보 수정</button>
</div>

<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container-fluid mt-3">

	회원가입 폼

	<form action="/action_page.php">
		<div class="mb-3 mt-3">
			<label for="userNaame" class="form-label">User Name:</label> <input type="text" class="form-control" id="userName" placeholder="Enter User Name" name="userName">
		</div>
		<div class="mb-3">
			<label for="pwd" class="form-label">Password:</label> <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
		</div>
		<div class="mb-3 mt-3">
			<label for="email" class="form-label">Email:</label> <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
		</div>
	</form>

	<button id="btn-save" class="btn btn-primary">회원가입</button>
</div>


<script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>
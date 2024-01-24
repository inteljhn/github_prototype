<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 아래 include 경로 상대경로로 찾아야 함 --%>
<%@ include file="../layout/header.jsp" %>

	<div class="container-fluid mt-3">
		글쓰기 화면
		<form action="/auth/loginProc" method="POST">
		<div class="mb-3 mt-3">
			<label for="userNaame" class="form-label">Titles:</label> 
			<input type="text" class="form-control" id="title" placeholder="Enter User Name" name="title">
		</div>
		<div class="mb-3">
			<label for="content">Content:</label>
			
			<textarea class="form-control summernote" rows="5" id="content" name="content"></textarea>
			<%--
			공식 예제에는 div를 사용하라고 되어있는데.. 일단 강좌대로 따라감
			<div class="summernote" id="summernote"></div>
			--%>
		</div>
	</form>
	
	<button id="btn-save" class="btn btn-primary">저장</button>
	
	</div>

	<script>
      $('.summernote').summernote({
        placeholder: '본문 내용을 작성하세요',
        tabsize: 2,
        height: 300
      });
    </script>
    <script src="/js/board.js"></script>
    
<%@ include file="../layout/footer.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 아래 include 경로 상대경로로 찾아야 함 --%>
<%@ include file="../layout/header.jsp" %>

	<div class="container">
		글 수정 화면
		<form action="/auth/loginProc" method="POST">
		
		<input type="hidden" id="id" value="${board.id}" />
		
		<div class="mb-3 mt-3">
			<label for="userNaame" class="form-label">Titles:</label> 
			<input type="text" class="form-control" id="title" placeholder="Enter User Name" name="title" value="${board.title}">
		</div>
		<div class="mb-3">
			<label for="content">Content:</label>
			
			<textarea class="form-control summernote" rows="5" id="content" name="content">
				${board.content}
			</textarea>
		</div>
	</form>
	
	<button id="btn-update" class="btn btn-primary">수정</button>
	
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
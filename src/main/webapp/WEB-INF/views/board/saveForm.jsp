<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 아래 include 경로 상대경로로 찾아야 함 --%>
<%@ include file="../layout/header.jsp" %>


<style>

@media (prefers-color-scheme: dark) { 
  .note-editor.note-airframe,
  .note-editor.note-frame {
    border: 1px solid #f5f5f5 !important;
  }

  .note-editor .note-toolbar .note-style .dropdown-style h1,
  .note-editor .note-toolbar .note-style .dropdown-style h2,
  .note-editor .note-toolbar .note-style .dropdown-style h3,
  .note-editor .note-toolbar .note-style .dropdown-style h4,
  .note-editor .note-toolbar .note-style .dropdown-style h5,
  .note-editor .note-toolbar .note-style .dropdown-style h6,
  .note-editor .note-toolbar .note-style .dropdown-style p,
  .note-popover .popover-content .note-style .dropdown-style h1,
  .note-popover .popover-content .note-style .dropdown-style h2,
  .note-popover .popover-content .note-style .dropdown-style h3,
  .note-popover .popover-content .note-style .dropdown-style h4,
  .note-popover .popover-content .note-style .dropdown-style h5,
  .note-popover .popover-content .note-style .dropdown-style h6,
  .note-popover .popover-content .note-style .dropdown-style p {
    color: #000;
  }
  
  .note-toolbar {
  	background-color:#6C757D;
  }
  .note-btn {
  	background-color:#6C757D;
  	color:white;
  }
  
  .note-editable h1,
  .note-editable h2,
  .note-editable h3,
  .note-editable h4,
  .note-editable h5,
  .note-editable h6,
  .note-editable p,
  .note-editable blockquote,
  .note-editable pre {
    color: #fff;
  }
}
</style>


	<div class="container">
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
			<!-- 공식 예제에는 div를 사용하라고 되어있는데.. 일단 강좌대로 따라감 -->
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
        //lang: 'ko-KR', // default: 'en-US'
      });
    </script>
    <script src="/js/board.js"></script>
    
<%@ include file="../layout/footer.jsp" %>
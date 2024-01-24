/**
 * 글 작성을 위한 javascript
 */

let board = {
	init : function(){
		
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		
		$("#btn-delete").on("click", ()=>{
			this.deleteById();
		});
		
		$("#btn-update").on("click", ()=>{
			this.update();
		});
		
		$("#btn-reply-save").on("click", ()=>{
			this.replySave();
		});
	},
	
	// 게시글 작성 요청
	save: function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data), 						// http body 데이터
			contentType: "application/json; charset=utf-8", 	// 전달하는 body 데이터가 어떤 타입인지(MIME)
			dataType: "json"
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	// 회원가입 수행 요청
	deleteById: function(){
		let id = $("#id").text();
		
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	// 게시글 작성 요청
	update: function(){
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data), 						// http body 데이터
			contentType: "application/json; charset=utf-8", 	// 전달하는 body 데이터가 어떤 타입인지(MIME)
			dataType: "json"
		}).done(function(resp){
			alert("글 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	// 댓글 작성 요청
	replySave: function(){
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};
		
		console.log(data);
		
		//let boardId = $("#boardId").val();
		
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data), 						// http body 데이터
			contentType: "application/json; charset=utf-8", 	// 전달하는 body 데이터가 어떤 타입인지(MIME)
			dataType: "json"
		}).done(function(resp){
			alert("댓글 작성이 완료되었습니다.");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	// 댓글 작성 요청
	replyDelete: function(boardId, replyId){
		
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(resp){
			alert("댓글 삭제 완료");
			location.href = `/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

board.init();
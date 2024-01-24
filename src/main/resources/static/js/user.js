/**
 * 
 */

let index = {
	// 아래에 버튼에 클릭 이벤트 핸들러를 설정하는 부분에서 화살표 함수를 사용함으로써
	// 여기에서의 this와 화살표 함수 안에서의 this가 같다고 함 (index)
	// 만약에 function() 으로 선언했으면 해당 함수 내부의 this는 window 객체를 가르키게 됨
	init : function(){
		
		// 2024-01-10. planthoon
		// => : 화살표 함수 선언.
		// 별도 스터디가 필요할듯..
		// 일단 강의에서는 여기에서는 this를 바인딩하기 위해서 사용했다고 함...
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		
		$("#btn-update").on("click", ()=>{
			this.update();
		});
		
		// Spring Security 활용한 로그인 구현으로 주석처리
		/*
		$("#btn-login").on("click", ()=>{
			this.login();
		});
		*/
	},
	
	// 회원가입 수행 요청
	save: function(){
		//alert("save 함수 호출됨");
		
		let data = {
			userName: $("#userName").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// 아래처럼 그냥 data 만 넣고 로그 찍으면 브라우저 개발자도구에서 object 값이 그대로 보임
		//console.log(data);


		// ajax 호출시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		// dataType 관련.. 
		// 기본적으로 서버로 무슨 요청을 보냈을 때 돌아오는 응답은 일단 문자열(String)임
		// dataType: "json" 지정시 서버의 응답이 json 포맷으로 왔다면 javascript object로 자동 변경함
		// dataType: 속성 생략해도 서버의 응답이 json 포맷으로 왔다면 디폴트로 javascript object로 자동 변경함
		// 원래는 디폴트가 아니었던 것으로 기억하는데 최근에 바뀐거 같다고 함...
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), 						// http body 데이터
			contentType: "application/json; charset=utf-8", 	// 전달하는 body 데이터가 어떤 타입인지(MIME)
			dataType: "json"
			
			
		}).done(function(resp){
			
			if(resp.status === 500){
				alert("서버 에러입니다.");
			}else{
				// done : 회원가입 수행 요청에 대한 응답이 돌아오면 실행
				alert("회원가입이 완료되었습니다.");
				console.log(resp);
				location.href = "/";	
			}
			
			
		}).fail(function(error){
			// fail : 회원가입 수행 요청 결과가 실패인 경우 실행
			alert(JSON.stringify(error));
		});
	},
	
	// Spring Security 활용한 로그인 구현으로 주석처리 
	// 로그인 수행 요청
	/*
	login: function(){
		
		let data = {
			userName: $("#userName").val(),
			password: $("#password").val()
		};
		
		$.ajax({
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), 						// http body 데이터
			contentType: "application/json; charset=utf-8", 	// 전달하는 body 데이터가 어떤 타입인지(MIME)
			dataType: "json"
			
		}).done(function(resp){
			// done : 회원가입 수행 요청 결과가 정상인 경우 실행
			alert("로그인이 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			// fail : 회원가입 수행 요청 결과가 실패인 경우 실행
			alert(JSON.stringify(error));
		});
	}
	*/
	
	// 회원가입 수행 요청
	update: function(){
		//alert("save 함수 호출됨");
		
		let data = {
			id: $("#id").val(),
			userName: $("#userName").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), 						// http body 데이터
			contentType: "application/json; charset=utf-8", 	// 전달하는 body 데이터가 어떤 타입인지(MIME)
			dataType: "json"
		}).done(function(resp){
			// done : 회원가입 수행 요청 결과가 정상인 경우 실행
			alert("회원수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			// fail : 회원가입 수행 요청 결과가 실패인 경우 실행
			alert(JSON.stringify(error));
		});
	}
	
	
}

index.init();
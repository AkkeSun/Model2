<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

</style>

<!--Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


<!-- 쿠키 사용하기  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>

<div align="center" style="margin-top: 200px; px-5">
<h1>회원가입</h1>
<p>환영합니다. 홍길동 홈페이지입니다</p>

<form action="member?param=regiAF" method="post">
<div class="row mb-1">
	<div class=col-sm-4></div>
	<div class=col-sm-4>
	<input type='text' class="form-control" name='id' id="_id" size='10' placeholder='user id'>
	</div>
	<div class=col-sm-4></div>	
</div>
<div class="row mb-1">
	<div class=col-sm-4></div>
	<div class=col-sm-4>
	<input type='password' class="form-control" name='pwd' id="_pwd" size='30' placeholder='password'>
	</div>
	<div class=col-sm-4></div>
</div>	
<div class="row mb-1">
	<div class=col-sm-4></div>
	<div class=col-sm-4>
 	<input type='text' name='name' class="form-control" size='20' placeholder='name'>
	</div>
	<div class=col-sm-4></div>
</div>
<div class="row mb-1">
	<div class=col-sm-4></div>
	<div class=col-sm-4>
	<input type='text' name='mail' class="form-control" id="_mail" size='20' placeholder='email'>
	</div>
	<div class=col-sm-4></div>
</div>
<div class="row mb-3">
	<div class=col-sm-4></div>
	<div class=col-sm-4 align=right> <button type=button id=btn class="btn btn-primary">중복확인</button> 
	<input type='submit' value='회원가입' class="btn btn-primary">
	</div>
	<div class=col-sm-4></div>
</div>
</form>

</div>

<script>
$(document).ready(function(){

	$("#btn").click(function(){
		$.ajax({
			url:"member?param=idCheck",
			type: "post",
			datatype:'json',
			data:{"id":$("#_id").val()},
			success:function( data ){	
				 
				if(data.result == "yes"){
					alert('사용할 수 있는 id입니다');
				}else{
					alert('사용할 수 없는 id입니다');
					$("#_id").val(""); // 기록한 id값 지우기 
				}		 
			},
			
			error:function(){
				alert('error');
			}	
		});
	});
});


</script>

</body>
</html>
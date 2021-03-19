<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
	.mainText{
		font-size:70px;
	}
	
</style>

<!-- Jquary -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!--Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>

<!-- Cookie -->
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

<style>
body {
  font-family: "Open Sans", -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", Helvetica, Arial, sans-serif; 
}

</style>


</head>
<body>
<div align="center" style="margin-top: 200px; px-5">

<div class="row mb-3">
	<div class=col-sm-4></div>
	<div class=col-sm-4><h2 class=mainText>login page</h2></div>
	<div class=col-sm-4></div>
</div>
<form action="member?param=loginAF" method="post">
<div class='row mb-1'>
	<div class=col-sm-4></div>
	<div class=col-sm-4><input type="text" class="form-control" id="_id" name="id" placeholder='user id'></div>
	<div class=col-sm-4></div>
</div>
<div class='row mb-2'>
	<div class=col-sm-4></div>
	<div class=col-sm-4 align=left>
	<input type="checkbox" id="chk_save_id" class="btn btn-secondary" autocomplete=off>
	<span style=font-size:13px>SAVE ID</span></div>
	<div class=col-sm-4></div>
</div>
<div class='row mb-3'>
	<div class=col-sm-4></div>
	<div class=col-sm-4><input type="password" class="form-control" name="pwd" size="20" placeholder='user password'></div>
	<div class=col-sm-4></div>
</div>
<div class=row>
	<div class="col-sm-4 ml-3"></div>
	<input type="submit" value="로그인" class="btn btn-info">
	<button type="button" onclick="account()" class="btn btn-info ml-1" >회원가입</button>
	<div class=col-sm-4></div>
</div>
</form>

</div>


<script type="text/javascript">

//가입
function account() {	
	location.href = "member?param=regi";
};



//쿠키 저장 
let user_id = $.cookie("user_id");

if(user_id != null){	// 저장된 id가 있음
//	alert("쿠키 있음");
	$("#_id").val( user_id );
	$("#chk_save_id").attr("checked", "checked");
}


$("#chk_save_id").click(function() {
	
	//체크한다면
	if( $("#chk_save_id").is(":checked") ){ 
		//아이디를 입력 안했다면
		if( $("#_id").val().trim() == "" ){
			Swal.fire(
					  'Good job!',
					  'You clicked the button!',
					  'success'
					)
			//alert('id를 입력해 주십시오');
			$("#chk_save_id").prop("checked", false);			
		//아이디를 입력했다면
		
		}else{
			// 쿠키를 저장 (k:v)                                                                             //유지기간(일)  //전역에 걸쳐 저장
			$.cookie("user_id", $("#_id").val().trim(), { expires:7, path:'./'});
		}
	}
	
	//체크해제한다면 (쿠키에서 제거 ->  유지기간 삭제)
	else{                      
		$.removeCookie("user_id", { path:'./' });
	}
	
	
	
}); 

</script>

</body>
</html>
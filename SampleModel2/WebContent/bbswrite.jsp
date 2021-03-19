<%@page import="dto.MemberDto"%>
<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
//로그인 정보 값 가져오기
Object ologin = session.getAttribute("login");
MemberDto mem = (MemberDto)ologin;
%>        
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>


<!-- 글을 작성 -->
<br><br><br><br>
<div align=center>
<form id=frm>
<table border=1>
<col width='70'><col width='600'>
<tr>
	<th>아이디</th>
	<td><%=mem.getId() %></td>
</tr>
<tr>
	<th>제목</th>
	<td><input type='text' name=title size=100% style=border-color:white></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea rows=30% cols=100% name=contents style=border-color:white></textarea></td>
</tr>
<tr>
<td colspan=2 align=right><button type=button id=btn>작성완료</button></td>
</tr>
</table>
</form>
</div>


<!-- bbs.writerAf.jsp -->

<script>
	$(document).ready(function () {
		
		$('#btn').click(function(){
			$('#frm').attr('action', 'bbswriteAF.jsp')
			$('#frm').submit();
			
		});
		
	});

</script>







</body>
</html>
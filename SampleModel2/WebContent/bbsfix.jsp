<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dto.MemberDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<body>

<%
int seq = Integer.parseInt((String)request.getParameter("seq"));
BbsDto d = (BbsDto)request.getAttribute("d");
MemberDto mem = (MemberDto)request.getAttribute("mem");
%>


	
<br><br><br><br>
<div align=center>
<form action='bbs'>
<input type="hidden" name="param" value="bbsfixAF">
<table border=1>
<col width='70'><col width='600'>
<tr>
	<th>작성자</th>
	<td><%=mem.getId() %></td>
</tr>
<tr>
	<th>작성일</th>
	<td><%=d.getWdate() %></td>
</tr>
<tr>
	<th>제목</th>
	<td><input type='text' name='title' size=20 value=<%=d.getTitle() %> style=border-color:white></td>
</tr>
<tr>
	<th>조회수</th>
	<td><%=d.getReadcount()%></td>
</tr>
<tr>
	<th>정보</th>
	<td></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea rows=30% cols=100% name=contents><%=d.getContents() %></textarea></td>
</tr>
<tr>
<td></td>
<td>
<input type='submit' value='수정완료'>
<button type=button id=btn1>수정취소</button>
</tr>
</table>
<input type="hidden" name="param" value="bbsfixAF">
<input type="hidden" name="seq" value="<%=seq %>">
</form>
</div>
	



<script>

$(document).ready(function () {

	$('#btn1').click(function () {
		location.href= 'bbs?param=bbslist';
	});
	
});


</script>	


</body>
</html>
<%@page import="Util.UtilEx"%>
<%@page import="cal.CalendarDto"%>
<%@page import="cal.CalendarDao"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

   
<%
	MemberDto mem = (MemberDto)request.getAttribute("mem");
	int year = Integer.parseInt((String)request.getAttribute("year"));
	int month = Integer.parseInt((String)request.getAttribute("month"))+1;
	int dayOfWeek = Integer.parseInt((String)request.getAttribute("dayOfWeek"));
	int lastday = Integer.parseInt((String)request.getAttribute("lastday"));
	int weekday = Integer.parseInt((String)request.getAttribute("weekday"));
	List <CalendarDto>list = (List<CalendarDto>)request.getAttribute("list");
%> 
    
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정관리</title>
</head>
<body>

<h4 align="right" style="background-color: #f0f0f0">환영합니다 <%=mem.getId() %>님</h4>
<h2>일정관리</h2>



<%
	
	// 버튼 (Model 2에서는 Dto만들어서 한꺼번에 묶어 보내자!)
	// << year --
	String pp = String.format("<a href='%s?year=%d&moneth=%d'><img src='./Image/left.gif'></a>",
	                          "cal?param=calendarList", year-1, month);
	
	
	// <  month--
	String p = String.format("<a href='%s?year=%d&month=%d'><img src='Image/prec.gif'></a>", 
								"cal?param=calendarList", year, month-1);
	
	
	// > month++
	String n = String.format("<a href='%s?year=%d&month=%d'><img src='Image/next.gif'></a>", 
								"cal?param=calendarList", year, month+1);
	
	
	// >> year ++
	String nn = String.format("<a href='%s?year=%d&moneth=%d'><img src='./Image/last.gif'></a>",
	                          "cal?param=calendarList", year+1, month);
%>



<!-- 달력만들기 -->
<div align=center>

<table border=1>
<col width="100"><col width="100"><col width="100"><col width="100">
<col width="100"><col width="100"><col width="100">

<tr height='100'>
	<td colspan='7' align="center" style='padding-top: 20px'>
		<%=pp %> &nbsp;&nbsp; <%=p %> &nbsp;
	<font color=black style='font-size:50px'>
		                
		<%=String.format("%d년&nbsp;&nbsp;%d월", year, month) %>		
	</font>
		<%=n %> &nbsp;&nbsp; <%=nn %> &nbsp;
	</td>
</tr>



<tr height='50'>
	<th aligh="center">일</th>
	<th aligh="center">월</th>
	<th aligh="center">화</th>
	<th aligh="center">수</th>
	<th aligh="center">목</th>
	<th aligh="center">금</th>
	<th aligh="center">토</th>
</tr>



<tr height="100" align="left" valign='top'>

<%

// 위쪽 빈칸                 1일자 요일
for(int i=1; i<dayOfWeek; i++){
	%>
	<td style='background-color: #cecece'></td>
	<%
}


// 날짜
for(int i=1; i<= lastday; i++){ // i = day
	%>
	<td>   <!-- 날짜 -->                                    <!-- 등록 -->
		<%=UtilEx.callist(year, month, i) %> &nbsp;&nbsp; <%=UtilEx.showPen(year, month, i) %> 
		<%=UtilEx.makeTable(year, month, i, list) %>      <!-- 목록테이블 -->
	</td>
	
	<%
	// 정렬하기 (오늘날짜+1일자 요일자리 -1) % 7 
	if((i+dayOfWeek -1) % 7 == 0){
		%>
		</tr>
		<tr height='100' align='left' valign='top'>
		<%
	}
}
%>
<%
//밑의 빈칸 구하기 
for(int i=0; i< 7-weekday; i++){
	%>
	<td style='background-color: #cecece'></td>
	<%
}
%>

</tr>

</table>
</div>



</body>
</html>
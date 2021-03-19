<%@page import="java.util.ArrayList"%>
<%@page import="dto.BbsDto"%>
<%@page import="java.util.List"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
//session 로그인 정보 확인
Object ologin = session.getAttribute("login");
MemberDto mem = null;
if(ologin == null){
	%>	
	<script>
	alert('로그인 해 주십시오');
	location.href = "login.jsp";
	</script>	
	<%
} 
mem = (MemberDto)ologin;
%>



<%!

public String arrow(int depth){
	String rs = "<img src='./Image/arrow.png' width='20px' heiht='20px'/>";
	String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";	// 여백
	
	String ts = "";
	for(int i = 0;i < depth; i++){
		ts += nbsp;
	}
	
	return depth==0 ? "":ts + rs;	
}

%>



<%
String choice = request.getParameter("choice");
String search = request.getParameter("search");
if(choice == null){
	choice = "";
}
if(search == null){
	search = "";
}
%>


<%
// Controller에서 값을 가져오기
List<BbsDto> list = (List<BbsDto>)request.getAttribute("list");
int pageNumber = Integer.parseInt((String)request.getAttribute("pageNumber")); // 현재 페이지
int bbsPage = Integer.parseInt((String)request.getAttribute("bbsPage"));       // 표시되는 페이지 수 최대 값  


//페이지를 번호 클릭했을 때 현재 페이지를 바꿔라
String spageNumber = request.getParameter("pageNumber");
if(spageNumber != null && !spageNumber.equals("")){	
	pageNumber = Integer.parseInt(spageNumber);
}



//출력될 페이지 수 (1페이지부터 시작되어야 하니 1 더해주기)
System.out.println("현재 페이지:" + pageNumber);
System.out.println("표시되는 페이지 수 :" + bbsPage);
%>






    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bbslist(Bulletin Board System) = 전자 게시판</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body bgcolor="#e9e9e9">
<h4 align="right" style="background-color: #f0f0f0">환영합니다 <%=mem.getId() %>님</h4>
<br>
<div align="center">
<h1 id=head style='cursor:pointer'>게시판</h1>
<br>
<table class="table table-hover" style="width: 1000px">
<thead>
<tr>
	<th>번호</th><th>제목</th><th>작성자</th>
</tr>
</thead>


<tbody>
<%
if(list == null || list.size() == 0){
	%>
	<tr>
		<td colspan="3">작성된 글이 없습니다</td>
	</tr>
	<%
}else{
	
	for(int i = 0;i < list.size(); i++){
		BbsDto bbs = list.get(i);
		%>
		<tr>
			<th><%=i + 1 %></th>
			<td>
				<%
				if(bbs.getDel() == 0){
					%>
					<%=arrow( bbs.getDepth() ) %>			
					<a href="bbs?param=bbsdetail&seq=<%=bbs.getSeq() %>">
						<%=bbs.getTitle() %>
					</a>	
					<%
				}else{
					%>		
					<font color="#ff0000">********* 이 글은 작성자에 의해서 삭제되었습니다</font> 
					<%
				}
				%>
			</td>
			<td align="center"><%=bbs.getId() %></td>
		</tr>
		<%
	}
}
%>
</tbody>
</table>
</div>

<div align="center">
<!-- 페이징 -->
<%                //출력되는 페이지 수 
for(int i = 0;i < bbsPage; i++){
	if(pageNumber == i){	// 현재 페이지 [1] 2 [3] 
		%>
		<span style="font-size: 15pt; color: #0000ff; font-weight: bold;">
			<%=i + 1 %>
		</span>&nbsp;
		<%
	}
	else{
		%>		
		<a href="#none" title="<%=i+1 %>페이지" onclick="goPage(<%=i %>)"
			style="font-size: 15pt; color: #000; font-weight: bold; text-decoration: none">
			[<%=i + 1 %>]
		</a>&nbsp;	
		<%
	}	
}
%>
</div>
<br>




<div align=center>
</div>
<div align="center">
<select id="choice"> 
	<option value="title">제목</option>
	<option value="content">내용</option>
	<option value="writer">작성자</option>
</select>

<input type="text" id="search" value='<%=search %>'>
<input type='button' class='btn btn-secondary' value='검색' id='_search'>
<br><br>
<input type='button' class='btn btn-primary' value='글쓰기' id='write'>
<input type='button' class='btn btn-primary' value='목록' id='title'>
<input type='button' class='btn btn-primary' value='로그아웃' id='logout'>

<br><br>
</div>







<script type="text/javascript">

$(document).ready(function () {
	
	//제목 누르면 새로고침
	$('#head').click(function() {
		location.href='bbs?param=bbslist';
	});
	
	//검색 후 choice 고정시키기
	let obj = document.getElementById("choice");
	obj.value = "<%=choice %>";
	obj.setAttribute("selected", "selected");
	
	
	//검색기능
	$("#_search").click(function() {
		alert('click');
		let choice = $('#choice').val();
		let search = $('#search').val();
		location.href = "bbs?param=bbslist&choice="+choice+"&search="+search;
	});
	
	//목록
	$('#title').click(function() {
		location.href='bbs?param=bbslist';
	});
	

	
	//글쓰기
	$("#write").click(function() {
		location.href='bbs?param=bbswrite';
	});

	
	//로그아웃
	$('#logout').click(function() {
		location.href='bbs?param=logout';
	});
	
});



//페이지 넘버 클릭
function goPage (pageNum){
	let choice = document.getElementById("choice").value;
	let search = document.getElementById("search").value;
	location.href = "bbs?param=bbslist&choice=" + choice + "&search=" + search + "&pageNumber=" + pageNum;
}


</script>

</body>
</html>






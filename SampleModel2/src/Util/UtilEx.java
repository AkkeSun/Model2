package Util;

import java.util.List;

import cal.CalendarDto;

public class UtilEx {

	// 날짜를 클릭하면 그날의 일정을 모두 볼 수 있는 callist.jsp로 이동하는 함수 (출력은 일만)
	public static String callist(int year, int month, int day) {
		String str = "";
		
		str += String.format("&nbsp;<a href='%s?param=%s&year=%d&month=%d&day=%d'>", 
										"cal", "calist",year, month, day);
		str += String.format("%2d", day);
		str += "</a>";
		   
		return str;
	}



	// 일정을 추가하기 위해서 pen 이미지를 클릭하면 calwrite.jsp로 이동하는 함수
	public static String showPen(int year, int month, int day) {
		String str = "";
		
		String image = "<img src='Image/pen2.png' width='18px' height='18px'>";
		
		str = String.format("<a href='%s?year=%d&month=%d&day=%d'>%s</a>", 
				"calwrite.jsp", year, month, day, image);
		
		return str;		
	}
	



	// 한문자를 두문자로 변경해주는 함수  1~9 -> 01~09
	public static String two(String msg) {
		return msg.trim().length()<2?"0"+msg.trim():msg.trim();
	}



	// nvl 함수 : 문자열이 비어있는지 확인하는 함수  (비었으면 true)
	public static boolean nvl(String msg) {
		return msg==null || msg.trim().equals("")?true:false;
	}




	// 목록 테이블
	public static String makeTable(int year, int month, int day, List<CalendarDto> list) {
		String str = "";

		//20210319
		String dates = (year+"") + two(month+"") + two(day+ "");


		str += "<table>";
		str += "<col width='100'>";

		// list 갯수만큼 출력
		for (CalendarDto dto : list) {
			if(dto.getRdate().substring(0, 8).equals(dates)) {  //입력날짜랑 약속날짜 (0~7 까지 가져온다)가 같다면 출력
				str += "<tr>";
				str += "<td style='line-height: 10px; overflow: hidden; border-collapse:collapse;border:1px blue solid'>";

				// 제목 누르면 디테일로 날라감
				str += "<a href='caldetail.jsp?seq=" + dto.getSeq() + "'>";
				str += "<font style = 'font-size:8px; color:blue'>";

				str += dot3(dto.getTitle());	

				str += "</font>";
				str += "</a>";


				str += "</td>";
				str += "</tr>";
			}
		}

		str += "</table>";
		
		return str;
	}

	
	
	// 일정의 제목이 길 때 ...로 처리하는 함수	
	public static String dot3(String title) {
		String str = "";
		if(title.length() >= 7) { 
			str = title.substring(0, 7) + "...";
		}
		else { 
			str = title.trim();
		}
		return str;
	}


}

package cal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import Util.UtilEx;
import dao.BbsDao;
import dao.MemberDao;
import dto.BbsDto;
import dto.MemberDto;
import net.sf.json.JSONObject;

@WebServlet("/cal")
public class CalController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}


	
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MemberController doProcess");

		req.setCharacterEncoding("utf-8");	
		String param = req.getParameter("param"); // 컨트롤러로 받아오는 값을 저장 


		if(param.equals("calendarList")) {
			
			String syear = req.getParameter("year");
			String smonth = req.getParameter("month");
			String day = req.getParameter("day");
		
			
			HttpSession session = req.getSession();
			MemberDto mem = (MemberDto)session.getAttribute("login");
			
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DATE, 1); //  1일자로 초기화 
			
			// 오늘 연 월 저장 (일단 보이는건 오늘 날짜 기준)
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1; //달은 0부터 넘어옴
			
			
			if(UtilEx.nvl(syear) == false) {
				year = Integer.parseInt(syear);
			}
			if(UtilEx.nvl(smonth) == false) {
				month = Integer.parseInt(smonth);
			}
			
			
			
			// 버튼 누르고 해를 넘어가는 경우에 
			if(month < 1) {
				month = 12;
				year--;
			}
			if(month >12) {
				month = 1;
				year++;
			}
			
			
			//연 월 일 최종 셋팅 (달을 넣을 땐 1 빼고 넣고, 불러올 때 1을 다시 넣는다)
			cal.set(year, month -1, 1);
			
			//셋팅한 날의 요일 (1 = 일요일)
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			
			CalendarDao dao = CalendarDao.getInstance();
			List<CalendarDto> list = dao.getCalendaerList(mem.getId(), year+UtilEx.two(month+""));
			
			
			int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
			cal.set(Calendar.DATE, lastday); // 그 달의 마지막 날짜로 초기화 
			int weekday = cal.get(Calendar.DAY_OF_WEEK); 

			
			req.setAttribute("mem", mem);
			req.setAttribute("year", year+"");
			req.setAttribute("month", month+"");
			req.setAttribute("day", day+"");
			req.setAttribute("dayOfWeek", dayOfWeek+"");
			req.setAttribute("lastday", lastday+"");
			req.setAttribute("weekday", weekday+"");
			req.setAttribute("list", list);
			
			forward("calendarList.jsp", req, resp);
			
			
		}
		else if (param.equals("calist")) {
			String year = req.getParameter("year");
			String month = req.getParameter("month");
			String day = req.getParameter("day");
		}
		
		
		
		
		
		
	} // method
	
	
	public void forward(String arg, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		RequestDispatcher dispatch = req.getRequestDispatcher(arg);
		dispatch.forward(req, resp);			
	}
}

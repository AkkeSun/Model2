package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BbsDao;
import dao.MemberDao;
import dto.BbsDto;
import dto.MemberDto;
import net.sf.json.JSONObject;


@WebServlet("/member")
public class MemberController extends HttpServlet {


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


		//값을 판단하여 지정 파일로 이동해준다.
		//로그인
		if(param.equals("login")){
			resp.sendRedirect("login.jsp");
			
			
		//회원가입	
		} else if(param.equals("regi")) {
			resp.sendRedirect("regi.jsp");
			
			
		//id 중복 유무체크 (ajax활용)	
		} else if(param.equals("idCheck")){
			String id = req.getParameter("id");
			
			// DB 확인
			MemberDao dao = MemberDao.getInstance();
			boolean b = dao.getId(id);
			String result = "";
			
			// 데이터 전송 YES or No
			if(b) { result = "no"; }
			else { result = "yes"; }
						
			JSONObject jObj = new JSONObject();
			jObj.put("result", result);
			resp.setContentType("applictaion/x-json; charset=UTF-8"); 
			resp.getWriter().print(jObj);
			
			
		//회원가입(최종)	
		} else if(param.equals("regiAF")) {
			System.out.println("MemberController regiAf");

			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String name = req.getParameter("name");
			String email = req.getParameter("mail"); 
			
			MemberDto dto = new MemberDto(id, pwd, name, email, 0);
			MemberDao dao = MemberDao.getInstance();
			boolean b = dao.addMember(dto);
		
			String msg = "OK";
			if(b == false) { msg = "NO"; }
			resp.sendRedirect("message.jsp?msg=" + msg);			
		
			
			
		//로그인(최종)
		} else if(param.equals("loginAF")) {
			System.out.println("123");
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String result = "no";
			
			MemberDao dao = MemberDao.getInstance();
			MemberDto dto = dao.login(new MemberDto(id, pwd, null, null, 0));

			if(dto != null && !dto.getId().equals("")){
				//session에 담기
				HttpSession session = req.getSession();
				session.setAttribute("login", dto);	
				result="yes";
			}
			resp.sendRedirect("bbs?param=bbslist");
		}
		
		
		
		
		
		
	} //method
		
} //class
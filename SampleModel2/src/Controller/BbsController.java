package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import dao.BbsDao;
import dao.MemberDao;
import dto.BbsDto;
import dto.MemberDto;
import net.sf.json.JSONObject;

@WebServlet("/bbs")
public class BbsController extends HttpServlet {

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


		//bbslist로 가라 
		if(param.equals("bbslist")) {
			System.out.println("In BbsController bbslist");
			String choice = req.getParameter("choice");
			String search = req.getParameter("search");
			String pageNumber = req.getParameter("pageNumber");
			int page = 0;

			if(choice == null){
				choice = "";
			} 
			if(search == null){
				search = "";
			}
			if(pageNumber != null) {
				page = Integer.parseInt(pageNumber);
			}

			//	잘 들어왔는지 확인
			System.out.println("choice: " +choice);
			System.out.println("search: " +search);
			System.out.println("page: " +page);

			// bblist 내용
			BbsDao dao = BbsDao.getInstance();
			List<BbsDto> list = dao.getBbsPageList(choice, search, page);

			//총 글의 수 
			int len = dao.getAllBbs(choice, search);

			//페이지 수 
			int bbsPage = len / 10;		
			if((len % 10) > 0){
				bbsPage = bbsPage + 1;
			}

			//래퍼클래스만 사용 가능하므로 String으로 받아서 형변환 하기
			req.setAttribute("list", list);
			req.setAttribute("bbsPage", bbsPage + "");
			req.setAttribute("pageNumber", page + "");

			// 보내기
			forward("bbslist.jsp", req, resp);

			
			
		// 글쓰기	
		} else if(param.equals("bbswrite")) {			
			resp.sendRedirect("bbswrite.jsp");	
			
		}else if(param.equals("bbswriteAf")) {
			//값 받아오기
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
				
			BbsDao dao = BbsDao.getInstance();
			boolean isS = dao.addBbsList(new BbsDto(id, title, content));
				if(!isS) {
					resp.sendRedirect("bbswrite.jsp");
				}
				
				resp.sendRedirect("bbs?param=bbslist");
			
				
			
			
		// 로그아웃
		} else if(param.equals("logout")) {
			HttpSession session = req.getSession();
			session.removeAttribute("login");
			resp.sendRedirect("login.jsp");


			
			
		// 디테일	
		} else if(param.equals("bbsdetail")) {
			System.out.println("In Bbs Deatil");
			int seq = Integer.parseInt(req.getParameter("seq"));

			HttpSession session = req.getSession();
			MemberDto mem = (MemberDto)session.getAttribute("login");

			BbsDao dao = BbsDao.getInstance();
			BbsDto d = dao.detailList(seq);
			
			req.setAttribute("seq", seq + "");
			req.setAttribute("mem", mem);
			req.setAttribute("d", d);
			
			forward("bbsdetail.jsp", req, resp);
	
			
			
			
			

		// 글 수정	
		} else if(param.equals("bbsfix")) {
			System.out.println("In Bbs fix");
			
			int seq = Integer.parseInt(req.getParameter("seq"));
			
			//로그인정보
			HttpSession session = req.getSession();
			MemberDto mem = (MemberDto)session.getAttribute("login");
			
			// 수정할 bbs디테일정보
			BbsDao dao = BbsDao.getInstance();
			BbsDto d = dao.detailList(seq);
				
			req.setAttribute("seq", seq + "");
			req.setAttribute("mem", mem);
			req.setAttribute("d", d);
			
			forward("bbsfix.jsp", req, resp);
			
			
		} else if(param.equals("bbsfixAF")) {
			System.out.println("In Bbsfix AF");
			
			//작성한 값을 변수에 저장
			String title = req.getParameter("title");
			String contents = req.getParameter("contents");
			int seq = Integer.parseInt(req.getParameter("seq"));

			//값이 잘 들어왔는지 확인
			System.out.println(title);
			System.out.println(contents);
			System.out.println(seq);
			
			BbsDao dao = BbsDao.getInstance();
			dao.fixBbsList(title, contents, seq);
			System.out.println("Bbs Fix success!!");
			
			resp.sendRedirect("bbs?param=bbslist");

		     

			
			
		// 글 삭제	
		} else if(param.equals("bbsdelete")) {
			System.out.println("In Bbs Delete");
			
			int seq = Integer.parseInt(req.getParameter("seq")); 
			BbsDao d= BbsDao.getInstance();
			d.deleteBbs(seq);
			
			System.out.println("delete Success!");
			
			resp.sendRedirect("bbs?param=bbslist");

			
			
			
		
		// 댓글쓰기 
		} else if(param.equals("bbsanswer")) {
			System.out.println("In Bbs answer");
			
			int seq = Integer.parseInt(req.getParameter("seq"));

			BbsDao dao = BbsDao.getInstance();
			BbsDto bbs = dao.detailList(seq);
			
			HttpSession session = req.getSession();
			MemberDto mem = (MemberDto)session.getAttribute("login");

			req.setAttribute("seq", seq+"");
			req.setAttribute("bbs", bbs);
			req.setAttribute("mem", mem);
			
			forward("bbsanswer.jsp", req, resp);
		
			
		} else if(param.equals("answerAF")) {	
			System.out.println("In Bbs answerAF");
			int seq = Integer.parseInt(req.getParameter("seq"));
			String id = req.getParameter("id");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			BbsDao d = BbsDao.getInstance();
			d.answer(seq, new BbsDto(id, title, content)); 
			System.out.println("anserAF success");
			
			resp.sendRedirect("bbs?param=bbslist");
		}

	}


	
	
	
	
	public void forward(String arg, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		RequestDispatcher dispatch = req.getRequestDispatcher(arg);
		dispatch.forward(req, resp);			
	}
}

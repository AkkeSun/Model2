package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import db.DbClose;
import db.DbConnection;
import dto.BbsDto;
import dto.MemberDto;

public class BbsDao {

	//Singleton 작업 
	private static BbsDao dao = new BbsDao();
	private BbsDao() {
		DbConnection.initConnection();

	}

	public static BbsDao getInstance() {
		return dao;
	}


	//DB close
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(conn != null)
				conn.close();
			if(stmt != null)
				stmt.close();
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




	///////////////////////////////////////////////////////////////////////////////////



	// List 출력 (검색 + 페이징)
	public List<BbsDto> getBbsPageList(String choice, String search, int page) {

		String sql = "SELECT SEQ, ID, REF, STEP, DEPTH, "
			    	+ " TITLE, CONTENT, WDATE, DEL, READCOUNT "
			        + " FROM " ;

		sql += " (SELECT ROW_NUMBER()OVER(ORDER BY REF DESC, STEP ASC) AS RNUM, "
	         + " SEQ, ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT "
	     	 + " FROM BBS " ;
		
		String sWord = "";
		if(choice.equals("title")) {
			sWord = " WHERE TITLE LIKE '%" + search + "%' ";
		}else if(choice.equals("content")) {
			sWord = " WHERE CONTENT LIKE '%" + search + "%' ";
		}else if(choice.equals("writer")) {
			sWord = " WHERE ID='" + search + "'";
		} 
		sql = sql + sWord;
		
		sql = sql + " ORDER BY REF DESC, STEP ASC) ";

		sql = sql + " WHERE RNUM >= ? AND RNUM <= ? ";
		
		int start, end;
		start = 1 + 10 * page;
		end = 10 + 10 * page;
		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<BbsDto> list = new ArrayList<BbsDto>();

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/4 getBbsSearchList success");

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			System.out.println("2/4 getBbsSearchList success");

			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsSearchList success");

			while(rs.next()) {
				BbsDto dto = new BbsDto(rs.getInt(1), 
						rs.getString(2), 
						rs.getInt(3), 
						rs.getInt(4), 
						rs.getInt(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getString(8), 
						rs.getInt(9), 
						rs.getInt(10));
				list.add(dto);
			}			
			System.out.println("4/4 getBbsSearchList success");

		} catch (SQLException e) {	
			System.out.println("getBbsSearchList fail");
			e.printStackTrace();
		} finally {			
			close(conn, psmt, rs);			
		}

		return list;
	}





	///////////////////////////////////////////////////////////////////////////////////

	
	// 글의 갯수 구하기 
	public int getAllBbs(String choice, String search) {
		String sql = " SELECT COUNT(*) FROM BBS ";
		
		String sWord = "";
		if(choice.equals("title")) {
			sWord = " WHERE TITLE LIKE '%" + search + "%' ";
		}else if(choice.equals("content")) {
			sWord = " WHERE CONTENT LIKE '%" + search + "%' ";
		}else if(choice.equals("writer")) {
			sWord = " WHERE ID='" + search + "'";
		} 
		sql = sql + sWord;
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int len = 0;
				
		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 getAllBbs success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 getAllBbs success");
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				len = rs.getInt(1);
			}
			System.out.println("3/3 getAllBbs success");
			
		} catch (SQLException e) {
			System.out.println("getAllBbs fail");
			e.printStackTrace();
		} finally {
			close(conn, psmt, rs);			
		}
		
		return len;
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////

	

	// 추가
	public boolean addBbsList(BbsDto dto) {
		String sql = " INSERT INTO BBS(SEQ, ID, REF, STEP, DEPTH, " 
				+ " TITLE, CONTENT, WDATE, "
				+ " DEL, READCOUNT ) "          // SEQ과 똑같이 셋팅
				+ " VALUES(SEQ_BBS.NEXTVAL, ?, (SELECT NVL(MAX(REF),0)+1 FROM BBS), 0, 0, ?, ?, SYSDATE, 0, 0) ";
		Connection conn = null;
		PreparedStatement psmt = null;		
		int count = 0;

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 addBbsList success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 addBbsList success");

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContents());

			count = psmt.executeUpdate();
			System.out.println("3/3 addBbsList success");

		} catch (SQLException e) {			
			e.printStackTrace();
			System.out.println("addBbsList fail");
		} finally {
			DbClose.close(conn, psmt, null);			
		}

		return count>0?true:false;
	}



	///////////////////////////////////////////////////////////////////////////////////



	// 수정
	public boolean fixBbsList(String title, String contents, int seq) {
		String sql = " UPDATE BBS "
				+ " SET TITLE = ?, CONTENT = ? "
				+ " WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;		
		int count = 0;

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 fixBbsList success");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 fixBbsList success");
			psmt.setString(1, title); 
			psmt.setString(2, contents);
			psmt.setInt(3, seq);

			count = psmt.executeUpdate();
			System.out.println("3/3 fixBbsList success");
			System.out.println("count:" + count);

		} catch (SQLException e) {			
			e.printStackTrace();
			System.out.println("fixBbsList fail");
		} finally {
			DbClose.close(conn, psmt, null);			
		}

		return count>0?true:false;
	}



	///////////////////////////////////////////////////////////////////////////////////




	// Detail 출력
	public BbsDto detailList(int seq) {
		String sql = " SELECT SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, WDATE, "
				+ " DEL, READCOUNT "
				+ " FROM BBS "
				+ " WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		BbsDto dto = null;



		try {
			conn = DbConnection.getConnection();
			System.out.println("1/4 getBbslist success");

			psmt = conn.prepareStatement(sql);    //쿼리문 완성
			psmt.setInt(1, seq);
			System.out.println("2/4 getBbslist success");

			rs = psmt.executeQuery();
			System.out.println("3/4 getBbslist success");

			if(rs.next()) {

				dto = new BbsDto(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), 
						rs.getInt(5), rs.getString(6), rs.getString(7), 
						rs.getString(8), rs.getInt(9),rs.getInt(10));

			}
			System.out.println("4/4 getBbslist success");

		} catch (SQLException e) {			
			e.printStackTrace();
			System.out.println("getBbslist fail");
		} finally {
			// 닫아주기
			close(conn, psmt, rs);			
		}

		return dto;
	}




	///////////////////////////////////////////////////////////////////////////////////





	// 조회수 올리기
	public void readcount(int seq) {
		String sql = " UPDATE BBS "
				+ " SET READCOUNT=READCOUNT+1 "
				+ " WHERE SEQ=? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		System.out.println("1/3 readcount success");

		try {
			conn = DbConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("2/3 readcount success");

			psmt.setInt(1, seq);
			psmt.executeUpdate();  
			System.out.println("3/3 readcount success");


		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("readcount fail");
		}   finally {
			close(conn, psmt, null);			
		}

	}



	///////////////////////////////////////////////////////////////////////////////////



	// 삭제
	public boolean deleteBbs(int seq) {

		String sql = " UPDATE BBS "
				+ " SET DEL=1 "
				+ " WHERE SEQ=? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 S deleteBbs");

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/3 S deleteBbs");

			count = psmt.executeUpdate();
			System.out.println("3/3 S deleteBbs");

		} catch (Exception e) {		
			System.out.println("fail deleteBbs");
			e.printStackTrace();
		} finally {
			close(conn, psmt, null);			
		}

		return count>0?true:false;
	}	




	///////////////////////////////////////////////////////////////////////////////////




	// 댓글달기                                 부모글번호, 새로은 답글
	public boolean answer(int seq, BbsDto bbs) {
		// update (이전 댓글들의 STEP을 1씩 늘린다_정렬)
		String sql1 = " UPDATE BBS "
				+ "	SET STEP=STEP+1 "
				+ " WHERE REF=(SELECT REF FROM BBS WHERE SEQ=? ) "
				+ "		AND STEP>(SELECT STEP FROM BBS WHERE SEQ=? )";		

		// insert 
		String sql2 = " INSERT INTO BBS "
				+ " (SEQ, ID, "
				+ " REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, WDATE, DEL, READCOUNT) "
				+ " VALUES(SEQ_BBS.NEXTVAL, ?, "
				+ "         (SELECT REF FROM BBS WHERE SEQ=?), "	   // REF
				+ "			(SELECT STEP FROM BBS WHERE SEQ=?) + 1, "  // STEP
				+ "         (SELECT DEPTH FROM BBS WHERE SEQ=?) + 1, " // DEPTH
				+ "         ?, ?, SYSDATE, 0, 0) ";     

		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;

		try {
			conn = DbConnection.getConnection();		
			conn.setAutoCommit(false); // 자동 커밋 해제 (수동모드)
			System.out.println("1/6 success answer");

			// update
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			System.out.println("2/6 success answer");

			count = psmt.executeUpdate();
			System.out.println("3/6 success answer");

			// psmt 초기화
			psmt.clearParameters(); 

			// insert
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, bbs.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, bbs.getTitle());
			psmt.setString(6, bbs.getContents());
			System.out.println("4/6 success answer");

			count = psmt.executeUpdate();
			System.out.println("5/6 success answer");

			conn.commit(); // 커밋 실행
			System.out.println("6/6 success answer");

		} catch (SQLException e) {
			System.out.println("answer fail");			
			try {
				conn.rollback(); // 실패시 돌아가기 
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}			
			e.printStackTrace();
		} finally {

			try {
				conn.setAutoCommit(true); //커밋 자동모드로 다시 바꾸기
			} catch (SQLException e) {				
				e.printStackTrace();
			}			
			close(conn, psmt, null);			
		}

		return count>0?true:false;		
	}



	///////////////////////////////////////////////////////////////////////////////////

	

} //class

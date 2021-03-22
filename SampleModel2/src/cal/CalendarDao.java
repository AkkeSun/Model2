package cal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DbClose;
import db.DbConnection;


public class CalendarDao {

	//싱글톤 작업
	private static CalendarDao dao= new CalendarDao();
	private CalendarDao() {
		DbConnection.initConnection();
	}

	public static CalendarDao getInstance() {
		return dao;
	}


	/////////////////////////////////////////////////////////////////////



	public List<CalendarDto> getCalendaerList(String id, String yyyyMM){
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RDATE, WDATE " + 
				" FROM" + 
				"    (SELECT ROW_NUMBER()OVER(PARTITION BY SUBSTR(RDATE, 1, 8)ORDER BY RDATE ASC) AS RNUM, " + 
				"         SEQ, ID, TITLE, CONTENT, RDATE, WDATE " + 
				"    FROM CALENDAR " + 
				"    WHERE ID=? AND SUBSTR(RDATE, 1, 6)=?) " + 
				" WHERE RNUM BETWEEN 1 AND 5 ";


		Connection conn = null;         // DB객채 생성
		PreparedStatement psmt = null;	// DB에 값을 보내기 위한 용도
		ResultSet rs = null;
		int count = 0;

		List<CalendarDto>list = new ArrayList<>();



		try {
			conn = DbConnection.getConnection();
			System.out.println("1/4 getCalendaerList success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, yyyyMM);
			System.out.println("2/4 getCalendaerList success");


			rs= psmt.executeQuery();
			System.out.println("3/4 getCalendaerList success");

			while(rs.next()) {
				int i = 1;
				CalendarDto dto = new CalendarDto( rs.getInt(i++),
						rs.getString(i++),	
						rs.getString(i++),
						rs.getString(i++),
						rs.getString(i++),		
						rs.getString(i++) );

				list.add(dto);
			}
			System.out.println("4/4 getCalendaerList success");

		} catch (SQLException e) {
			System.out.println("getCalendaerList fail");
			e.printStackTrace();
		} finally {
			DbClose.close(conn, psmt, rs);
		}
		return list;
	}



	/////////////////////////////////////////////////////////////////////


	// 일정 집어넣기
	public boolean addCalendar(CalendarDto cal) {
		String sql = " INSERT INTO CALENDAR(SEQ, ID, TITLE, CONTENT, RDATE, WDATE) "
				+ " VALUES(SEQ_CAL.NEXTVAL, ?, ?, ?, ?, SYSDATE) ";
		Connection conn = null;         // DB객채 생성
		PreparedStatement psmt = null;	// DB에 값을 보내기 위한 용도
		int count = 0;

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 addCalendar success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, cal.getId());
			psmt.setString(2, cal.getTitle());
			psmt.setString(3, cal.getContent());
			psmt.setString(4, cal.getRdate());
			System.out.println("2/3 addCalendar success");

			count = psmt.executeUpdate();
			System.out.println("3/3 addCalendar success");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("addCalendar fail");
		} finally {
			DbClose.close(conn, psmt, null);
		}

		return count>0?true:false;


	}


	/////////////////////////////////////////////////////////////////////

	//디테일
	public List<CalendarDto> getDetail(String seq){

		String sql = " SELECT * FROM CALENDAR WHERE SEQ = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<CalendarDto> list = new ArrayList<>();

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/4 getDetail success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, seq);
			System.out.println("2/4 getDetail success");

			rs = psmt.executeQuery();
			System.out.println("3/4 getDetail success");

			while(rs.next()) {
				int i = 1;
				CalendarDto dto = new CalendarDto( rs.getInt(i++),
						rs.getString(i++),	
						rs.getString(i++),
						rs.getString(i++),
						rs.getString(i++),		
						rs.getString(i++) );

				list.add(dto);
				System.out.println("4/4 getDetail success");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("getDetail fail");
		} finally {
			DbClose.close(conn, psmt, rs);
		}

		return list;
	}


	
	
	/////////////////////////////////////////////////////////////////////

	


	// 일정 수정
	public boolean updateCalendar(CalendarDto cal, String seq) {
		String sql = " UPDATE CALENDAR "
				+ " SET ID=?, TITLE=?, CONTENT=?, RDATE=? "
				+ " WHERE SEQ=? ";

		Connection conn = null;         // DB객채 생성
		PreparedStatement psmt = null;	// DB에 값을 보내기 위한 용도

		int count = 0;

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 updateCalendar success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, cal.getId());
			psmt.setString(2, cal.getTitle());
			psmt.setString(3, cal.getContent());
			psmt.setString(4, cal.getRdate());
			psmt.setInt(5, Integer.parseInt(seq));
			System.out.println("2/3 updateCalendar success");

			count = psmt.executeUpdate();
			System.out.println("3/3 updateCalendar success");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("updateCalendar fail");
		} finally {
			DbClose.close(conn, psmt, null);
		}

		return count>0?true:false;
	}




	/////////////////////////////////////////////////////////////////////



	// 일정 삭제
	public boolean deleteCalendar(String seq) {
		String sql = " DELETE FROM CALENDAR "
				   + " WHERE SEQ=? ";

		Connection conn = null;         // DB객채 생성
		PreparedStatement psmt = null;	// DB에 값을 보내기 위한 용도

		int count = 0;

		try {
			conn = DbConnection.getConnection();
			System.out.println("1/3 deleteCalendar success");

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(seq));
			System.out.println("2/3 deleteCalendar success");

			count = psmt.executeUpdate();
			System.out.println("3/3 deleteCalendar success");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("deleteCalendar fail");
		} finally {
			DbClose.close(conn, psmt, null);
		}

		return count>0?true:false;
	}

	/////////////////////////////////////////////////////////////////////

	
	
	
    //callsist 출력용
	public List<CalendarDto> getCallist(String msg){
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, RDATE, WDATE " + 
				     " FROM CALENDAR " +
				     " WHERE SUBSTR(RDATE, 1, 8) = ?";
		


		Connection conn = null;         // DB객채 생성
		PreparedStatement psmt = null;	// DB에 값을 보내기 위한 용도
		ResultSet rs = null;
		int count = 0;

		List<CalendarDto>list = new ArrayList<>();



		try {
			conn = DbConnection.getConnection();
			System.out.println("1/4 getCalendaerList success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, msg);
			System.out.println("2/4 getCalendaerList success");


			rs= psmt.executeQuery();
			System.out.println("3/4 getCalendaerList success");

			while(rs.next()) {
				int i = 1;
				CalendarDto dto = new CalendarDto( rs.getInt(i++),
						rs.getString(i++),	
						rs.getString(i++),
						rs.getString(i++),
						rs.getString(i++),		
						rs.getString(i++) );

				list.add(dto);
			}
			System.out.println("4/4 getCalendaerList success");

		} catch (SQLException e) {
			System.out.println("getCalendaerList fail");
			e.printStackTrace();
		} finally {
			DbClose.close(conn, psmt, rs);
		}
		return list;
	}
	



	/////////////////////////////////////////////////////////////////////








} // class 

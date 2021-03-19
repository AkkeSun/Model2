package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DbClose;
import db.DbConnection;
import dto.MemberDto;

public class MemberDao {
	
	//싱글톤 셋팅
	private static MemberDao dao = new MemberDao();
	private MemberDao() {
		DbConnection.initConnection();
	}
	
	public static MemberDao getInstance() {		
		return dao;
	}
	
	
	public boolean addMember(MemberDto dto) {
		
		String sql = " INSERT INTO MEMBER(ID, PWD, NAME, EMAIL, AUTH) "
					+ " VALUES(?, ?, ?, ?, 3) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;		
		int count = 0;
		
		try {
			conn = DbConnection.getConnection();
			//System.out.println("1/3 addMember success");
				
			psmt = conn.prepareStatement(sql);
			//System.out.println("2/3 addMember success");
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			
			count = psmt.executeUpdate();
			//System.out.println("3/3 addMember success");
			
		} catch (SQLException e) {			
			e.printStackTrace();
			System.out.println("addMember fail");
		} finally {
			DbClose.close(conn, psmt, null);			
		}
		
		return count>0?true:false;
	}
	
	
	
	
	public boolean getId(String id) {
		String sql = " SELECT ID FROM MEMBER WHERE ID = ? ";
		Connection conn = null;        // DB 객채생성
		PreparedStatement psmt = null; // DB에 값을 보내기 위한 용도
		ResultSet rs = null;           // DB에서 값을 받기위한 용도
		
		String findId = "";
		boolean findid=false;
		
		try {
			conn = DbConnection.getConnection();  //DB 연결
			//System.out.println("1/3 getId success");
			
			psmt = conn.prepareStatement(sql);  //쿼리문 완성
			psmt.setString(1, id.trim());
			//System.out.println("2/3 getId success");

			rs = psmt.executeQuery();           //저장할 주머니
			//System.out.println("3/3 getId success");

			if(rs.next()) {			 		    //값이 있으면 true
			 findid = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("false");
		} finally {
			// 닫아주기
			DbClose.close(conn, psmt, null);
		}

		return findid;
	}
	
	
	
	
	
public MemberDto login(MemberDto dto) {
		
		String sql = " SELECT ID, NAME, EMAIL, AUTH "
				+ " FROM MEMBER "
				+ " WHERE ID=? AND PWD=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto mem = null;
		
		try {
			conn = DbConnection.getConnection();  //DB 연결
			psmt = conn.prepareStatement(sql);
			System.out.println("1/3 login suc");
			
			//쿼리문 완성
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			System.out.println("2/3 login suc");
			rs = psmt.executeQuery();
			
			//값이 있다면 
			if(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				int auth = rs.getInt(4);	
				mem = new MemberDto(id, null, name, email, auth);				
			}
			
			System.out.println("3/3 login suc");
			
		} catch (Exception e) {
			System.out.println("login fail");
			e.printStackTrace();
		} finally {
			DbClose.close(conn, psmt, rs);			
		}
		
		//dto를 꺼낸다
		return mem;
	}
	
}







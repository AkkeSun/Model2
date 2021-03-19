package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	
	// 오라클 드라이버 실행 
	public static void initConnection() {
		try {                                             
			Class.forName("oracle.jdbc.driver.OracleDriver");  

		//	System.out.println("Driver Loading Success");

		} catch (ClassNotFoundException e) {
		//	System.out.println("Driver가 없습니다.");
		}
	}
	
	
	//DB에 접속 
	public static Connection getConnection() {
		// URL주소 : DB로 넘어가서 오라클 우클릭 - properties - Driver Properties - Connection URL 
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.0.232:1521:xe";
		String id = "hr";
		String pw = "hr";

		try {
			conn = DriverManager.getConnection(url, id, pw); // 주소, 계정, PW 
	//		System.out.println("Oracle Connection Success");
		} catch (SQLException e) {		
			e.printStackTrace();
			System.out.println("DB를 연결하지 못했습니다.");
		}
		return conn;
	}

}

package com.tj.ex06preparedstmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PreparedStatementDelete {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		// 사용자에게 부서 번호를 입력받아 부서 번호가 존재하면 수정작업(부서이름,부서위치를 받아 수정)
		// 						  부서 번호가 존재하지 않으면 없다고 출력
		// select(stmt 객체 이용) -> update(pstmt 객체이용)
		Scanner sc = new Scanner(System.in);
		System.out.print("수정할 부서 번호는?");
		int deptno = sc.nextInt();
		String selectSQL="SELECT COUNT(*) CNT FROM DEPT WHERE DEPTNO="+deptno;
		String updateSQL="UPDATE DEPT SET DNAME=?, LOC=? WHERE DEPTNO=?";
		
		Connection conn = null;
		Statement stmt =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSQL);
			rs.next(); // <- 이걸 해야 rs의 값을 긁어올getInt() 수 있다.
			int cnt = rs.getInt("cnt");
			if(cnt==1) {
				System.out.print("수정할 부서이름?");
				String dname = sc.next().toUpperCase();
				System.out.print("수정할 부서위치?");
				String loc = sc.next().toUpperCase();
				pstmt = conn.prepareStatement(updateSQL);
				pstmt.setString(1, dname);
				pstmt.setString(2, loc);
				pstmt.setInt(3, deptno);
				int result = pstmt.executeUpdate();
				System.out.println(result>0?"수정성공":"수정실패");				
			}else {
				System.out.println("존재하지 않는 부서번호라 수정 불가");
			}			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
			if(pstmt!=null) pstmt.close();
			if(rs!=null) rs.close();
			if(stmt!=null) stmt.close();
			if(conn!=null) conn.close();
			}catch (Exception e){}
		}
		
		
	}
}

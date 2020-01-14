package com.tj.ex06preparedstmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PreparedStatementUpdateSelect {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		// ����ڿ��� �μ� ��ȣ�� �Է¹޾� �μ� ��ȣ�� �����ϸ� �����۾�(�μ� �̸�, �μ� ��ġ�� �Է� �޾Ƽ� ����)
		// 						  �μ� ��ȣ�� �������� ������ ���ٰ� ���
		String selectSQL = "SELECT COUNT(*) CNT FROM DEPT WHERE DEPTNO=?";
		String updateSQL = "UPDATE DEPT SET DNAME=?, LOC=? WHERE DEPTNO=?";
		Scanner sc = new Scanner(System.in);
		System.out.print("������ �μ� ��ȣ��?");
		int deptno = sc.nextInt();
				                                           
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setInt(1,deptno);
			rs = pstmt.executeQuery();
			rs.next();
			int cnt = rs.getInt("cnt");
			if(cnt==1) {
				System.out.println("������ �μ� �̸���?");
				String dname = sc.next();
				System.out.println("������ �μ� ��ġ��?");
				String loc = sc.next();
				rs.close(); // �ڱ����� �� ��ü ���� �ݰ� 
				pstmt.close(); // �ڱ����� �� ��ü ���� �ݰ� 
				pstmt = conn.prepareStatement(updateSQL); // ��ü ���� �����.
				pstmt.setString(1, dname);
				pstmt.setString(2, loc);
				pstmt.setInt(3, deptno);
				int result = pstmt.executeUpdate();
				System.out.println(result>0? "��������" : "��������");
			
			}else {
				System.out.println("�μ���ȣ�� �������� �ʽ��ϴ�.");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }				
		}

	}
}
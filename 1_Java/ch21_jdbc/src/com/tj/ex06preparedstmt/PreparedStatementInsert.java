package com.tj.ex06preparedstmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PreparedStatementInsert {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		String sql = "INSERT INTO DEPT VALUES (?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Scanner sc = new Scanner(System.in);
		System.out.print("�Է��� �μ���ȣ?");
		int deptno = sc.nextInt();
		System.out.print("�μ� �̸���?");
		String dname = sc.next();
		System.out.println("�μ� ��ġ��?");
		String loc = sc.next();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement(sql); // ���� sql���� �� �� �ƴ�. sql�� ������ ��ü�� ������ ��.
			pstmt.setInt(1, deptno); // sql���� ù ��° ? ó�� 
			pstmt.setString(2, dname);
			pstmt.setString(3, loc); // �̷��Ը� �ϸ� Ÿ�Կ� ���� �˾Ƽ� '' ó�� ���� �ؼ� sql���� ����
			int result = pstmt.executeUpdate();
			if(result>0) {
				System.out.println(deptno+"�� �μ� �Է� ����");
			}else {
				System.out.println("�Է� ����");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }				
		}
		
		
	}
}
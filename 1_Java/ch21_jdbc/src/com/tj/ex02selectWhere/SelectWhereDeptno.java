package com.tj.ex02selectWhere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectWhereDeptno {
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.print("����� ���ϴ� �μ���ȣ��?");
		int deptno = sc.nextInt();
		String query="SELECT*FROM EMP WHERE DEPTNO="+deptno;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			if(rs.next()) { // 1�� �̻� ��� ���
				// if �� �ȿ��� �ٷ� while���� ����, rs.next()�� �� ���ϰ� �Ǽ� ù ��° ���� ������ �ȳ����ϱ�, do while �ؾ��Ѵ�.
				do {
					// rs�޾Ƽ� ��� (���,�̸�,�μ���ȣ)
					int empno = rs.getInt("empno");
					String ename = rs.getString("ename");
					int deptnum = rs.getInt("deptno");
					System.out.println(empno+"\t"+ename+"\t"+deptnum);
				}while(rs.next());
				
			}else { // ����� 0��
				System.out.println("�ش� �μ���ȣ�� ����� �����ϴ�.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }
		}
		
	}
}
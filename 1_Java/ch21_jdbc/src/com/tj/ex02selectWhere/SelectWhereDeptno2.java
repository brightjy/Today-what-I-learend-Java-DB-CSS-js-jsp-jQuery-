package com.tj.ex02selectWhere;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectWhereDeptno2 {
	// 1. ����ڿ��� �μ� ��ȣ�� �����,
	// 2-1. �ش� �μ� ��ȣ�� ������, �ش� �μ� �̸�, �ش� �μ� ��ġ ��� �� �ش� �μ� ����� ���
	// 2-2. ���� �μ� ��ȣ��, '���� �μ� ��ȣ'��� ���.
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.print("����� ���ϴ� �μ���ȣ��?");
		int deptno = sc.nextInt();
		
		String query1="SELECT*FROM DEPT WHERE DEPTNO="+deptno;
		String query2="SELECT*FROM EMP WHERE DEPTNO="+deptno;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query1);
			if(rs.next()) {
				String dname = rs.getString("dname");
				String loc = rs.getString("loc");
				System.out.println("�μ��� : "+dname+"\t �μ���ġ : "+loc);
				rs.close(); // ������ ������ rs �ݱ�
				rs = stmt.executeQuery(query2); // �ٽ� �ޱ�
				if(rs.next()) {
					do {
						int empno = rs.getInt("empno");
						String ename = rs.getString("ename");
						Date hiredate = rs.getDate("hiredate");
						System.out.println(empno+"\t"+ename+"\t"+hiredate);
					}while(rs.next());
				}else {
					System.out.println(deptno+"�� �μ��� ����� �����ϴ�.");
				}
						
			}else {
				System.out.println(deptno+"���� ��ȿ���� ���� �μ� ��ȣ �Դϴ�.");
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
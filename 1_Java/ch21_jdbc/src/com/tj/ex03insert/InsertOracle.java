package com.tj.ex03insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertOracle {
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.print("�Է��� �μ���ȣ?");
		int deptno = sc.nextInt();
		System.out.print("�Է��� �μ��̸�?");
		String dname = sc.next(); // �μ��̸��� �����̽� ��밡��
		System.out.print("�Է��� �μ���ġ?");
		sc.nextLine(); // ���� ����� �뵵
		String loc = sc.nextLine();
		String sql="INSERT INTO DEPT VALUES ("+deptno+",'"+dname.toUpperCase()+"','"+loc+"')";
		//sql = String.format("INSERT INTO DEPT VALUES (%d,'%s','%s')", deptno, dname, loc);
		
		Connection conn=null;
		Statement stmt=null;
		
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,"scott","tiger");
			stmt=conn.createStatement();
			int result = stmt.executeUpdate(sql); // insert,delete,update �� executeUpdate
			System.out.println(result>0? "�Է¼���":"�Է½���");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }
		}
	}
}
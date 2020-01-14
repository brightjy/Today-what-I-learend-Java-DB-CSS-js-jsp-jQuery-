package com.tj.ex02selectWhere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectWhereDname {
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.println("���ϴ� �μ� �̸���?");
		String dname = sc.nextLine(); // �μ��̸��� �����̽� ���� sc.nextLine
        String sql="SELECT * FROM DEPT WHERE DNAME='"+dname+"'"; // VARCHAR �ʵ�� ' ' single quotation mark�� �������Ѵ�. '' �Ȼ츮��, select*from dept where dname=dname �� �ϴ� ���̶� invalid identifier ������.
		//sql=String.format("SELECT*FROM DEPT WHERE DNAME='%s'",dname);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,"scott","tiger");
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) {
				int deptno = rs.getInt("deptno");
				String loc = rs.getString("loc");
				System.out.println("�Է��Ͻ� �μ���ȣ : "+deptno);
				System.out.println("�Է��Ͻ� �μ��̸� : "+dname);
				System.out.println("�Է��Ͻ� �μ���ġ : "+loc);
			}else {
				System.out.println("��ȿ���� ���� �μ��̸��Դϴ�.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage()+"����̹�����");
		} catch (SQLException e) {
			System.out.println(e.getMessage()+"SQL����");
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }
		}
	}
}
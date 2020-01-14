package com.tj.ex02selectWhere;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectWhereDname2 {
	public static void main(String[] args) {
		// 1. ����ڷκ��� �μ��̸� �޾�,
		// 2-1. �ش� �μ� �̸��� ���� ���, �μ��������, �ش� �μ� ��� (���,�̸�,�μ���ġ,�Ի���)
		// 2-2. �ش� �μ� �̸��� ���� ��� ���ٰ� ���
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.println("�˻��� ���ϴ� �μ�����?");
		String dname = sc.nextLine();
		String sql1 = "SELECT * FROM DEPT WHERE DNAME='"+dname.toUpperCase()+"'";
		String sql2 = "SELECT EMPNO, ENAME, LOC, HIREDATE FROM EMP E,DEPT D WHERE E.DEPTNO=D.DEPTNO AND DNAME='"+dname.toUpperCase()+"'";	
	    //sql2=String.format("SELECT * FROM EMP E, DEPT D WHERE E.DEPTNO=D.DEPTNO AND DNAME='%s'",dname.toUpperCase()); <- �̷��� �ص� ��.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,"scott","tiger");
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql1);
			if(rs.next()) {
				int deptno = rs.getInt("deptno");
				String dname2 = rs.getString("dname");
				String loc = rs.getString("loc");
				System.out.println("[�μ�����] �μ���ȣ : "+deptno+" �μ��̸� : "+dname2+" �μ���ġ : "+loc);
				rs.close();
				rs=stmt.executeQuery(sql2);
				if(rs.next()) {
					do {
						int empno = rs.getInt("empno");
						String ename = rs.getString("ename");
						Date hiredate = rs.getDate("hiredate");
						String loc1 = rs.getString("loc");
						System.out.println("[�������] ��� : "+empno+" �̸� : "+ename+" �Ի��� : "+hiredate+" �μ���ġ : "+loc1);						
					}while(rs.next());
				}else {
					System.out.println(dname+"�μ��� ����� �����ϴ�.");
				}
			}else {
				System.out.println(dname+"(��)�� �̸��� �μ��� �����ϴ�.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }
		}
	}
}


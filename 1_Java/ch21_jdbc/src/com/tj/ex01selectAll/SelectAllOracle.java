package com.tj.ex01selectAll;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectAllOracle {
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		String query = "SELECT*FROM EMP";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); // 1�ܰ�.driver load
			conn = DriverManager.getConnection(url,"scott","tiger"); // 2�ܰ�. DB����
			stmt = conn.createStatement(); // 3�ܰ�.SQL���� ������ ��ü
			rs = stmt.executeQuery(query); // 4�ܰ�.SQL�� ����, 5.SQL�� ��� �ޱ�(rs)
			System.out.println("���\t�̸�\t��å\t\t�����\t�Ի���\t\t�޿�\t��\t�μ���ȣ");
			System.out.println("----------------------------------------------------------------------------------");
			if(rs.next()) { // 6�ܰ�.����� �޾� ���ϴ� ���� ���� 
				do {
					int EMPNO = rs.getInt("EMPNO");
					String ENAME = rs.getString("ENAME");
					String JOB = rs.getString("JOB");
					int MGR = rs.getInt("MGR");
					Date HIREDATE = rs.getDate("HIREDATE");
					int SAL = rs.getInt("SAL");
					int COMM = rs.getInt("COMM");
					int DEPTNO = rs.getInt("DEPTNO");
					if(JOB!=null && JOB.length()>=7) {
						System.out.printf("%d\t %s\t %s\t %d\t %TF\t %d\t %d\t %d\n",
											EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO);
					}else {
						System.out.printf("%d\t %s\t %s\t\t %d\t %TF\t %d\t %d\t %d\n",
								EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO); //rs�� �Ѹ���			
					}
				}while(rs.next());
			}else {
				System.out.println("�����Ͱ� �����ϴ�.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try { // 7�ܰ�.��������
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }
		}
	}
}
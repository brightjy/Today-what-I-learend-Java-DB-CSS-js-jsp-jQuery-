package com.tj.ex04update;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateOracle {
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.println("������ �μ� ��ȣ��?");
		int deptno = sc.nextInt();
		String selectSQL="SELECT COUNT(*) C FROM DEPT WHERE DEPTNO="+deptno; // �Է��� �μ���ȣ�� �ִ��� count (������ 1, ������0)
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSQL);
			rs.next(); // ���� �� �� ���� ������ �����;� �ϴϱ� if �� �ȿ� ���� �ʿ� ���� (���ప�� 0�̳� 1�̳ĸ� ���� ��)
			int cnt = rs.getInt("C"); // rs ù �� ° ���� ���� �����Ͷ�(�ʵ���� count(*)�� Ư�����ڰ� �ִµ�, Ư�����ڸ� getInt(count(*)) �̷������� ���� ����. �׷��� �� ������.. �Ǵ� ALIAS
			if(cnt==1) { // �μ���ȣ�� �����ϴ� ���
				System.out.print("�����ϰ��� �ϴ� �μ��̸���?");
				String dname = sc.next();
				System.out.println("�����ϰ��� �ϴ� �μ���ġ��?");
				String loc = sc.next();
				String updateSQL =String.format("UPDATE DEPT SET DNAME='%s', LOC='%s' WHERE DEPTNO=%d", dname, loc, deptno);
				int result = stmt.executeUpdate(updateSQL);
				System.out.println(result>0? deptno+"�� �μ� ���� ����" : "���� ����");
			}else { // �μ���ȣ�� ����(cnt==0)
				System.out.println("�������� ���� �μ���ȣ�Դϴ�. ������ �Ұ����մϴ�.");
			}			
		} catch (Exception e) {
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
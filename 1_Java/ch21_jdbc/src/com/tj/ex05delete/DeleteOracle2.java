package com.tj.ex05delete;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteOracle2 {
	public static void main(String[] args) {
		// 1. ������ �μ���ȣ �Է¹ޱ�
		// 2-1. �����ϴ� �μ���ȣ�� ��� : 1) ��¥ �������� �����, 2) Y �Է� �� -> �ش� �μ���ȣ ����, N �Է� �� -> ��������. ���α׷� ��.
		// 2-2. �������� �ȴ� �μ��� ��� : "�������� �ʴ� �μ���ȣ �Դϴ�." ���
		
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		System.out.println("������ �μ���ȣ��?");
		int deptno = sc.nextInt();
		String selectSQL = "SELECT COUNT(*) FROM DEPT WHERE DEPTNO="+deptno;
		String deleteSQL = String.format("DELETE FROM DEPT WHERE DEPTNO=%d",deptno);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSQL);
			rs.next();
			int cnt = rs.getInt(1);
			if(cnt==1) {
				System.out.print(deptno+"�� �μ��� ��¥ �����Ͻðڽ��ϱ�?");
				String answer = sc.next();
				if(answer.equalsIgnoreCase("y")) {				
					int result = stmt.executeUpdate(deleteSQL);
					System.out.println(result>0? deptno+"�� �μ� ���� ����" : "���� ����");					
				}else if(answer.equalsIgnoreCase("n")) {
					System.out.print("���α׷��� �����մϴ�.");
				}else {
					System.out.println("��ȿ���� ���� �Է°��Դϴ�.");
				}
			}else {
				System.out.println("�������� �ʴ� �μ���ȣ�Դϴ�. ������ �Ұ����մϴ�.");
			}
						
			} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) { }		
		
		}
	
	}
}
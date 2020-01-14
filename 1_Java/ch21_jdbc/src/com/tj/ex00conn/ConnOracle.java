package com.tj.ex00conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnOracle {
	public static void main(String[] args) {
		String driver="oracle.jdbc.driver.OracleDriver";
		String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; 
		Connection conn = null;
		try {
			// (1) 1�ܰ� : ����̹� �ε�
			Class.forName(driver);
			// (2) �����ͺ��̽��� ���� ��ü ����
			conn = DriverManager.getConnection(url,"scott","tiger");
			System.out.println("������ ���̽� ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage()+"����̹� �� ã��");
		} catch (SQLException e) {
			System.out.println(e.getMessage()+"DB���� ����");
		} finally {
			try {
				if(conn!=null) conn.close(); // ������ ���� conn=null �� �� �� �����Ƿ�. conn!=null �� ��츸 close�ǰ�
			} catch (SQLException e) {
				System.out.println(e.getMessage()+"�ݱ� ����");
			}
		}
	}
}
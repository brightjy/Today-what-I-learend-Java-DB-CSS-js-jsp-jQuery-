package com.tj.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		Scanner sc = new Scanner(System.in);
		String fn = "";
		ArrayList<StudentDTO> students = new ArrayList<StudentDTO>(); // �����ͺ��̽��� �ִ� �� arraylist�� �־ ���.
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt =null;
		ResultSet rs = null;
		String sName,mName,nameNo;
		int score,rank;
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
		do {
			System.out.print("1:�л��Է�(�̸�,�а���,����), 2:�а� �� ���(�а���), 3:�� ���, �� ��:����");			
			fn = sc.next();
			switch(fn) {
			case "1" : //�л��Է�(�̸�,�а���,����)
				String sql="INSERT INTO STUDENT (sNO,sNAME,mNO,SCORE) VALUES" + 
						"    (TO_CHAR(SYSDATE,'YYYY')" +
						"    ||(SELECT mNO FROM MAJOR WHERE mNAME=?)" +
						"	 ||TRIM(TO_CHAR(STUDENT_SQ.NEXTVAL,'000'))," + 
						"    ?,(SELECT mNO FROM MAJOR WHERE mNAME=?),?)";
				System.out.print("�Է��� �л� �̸�?");
				sName = sc.next();
				System.out.print("�а���?");
				mName = sc.next();
				System.out.print("����?");
				score = sc.nextInt();
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");	
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, mName);
					pstmt.setString(2, sName);
					pstmt.setString(3, mName);
					pstmt.setInt(4, score);
					int result = pstmt.executeUpdate();
					System.out.println(result>0 ? sName+"�Է¼���":"�Է½���");					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				} finally {
					try {
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();						
					} catch (Exception e2) {}					
				}
				break;
			case "2" : //�а��� ���(�а��� �Է¹޾� �л� ���)
				sql="SELECT ROWNUM RANK, sNAME||'('||sNO||')' NAME_NO, mNAME, SCORE" + 
						"    FROM (SELECT*FROM STUDENT S, MAJOR M WHERE S.mNO=M.mNO AND mNAME=?" + 
						"    ORDER BY SCORE DESC)"; // ������ ���� ���� �����ϱ� ���� ������ �ʿ���� �׳� ��
				System.out.print("����� �а� �̸�?");
				mName = sc.next(); //������ ���� ���� �����ϱ� ���� ������ �ʿ� ���� �׳� ��
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,mName);
					rs = pstmt.executeQuery(); // select���̴ϱ� select�� ������� rs(resultSet���� ���� ����. �ϴ� ����. query
					students.clear(); // ArrayList<StudentDTO>�� �ִ� ��(students)�� ���� size=0
					while(rs.next()) { // rs.next() �̰� �ؾ� resultSet ���� �޾ƿ��� ��.
						rank = rs.getInt("rank");
						nameNo = rs.getString("name_no"); // select���� alias 
						mName = rs.getString("mName"); // ���� �̹� ������ ��ü�� �׳� �ٷ� ���� �ᵵ ��.
						score = rs.getInt("score");	
						students.add(new StudentDTO(rank,nameNo,mName,score)); // rs.netx() �ؼ� ������ �ް�, arrayList�� add. toString �޼ҵ�� ���⼭ ����															
					}
					if(!students.isEmpty()) { // �����Ͱ� �ִ� ���. if(students.size()!=0 �̷��� �ص� ��.
						System.out.println("���\t�̸�(nameNo)\t�а�\t����");
						System.out.println("---------------------------------------");
						for(StudentDTO s : students) { // for Ȯ�屸��. s�� �ӽú���.
							System.out.println(s); //toString �Ѹ��� �� ���⼭
						}
					}else { // �����Ͱ� ���� ���
						System.out.println("�ش� �а� �л��� �����ϴ�.");
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();						
					} catch (Exception e2) {}					
				}
				break;
			case "3" : //�� ���(���� ������ ���� �л� ��)
				sql="SELECT ROWNUM RANK, sNAME||'('||sNO||')' NAME_NO, mNAME, SCORE" + 
						"    FROM (SELECT*FROM STUDENT S, MAJOR M WHERE S.mNO=M.mNO AND sEXPEL=0 ORDER BY SCORE DESC)";
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					students.clear();
					while(rs.next()) {
						rank = rs.getInt("rank");
						nameNo = rs.getString("name_no");
						mName = rs.getString("mName");
						score = rs.getInt("score");
						students.add(new StudentDTO(rank,nameNo,mName,score));
					}
					if(students.size()!=0) {
						System.out.println("���\t�̸�(nameNo)\t�а�\t����");
						System.out.println("---------------------------------------");
						for(StudentDTO s : students) {
							System.out.println(s);
						}
					}else {
						System.out.println("�л��� �����ϴ�.");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();						
					} catch (Exception e2) {}					
				}			
				break;
			}
		}while(fn.equals("1")||fn.equals("2")||fn.equals("3"));
		System.out.println("�ȳ�~");

	}
}
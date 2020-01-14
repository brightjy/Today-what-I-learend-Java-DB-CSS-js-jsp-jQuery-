package com.tj.student;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class StudentFrame extends JFrame implements ActionListener {

	private Container contenPane;
	private JPanel jpup, jpbtn;
	private JTextField txtSNo, txtSName, txtScore;
	private JTextArea txtPool;
	private JButton btnSNoSearch, btnSNameSearch, btnMNameSearch, btnInput, btnUpdate, 
			btnStudentOut, btnExpel, btnExit, btnExpelOut;
	private JScrollPane scrollPane;
	private JComboBox<String> comMname;
	private Vector<String> mNames; // ���� ������ Ȯ���� �� �ƴϴϱ� vector (������ ��� arraylist)
	private String driver;
	private String url;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	ArrayList<StudentDTO> students;
	ArrayList<StudentDTO2> students2;
	
	public StudentFrame(String title) {
		super(title);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		students = new ArrayList<StudentDTO>();
		students2 = new ArrayList<StudentDTO2>();
		driver = "oracle.jdbc.driver.OracleDriver";
		url = "jdbc:oracle:thin:@localhost:1521:xe"; 
		
		contenPane = getContentPane();
		contenPane.setLayout(new FlowLayout());
		
		jpup = new JPanel(new GridLayout(4,3));
		jpbtn = new JPanel(new FlowLayout());
		txtSNo = new JTextField(10);
		txtSName = new JTextField(10);
		txtScore = new JTextField(10);
		mNames = new Vector<String>();
		mNames.add("");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,"scott","tiger");
			pstmt = conn.prepareStatement("SELECT mNAME FROM MAJOR");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mNames.add(rs.getString("mname")); // vector�� ���� ������.
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		comMname = new JComboBox<String>(mNames);
		btnSNoSearch = new JButton("�й��˻�");
		btnSNameSearch = new JButton("�̸��˻�");
		btnMNameSearch = new JButton("�����˻�");
		btnInput = new JButton("�л��Է�");
		btnUpdate = new JButton("�л�����");
		btnStudentOut = new JButton("�л����");
		btnExpel = new JButton("����ó��");
		btnExit = new JButton("����");
		btnExpelOut= new JButton("���������");
		txtPool = new JTextArea(10,50);
		scrollPane = new JScrollPane(txtPool);

		jpup.add(new JLabel("�й�",(int)CENTER_ALIGNMENT));
		jpup.add(txtSNo);
		jpup.add(btnSNoSearch);
		jpup.add(new JLabel("�̸�",(int)CENTER_ALIGNMENT));
		jpup.add(txtSName);
		jpup.add(btnSNameSearch);
		jpup.add(new JLabel("����",(int)CENTER_ALIGNMENT));
		jpup.add(comMname);
		jpup.add(btnMNameSearch);
		jpup.add(new JLabel("����",(int)CENTER_ALIGNMENT));
		jpup.add(txtScore);
		jpbtn.add(btnInput);
		jpbtn.add(btnUpdate);
		jpbtn.add(btnStudentOut);
		jpbtn.add(btnExpel);
		jpbtn.add(btnExit);
		jpbtn.add(btnExpelOut);
		
		contenPane.add(jpup);
		contenPane.add(jpbtn);
		contenPane.add(scrollPane);
	
		setVisible(true);
		setBounds(300,300,600,400);
		
		btnSNoSearch.addActionListener(this);
		btnSNameSearch.addActionListener(this);
		btnMNameSearch.addActionListener(this);
		btnInput.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnStudentOut.addActionListener(this);
		btnExpel.addActionListener(this);
		btnExit.addActionListener(this);
		btnExpelOut.addActionListener(this);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSNoSearch) {
			String sql = "SELECT sNO, sNAME, mNAME, SCORE FROM STUDENT S, MAJOR M" + 
					     "    WHERE S.mNO=M.mNO AND sNO=?";
			String sNo = txtSNo.getText().trim(); // �ݺ��Ǵ� ���� ��ü�� ����
			if(!sNo.equals("")) { // �Է��� �й� ���� ������ �ƴϸ�,
				//�й��� �����ͼ�
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,sNo);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						txtSName.setText(rs.getString("sName"));
						comMname.setSelectedItem(rs.getString("mName"));
						txtScore.setText(rs.getString("score"));
						txtPool.setText(sNo+"�˻��Ϸ�");						
					}else {
						txtSName.setText("");
						comMname.setSelectedItem("");
						txtScore.setText("");
						txtPool.setText("���� �й��Դϴ�.");						
					}					
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}
				}	
			}else {
				txtSNo.setText("");
				comMname.setSelectedItem("");
				txtScore.setText("");
				txtPool.setText("�й��� �Է� �� �˻��ϼ���.");
			}
			
		}else if(e.getSource()==btnSNameSearch) {
			String sql = "SELECT sNO, sNAME, mNAME, SCORE, sEXPEL FROM STUDENT S, MAJOR M" + 
					     "    WHERE S.mNO=M.mNO AND sNAME=?";
			String sName = txtSName.getText().trim();
			if(!sName.equals("")) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, sName);
					rs = pstmt.executeQuery();
					students2.clear();
					while(rs.next()) {
						String sNo = rs.getString("sNo");
						String mName = rs.getString("mName");
						int score = rs.getInt("score");						
						txtSNo.setText(sNo);
						txtSName.setText(sName);
						comMname.setSelectedItem(mName);
						txtScore.setText(String.valueOf(score));
						students2.add(new StudentDTO2(sNo, sName, mName, score));
					}
					if(!students2.isEmpty()) {
						txtPool.setText("\t�й�\t�̸�\t�а���\t����\t��������\n");
						txtPool.append("\t-----------------------------------------\n");
						for(StudentDTO2 s : students2) {
							txtPool.append(s.toString()+"\n");
						}
					}else {
						txtSNo.setText("");
						comMname.setSelectedItem("");
						txtScore.setText("");
						txtPool.setText("�ش� �̸��� �л��� �����ϴ�.");
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}
				}
			}else {
				txtSNo.setText("");
				comMname.setSelectedItem("");
				txtScore.setText("");
				txtPool.setText("�̸��� �Է��ϰ� �˻��ϼ���.");
			}
		}else if(e.getSource()==btnMNameSearch) {
			String sql="SELECT ROWNUM RANK, sNAME||'('||sNO||')' sNAME_NO, mNAME||'('||mNO||')' mNAME_NO, SCORE,sEXPEL" + 
					"    FROM (SELECT S.*, mNAME FROM STUDENT S, MAJOR M WHERE S.mNO=M.mNO AND mNAME=? ORDER BY SCORE DESC)";		
			String mName = comMname.getSelectedItem().toString();
			if(!mName.contentEquals("")) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, mName);
					rs = pstmt.executeQuery();
					students.clear();
					while(rs.next()) {
						int rank = rs.getInt("rank");
						String sname_no = rs.getString("sname_no");
						String mname_no = rs.getString("mname_no");
						int score = rs.getInt("score");
						
						students.add(new StudentDTO(rank,sname_no,mname_no,score));
					}
					if(!students.isEmpty()) {
						txtPool.setText("���\t�̸�\t�а���\t����\t��������\n");
						txtPool.append("-----------------------------------------------------------------------------\n");
						for(StudentDTO s : students) {
							txtPool.append(s.toString()+"\n");
						}
					}					
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}
				}
			}else {
				System.out.println("������ �����ϰ� �˻��ϼ���.");
			}			
		}else if(e.getSource()==btnInput) {
			String sql="INSERT INTO STUDENT (sNO,sNAME,mNO,SCORE) VALUES" + 
					"    (TO_CHAR(SYSDATE,'YYYY')||(SELECT mNO FROM MAJOR WHERE mNAME=?)||TRIM(TO_CHAR(STUDENT_SQ.NEXTVAL,'000'))," + 
					"        ?,(SELECT mNO FROM MAJOR WHERE mNAME=?),?)";		
			String sName = txtSName.getText().trim();
			String mName = comMname.getSelectedItem().toString();
			if(sName.length()!=0 && mName.length()!=0) {
				int score = 0;
				try {
					score = Integer.parseInt(txtScore.getText());
					if(score<0 || score>100) {
						System.out.println("��ȿ�� ������ �ƴϸ� 0�� ó��");
						score=0;
					}
				} catch (Exception e2) {
					System.out.println("������ �Է� ���߰ų�, ���ڸ� �Է��ϸ� 0�� ó��");
				}		
			try {
				conn = DriverManager.getConnection(url,"scott","tiger");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mName);
				pstmt.setString(2, sName);
				pstmt.setString(3,mName);
				pstmt.setInt(4, score);
				int result = pstmt.executeUpdate();
				txtPool.setText("�л��Է¼���");
				comMname.setSelectedIndex(0);
				txtSName.setText("");
				txtScore.setText("");
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} finally {
				try {
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}
			}
			}
		}else if(e.getSource()==btnUpdate) {
			String sql="UPDATE STUDENT SET sName=?, mNO=(SELECT mNO FROM MAJOR WHERE mNAME=?), SCORE=? WHERE sNO=?";
			String sNo = txtSNo.getText().trim();
			String sName = txtSName.getText().trim();
			String mName = comMname.getSelectedItem().toString();
			if(sNo.length()!=0 && sName.length()!=0 && mName.length()!=0) {
				int score = 0;
				try {
					score = Integer.parseInt(txtScore.getText());
					if(score<0 || score>100) {
						System.out.println("��ȿ�� ������ �ƴϸ� 0�� ó��");
						score=0;
					}
				} catch (Exception e2) {
					System.out.println("������ �Է� ���߰ų�, ���ڸ� �Է��ϸ� 0�� ó��");
				}
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, sName);
					pstmt.setString(2, mName);
					pstmt.setInt(3, score);
					pstmt.setString(4, sNo);
					int result = pstmt.executeUpdate();
					txtPool.setText("�л���������");
					comMname.setSelectedIndex(0);
					txtSName.setText("");
					txtScore.setText("");
					txtSNo.setText("");
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}				
				}
			}else {
				txtPool.setText("������ �л��� �й��� �Է��ؾ� �մϴ�.");
			}
		}else if(e.getSource()==btnStudentOut) {
			String sql="SELECT ROWNUM RANK, sNAME||'('||sNO||')' NAME_NO, mNAME||'('||mNO||')' mNAME_NO, SCORE" + 
					"    FROM (SELECT S.*, mNAME FROM STUDENT S, MAJOR M WHERE S.mNO=M.mNO AND sEXPEL=0 ORDER BY SCORE DESC)";
			try {
				conn = DriverManager.getConnection(url,"scott","tiger");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				students.clear();
				while(rs.next()) {
					int rank = rs.getInt("rank");
					String nameNo = rs.getString("name_no");
					String mNameNo = rs.getString("mName_No");
					int score = rs.getInt("score");
					students.add(new StudentDTO(rank,nameNo,mNameNo,score));
				}
				if(!students.isEmpty()) {
					txtPool.setText("���\t�̸�\t�а���\t����\t��������\n");
					txtPool.append("\t---------------------------------------------------------------------------------------------\n");
					for(StudentDTO s : students) {
						txtPool.append(s.toString()+"\n");
				}
				}else {
					txtPool.setText("�л��� �����ϴ�.");
				}
				txtSNo.setText("");
				txtSName.setText("");
				comMname.setSelectedIndex(0);
				txtScore.setText("");
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} finally {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}				
			}			
		}else if(e.getSource()==btnExpel) {
			String sql="UPDATE STUDENT SET sEXPEL=1 WHERE SNO=?";
			String sNo = txtSNo.getText().trim();
			if(sNo.length()!=0) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, sNo);
					int result = pstmt.executeUpdate();
					if(result>0) {
						txtPool.setText("����ó�� ����");						
					}else {
						txtPool.setText("�ش� �й��� ��� ����ó�� ����");
					}
					comMname.setSelectedIndex(0);
					txtSName.setText("");
					txtScore.setText("");
					txtSNo.setText("");
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
					}				
				}		
			}else {
				txtPool.setText("����ó�� �� �л��� �й��� �Է��ؾ� �մϴ�.");
			}						
		}else if(e.getSource()==btnExpelOut) {
			String sql="SELECT ROWNUM RANK, sNAME||'('||sNO||')' NAME_NO, mNAME||'('||mNO||')' mNAME_NO, SCORE" + 
					"    FROM (SELECT S.*, mNAME FROM STUDENT S, MAJOR M WHERE S.mNO=M.mNO AND sEXPEL=1 ORDER BY SCORE DESC)";
			try {
				conn = DriverManager.getConnection(url,"scott","tiger");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				students.clear();
				while(rs.next()) {
					int rank = rs.getInt("rank");
					String nameNo = rs.getString("name_no");
					String mNameNo = rs.getString("mName_No");
					int score = rs.getInt("score");
					students.add(new StudentDTO(rank,nameNo,mNameNo,score));
				}
				if(!students.isEmpty()) {
					txtPool.setText("���\t�̸�\t�а���\t����\t��������\n");
					txtPool.append("\t---------------------------------------------------------------------------------------------\n");
					for(StudentDTO s : students) {
						txtPool.append(s.toString()+"\n");
				}
				}else {
					txtPool.setText("�������� �л��� �����ϴ�.");
				}
				txtSNo.setText("");
				txtSName.setText("");
				comMname.setSelectedIndex(0);
				txtScore.setText("");
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} finally {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
				}				
			}	
		}else if(e.getSource()==btnExit) {
			setVisible(false);
			dispose();
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		new StudentFrame("�л�����");
	}

}
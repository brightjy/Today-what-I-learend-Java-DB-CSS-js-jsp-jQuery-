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
	private Vector<String> mNames; // 전공 과목이 확정된 게 아니니까 vector (고정된 경우 arraylist)
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
				mNames.add(rs.getString("mname")); // vector에 값을 가져옴.
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
		btnSNoSearch = new JButton("학번검색");
		btnSNameSearch = new JButton("이름검색");
		btnMNameSearch = new JButton("전공검색");
		btnInput = new JButton("학생입력");
		btnUpdate = new JButton("학생수정");
		btnStudentOut = new JButton("학생출력");
		btnExpel = new JButton("제적처리");
		btnExit = new JButton("종료");
		btnExpelOut= new JButton("제적자출력");
		txtPool = new JTextArea(10,50);
		scrollPane = new JScrollPane(txtPool);

		jpup.add(new JLabel("학번",(int)CENTER_ALIGNMENT));
		jpup.add(txtSNo);
		jpup.add(btnSNoSearch);
		jpup.add(new JLabel("이름",(int)CENTER_ALIGNMENT));
		jpup.add(txtSName);
		jpup.add(btnSNameSearch);
		jpup.add(new JLabel("전공",(int)CENTER_ALIGNMENT));
		jpup.add(comMname);
		jpup.add(btnMNameSearch);
		jpup.add(new JLabel("점수",(int)CENTER_ALIGNMENT));
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
			String sNo = txtSNo.getText().trim(); // 반복되는 것은 객체로 선언
			if(!sNo.equals("")) { // 입력한 학번 값이 공백이 아니면,
				//학번을 가져와서
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,sNo);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						txtSName.setText(rs.getString("sName"));
						comMname.setSelectedItem(rs.getString("mName"));
						txtScore.setText(rs.getString("score"));
						txtPool.setText(sNo+"검색완료");						
					}else {
						txtSName.setText("");
						comMname.setSelectedItem("");
						txtScore.setText("");
						txtPool.setText("없는 학번입니다.");						
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
				txtPool.setText("학번을 입력 후 검색하세요.");
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
						txtPool.setText("\t학번\t이름\t학과명\t점수\t제적여부\n");
						txtPool.append("\t-----------------------------------------\n");
						for(StudentDTO2 s : students2) {
							txtPool.append(s.toString()+"\n");
						}
					}else {
						txtSNo.setText("");
						comMname.setSelectedItem("");
						txtScore.setText("");
						txtPool.setText("해당 이름의 학생이 없습니다.");
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
				txtPool.setText("이름을 입력하고 검색하세요.");
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
						txtPool.setText("등수\t이름\t학과명\t점수\t재적여부\n");
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
				System.out.println("전공을 선택하고 검색하세요.");
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
						System.out.println("유효한 점수가 아니면 0점 처리");
						score=0;
					}
				} catch (Exception e2) {
					System.out.println("점수를 입력 안했거나, 문자를 입력하면 0점 처리");
				}		
			try {
				conn = DriverManager.getConnection(url,"scott","tiger");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, mName);
				pstmt.setString(2, sName);
				pstmt.setString(3,mName);
				pstmt.setInt(4, score);
				int result = pstmt.executeUpdate();
				txtPool.setText("학생입력성공");
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
						System.out.println("유효한 점수가 아니면 0점 처리");
						score=0;
					}
				} catch (Exception e2) {
					System.out.println("점수를 입력 안했거나, 문자를 입력하면 0점 처리");
				}
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, sName);
					pstmt.setString(2, mName);
					pstmt.setInt(3, score);
					pstmt.setString(4, sNo);
					int result = pstmt.executeUpdate();
					txtPool.setText("학생수정성공");
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
				txtPool.setText("수정할 학생의 학번은 입력해야 합니다.");
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
					txtPool.setText("등수\t이름\t학과명\t점수\t재적여부\n");
					txtPool.append("\t---------------------------------------------------------------------------------------------\n");
					for(StudentDTO s : students) {
						txtPool.append(s.toString()+"\n");
				}
				}else {
					txtPool.setText("학생이 없습니다.");
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
						txtPool.setText("제적처리 성공");						
					}else {
						txtPool.setText("해당 학번이 없어서 제적처리 실패");
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
				txtPool.setText("제적처리 할 학생의 학번을 입력해야 합니다.");
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
					txtPool.setText("등수\t이름\t학과명\t점수\t재적여부\n");
					txtPool.append("\t---------------------------------------------------------------------------------------------\n");
					for(StudentDTO s : students) {
						txtPool.append(s.toString()+"\n");
				}
				}else {
					txtPool.setText("제적당한 학생이 없습니다.");
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
		new StudentFrame("학생관리");
	}

}

package com.tj.supermarket;

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
	
	public class CustomerFrame extends JFrame implements ActionListener{
	//Swing
	private Container container;
	private JPanel jpup, jpbtn;
	private JTextField jtxtId, jtxtTel, jtxtName, jtxtPoint, jtxtAmount;
	private Vector<String> levels;
	private JComboBox<String> jcomLevel;
	private JButton jbtnTelSearch, jbtnNameSearch, jbtnPoint;
	private JButton jbtnBuy, jbtnLevelOutput, jbtnAllOutput, 
	jbtnInsert, jbtnTelUpdate, jbtnDelete, jbtnExit;
	private JTextArea jtxtPool;
	private JScrollPane scrollPane;
	
	private String driver;
	private String url;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	ArrayList<CustomerDTO> customers;

	
	public CustomerFrame(String title) {
	super(title);
	
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	customers = new ArrayList<CustomerDTO>();	
	driver = "oracle.jdbc.driver.OracleDriver";
	url = "jdbc:oracle:thin:@localhost:1521:xe"; 
	
	container = getContentPane();
	container.setLayout(new FlowLayout());
	jpup = new JPanel(new GridLayout(6, 3));
	jpbtn = new JPanel();
	jtxtId = new JTextField(20);
	jtxtTel = new JTextField(20);
	jtxtName = new JTextField(20);
	jtxtPoint = new JTextField(20);
	jtxtAmount = new JTextField(20);
	levels = new Vector<String>();
	levels.add("");
	
	try {
		Class.forName(driver);
		conn = DriverManager.getConnection(url,"scott","tiger");
		pstmt = conn.prepareStatement("SELECT LEVEL_NAME FROM CUS_LEVEL");
		rs = pstmt.executeQuery();
		while(rs.next()) {
			levels.add(rs.getString("level_name"));
		}
	} catch (ClassNotFoundException e) {
		System.out.println(e.getMessage());
	} catch (SQLException e) {
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
	
	jcomLevel = new JComboBox<String>(levels);
	jbtnTelSearch = new JButton("��4�ڸ�(FULL) �˻�");
	jbtnNameSearch = new JButton("���� �̸� �˻�");
	jbtnPoint = new JButton("����Ʈ�θ� ����");
	jpup.add(new JLabel(" �� �� �� ",(int) CENTER_ALIGNMENT));
	jpup.add(jtxtId);
	jpup.add(new JLabel("")); // ���� Ȯ���� ���� �� �� �߰� 
	jpup.add(new JLabel("�� �� �� ȭ",(int) CENTER_ALIGNMENT));
	jpup.add(jtxtTel);
	jpup.add(jbtnTelSearch);
	jpup.add(new JLabel("�� �� �� ��",(int) CENTER_ALIGNMENT));
	jpup.add(jtxtName);
	jpup.add(jbtnNameSearch);
	jpup.add(new JLabel("��  ��  Ʈ",(int) CENTER_ALIGNMENT));
	jpup.add(jtxtPoint);
	jpup.add(jbtnPoint);
	jpup.add(new JLabel("�� �� �� ��",(int) CENTER_ALIGNMENT));
	jpup.add(jtxtAmount);
	jpup.add(new JLabel(""));//�� �� �߰��ϴ� �κ�
	jpup.add(new JLabel("�� �� �� ��",(int) CENTER_ALIGNMENT));
	jpup.add(jcomLevel);
	jbtnBuy = new JButton("��ǰ ����");
	jbtnLevelOutput = new JButton("��޺����");
	jbtnAllOutput = new JButton("��ü���");
	jbtnInsert = new JButton("ȸ������");
	jbtnTelUpdate = new JButton("��ȣ����");
	jbtnDelete = new JButton("ȸ��Ż��");
	jbtnExit = new JButton("������");
	jpbtn.add(jbtnBuy);
	jpbtn.add(jbtnLevelOutput);
	jpbtn.add(jbtnAllOutput);
	jpbtn.add(jbtnInsert);
	jpbtn.add(jbtnTelUpdate);
	jpbtn.add(jbtnDelete);
	jpbtn.add(jbtnExit);
	jtxtPool = new JTextArea(6, 60);
	scrollPane = new JScrollPane(jtxtPool);
	container.add(jpup);
	container.add(jpbtn);
	container.add(scrollPane);
	setSize(new Dimension(750, 385));setLocation(200, 200);
	setVisible(true);
	jtxtPool.setText("\t�� �� �� �����˻� �� �����ϼ��� �� �� ��");
	
	jbtnTelSearch.addActionListener(this);
	jbtnNameSearch.addActionListener(this);
	jbtnPoint.addActionListener(this);
	jbtnBuy.addActionListener(this);
	jbtnLevelOutput.addActionListener(this);
	jbtnAllOutput.addActionListener(this);
	jbtnInsert.addActionListener(this);
	jbtnTelUpdate.addActionListener(this);
	jbtnDelete.addActionListener(this);
	jbtnExit.addActionListener(this);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jbtnTelSearch) {
			String sql ="SELECT CID, CTEL, CNAME, CPOINT, CCOST, LEVEL_NAME, " + 
					"	NVL((SELECT LEVEL_HICOST-CCOST+1 FROM CUSTOMER WHERE C.LEVEL_CODE<>3 AND CID=C.CID),0) SMM" + 
					"    FROM CUS_LEVEL L, CUSTOMER C" + 
					"    WHERE L.LEVEL_CODE=C.LEVEL_CODE AND CTEL LIKE '%'||?";
			String cTel = jtxtTel.getText().trim();
			if(cTel.length()>=4) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, cTel);
					rs = pstmt.executeQuery();
					customers.clear();
					while(rs.next()) {
						int cid = rs.getInt("cid");
						String ctel = rs.getString("ctel");
						String cname = rs.getString("cname");
						int cpoint = rs.getInt("cpoint");
						int ccost = rs.getInt("ccost");
						int smm = rs.getInt("smm");
						String level_name = rs.getString("level_name");
						
						jtxtId.setText(String.valueOf(cid));
						jtxtName.setText(cname);
						jtxtTel.setText(ctel);
						jtxtPoint.setText(String.valueOf(cpoint));
						jcomLevel.setSelectedItem(level_name);						
						customers.add(new CustomerDTO(ctel, cname,cpoint,ccost,level_name,smm));
					}
					if(!customers.isEmpty()){
						jtxtPool.setText("��ȭ\t�̸�\t����Ʈ\t���Ŵ�����\t��������\t�������� ���� ������ ������ �ݾ�\n");
						jtxtPool.append("-------------------------------------------------------------------------------------------------------------\n");
						for(CustomerDTO c : customers) {
							jtxtPool.append(c.toString()+"\n");
						}
					}
					else {
						jtxtId.setText("");
						jtxtName.setText("");
						jtxtPoint.setText("");
						jcomLevel.setSelectedIndex(0);
						jtxtPool.setText("���� ��ȭ��ȣ�Դϴ�.");
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
				jtxtId.setText("");
				jtxtName.setText("");
				jtxtPoint.setText("");
				jcomLevel.setSelectedIndex(0);
				jtxtPool.setText("��ȭ��ȣ �Է� �� �˻��ϼ���.");
			}
			
		}else if(e.getSource()==jbtnNameSearch) {
			String sql = "SELECT CID, CTEL, CNAME, CPOINT, CCOST, LEVEL_NAME," + 
					"	NVL((SELECT LEVEL_HICOST-CCOST+1 FROM CUSTOMER WHERE C.LEVEL_CODE<>3 AND CID=C.CID),0) SMM" + 
					"    FROM CUS_LEVEL L, CUSTOMER C" + 
					"    WHERE L.LEVEL_CODE=C.LEVEL_CODE AND CNAME=?";
			String cname = jtxtName.getText().trim();
			if(cname.length()!=0) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, cname);
					rs = pstmt.executeQuery();
					customers.clear();
					while(rs.next()) {
						int cid = rs.getInt("cid");
						String ctel = rs.getString("ctel");
						cname = rs.getString("cname");
						int cpoint = rs.getInt("cpoint");
						int ccost = rs.getInt("ccost");
						String level_name = rs.getString("level_name");
						int smm = rs.getInt("smm");
						
						jtxtId.setText(String.valueOf(cid));
						jtxtTel.setText(ctel);
						jtxtName.setText(cname);
						jtxtPoint.setText(String.valueOf(cpoint));
						jcomLevel.setSelectedItem(level_name);
						customers.add(new CustomerDTO(ctel,cname,cpoint,ccost,level_name,smm));
					}
					if(!customers.isEmpty()) {
						jtxtPool.setText("��ȭ\t�̸�\t����Ʈ\t���Ŵ�����\t��������\t�������� ���� ������ ������ �ݾ�\n");
						jtxtPool.append("-------------------------------------------------------------------------------------------------------------\n");;
						for(CustomerDTO c : customers) {
							jtxtPool.append(c.toString()+"\n");
						}
					}else {
						jtxtId.setText("");
						jtxtName.setText("");
						jtxtPoint.setText("");
						jcomLevel.setSelectedIndex(0);
						jtxtPool.setText("���� �̸��Դϴ�.");
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage()+1);
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage()+2);
					}
				}					
			}else {
				jtxtId.setText("");
				jtxtName.setText("");
				jtxtPoint.setText("");
				jcomLevel.setSelectedIndex(0);
				jtxtPool.setText("�̸� �Է� �� �˻��ϼ���.");
			}
						
		}else if(e.getSource()==jbtnPoint) {
			String sql="UPDATE CUSTOMER SET CPOINT=CPOINT-? WHERE CID=?";	
			String cid = jtxtId.getText();
			int cpoint = Integer.parseInt(jtxtPoint.getText().trim());
				if (cid.length()!=0) {
					int amount = 0;
					amount = Integer.parseInt(jtxtAmount.getText().trim());
					if(cpoint-amount>=0) {
					try {
						conn = DriverManager.getConnection(url,"scott","tiger");
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1,amount);
						pstmt.setString(2, cid);						
						int result = pstmt.executeUpdate();
						if(result>0) {
							jtxtPool.setText("����Ʈ�θ� ���� ����");
							jtxtPoint.setText(String.valueOf(cpoint-amount));
							jtxtAmount.setText("");														
						}else {
							jtxtPool.setText("����Ʈ�� ���Ž���");
						}
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					} finally {
						try {
							if(pstmt!=null) pstmt.close();
							if(conn!=null) conn.close();
						} catch (Exception e2) {
							System.out.println(e2.getMessage()+2);
						}
					}						
				}else {
					jtxtPool.setText("����Ʈ�� �����ؼ� ����Ʈ ������ �Ұ��ؿ�.");
				}
			}else {
				jtxtPool.setText("���̵� or �� ��ȣ or �̸� ������ ���� �˻� ���� ���ּ���.");
			}
				
		}else if(e.getSource()==jbtnBuy) {
			String sql = "UPDATE CUSTOMER SET CCOST=CCOST+?, CPOINT=CPOINT+?*0.5," + 
					"    LEVEL_CODE=(SELECT L.LEVEL_CODE FROM CUS_LEVEL L, CUSTOMER C WHERE C.CCOST+? BETWEEN LEVEL_LOCOST AND LEVEL_HICOST AND CID=?)" + 
					"    WHERE CID=?";
			String cid = jtxtId.getText();
			if(cid.length()!=0) {
				int amount =0;
				amount = Integer.parseInt(jtxtAmount.getText().trim());
				if(amount>0) {
					try {
						conn = DriverManager.getConnection(url,"scott","tiger");
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, amount);
						pstmt.setInt(2, amount);
						pstmt.setInt(3, amount);
						pstmt.setString(4, cid);
						pstmt.setString(5, cid);
						int result = pstmt.executeUpdate();
						if(result>0) {
							jtxtPool.setText("��ǰ���� ����, �ʿ� �� ���� ������ ����");
							jtxtAmount.setText("");						
						}else {
							jtxtPool.setText("��ǰ���� ����");
						}
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
				} else {
					jtxtPool.setText("���� �ݾ��� �Է����ּ���.");
				}
			}else {
				jtxtPool.setText("���̵� or �� ��ȣ or �̸� ������ ���� �˻� ���� ���ּ���.");
			}
			
		}else if(e.getSource()==jbtnLevelOutput) {
			String sql="SELECT CID, CTEL, CNAME, CPOINT, CCOST, LEVEL_NAME," + 
					"	NVL((SELECT LEVEL_HICOST-CCOST+1 FROM CUSTOMER WHERE C.LEVEL_CODE<>3 AND CID=C.CID),0) SMM" + 
					"    FROM CUS_LEVEL L, CUSTOMER C" + 
					"    WHERE L.LEVEL_CODE=C.LEVEL_CODE AND C.LEVEL_CODE = (SELECT LEVEL_CODE FROM CUS_LEVEL WHERE LEVEL_NAME=?)";
			String level_name = jcomLevel.getSelectedItem().toString();
			if(!level_name.contentEquals("")) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, level_name);
					rs = pstmt.executeQuery();
					customers.clear();
					while(rs.next()) {
						int cid = rs.getInt("cid");
						String ctel = rs.getString("ctel");
						String cname = rs.getString("cname");
						int cpoint = rs.getInt("cpoint");
						int ccost = rs.getInt("ccost");
						level_name = rs.getString("level_name");
						int smm = rs.getInt("smm");						
						customers.add(new CustomerDTO(ctel,cname,cpoint,ccost,level_name,smm));
					}
					if(!customers.isEmpty()) {
						jtxtPool.setText("��ȭ\t�̸�\t����Ʈ\t���Ŵ�����\t��������\t�������� ���� ������ ������ �ݾ�\n");
						jtxtPool.append("-------------------------------------------------------------------------------------------------------------\n");;
						for(CustomerDTO c : customers) {
							jtxtPool.append(c.toString()+"\n");
						}
					}else {
						jtxtId.setText("");
						jtxtName.setText("");
						jtxtPoint.setText("");
						jcomLevel.setSelectedIndex(0);
						jtxtPool.setText("�ش� ����� ������ �����ϴ�.");
					}										
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage()+2);
					}
				}		
			}else {
				jtxtPool.setText("����� ���� �� ��ȸ�ϼ���.");
			}
		}else if(e.getSource()==jbtnAllOutput) {
			String sql="SELECT CID, CTEL, CNAME, CPOINT, CCOST, LEVEL_NAME," + 
					"	NVL((SELECT LEVEL_HICOST-CCOST+1 FROM CUSTOMER WHERE C.LEVEL_CODE<>3 AND CID=C.CID),0) SMM" + 
					"    FROM CUS_LEVEL L, CUSTOMER C" + 
					"    WHERE L.LEVEL_CODE=C.LEVEL_CODE" + 
					"    ORDER BY CCOST DESC";
			try {
				conn = DriverManager.getConnection(url,"scott","tiger");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				customers.clear();
				while(rs.next()) {
					String ctel = rs.getString("ctel");
					String cname = rs.getString("cname");
					int cpoint = rs.getInt("cpoint");
					int ccost = rs.getInt("ccost");
					String level_name = rs.getString("level_name");
					int smm = rs.getInt("smm");						
					customers.add(new CustomerDTO(ctel,cname,cpoint,ccost,level_name,smm));
				}
				if(!customers.isEmpty()) {
					jtxtPool.setText("��ȭ\t�̸�\t����Ʈ\t���Ŵ�����\t��������\t�������� ���� ������ ������ �ݾ�\n");
					jtxtPool.append("-------------------------------------------------------------------------------------------------------------\n");;
					for(CustomerDTO c : customers) {
						jtxtPool.append(c.toString()+"\n");
					}
				}else {
					jtxtPool.setText("ȸ���� �ƹ��� �����ϴ�.");
				}
			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} finally {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
				} catch (Exception e2) {
					System.out.println(e2.getMessage()+2);
				}
			}				
		}else if(e.getSource()==jbtnInsert) {
			String sql = "INSERT INTO CUSTOMER (CID, CTEL, CNAME) VALUES (CUS_SQ.NEXTVAL, ?, ?)";
			String ctel = jtxtTel.getText().trim();
			String cname = jtxtName.getText().trim();
			if(ctel.length()!=0 && cname.length()!=0) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, ctel);
					pstmt.setString(2, cname);
					int result = pstmt.executeUpdate();
					if(result>0) {
						jtxtPoint.setText("1000");
						jtxtPool.setText("ȸ������ �����մϴ�.����Ʈ 1000���� ������ �帳�ϴ�.");					
					} else {
						jtxtPool.setText("ȸ�����Կ� �����߽��ϴ�.");
					}
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage()+2);
					}
				}
			}else {
				jtxtName.setText("");
				jtxtTel.setText("");
				jtxtPoint.setText("");
				jcomLevel.setSelectedIndex(0);
				jtxtPool.setText("��ȭ��ȣ�� �̸��� �Է��ؾ� ȸ�������� �����ؿ�.");
			}
		
		}else if(e.getSource()==jbtnTelUpdate) {
			String sql = "UPDATE CUSTOMER SET CTEL=? WHERE CID=?";
			String cid = jtxtId.getText().trim();
			String ctel = jtxtTel.getText().trim();
			if(cid.length()!=0 && ctel.indexOf('-')>=2 && ctel.lastIndexOf('-')>=6) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, ctel);
					pstmt.setString(2, cid);
					int result = pstmt.executeUpdate();
					if(result>0) {
						jtxtPool.setText("��ȣ ������ �Ϸ�Ǿ����ϴ�.");
					}else {
						jtxtPool.setText("��ȣ ������ ���еǾ����ϴ�.");
					}		
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage()+2);
					}
				}		
			}else {
				jtxtPool.setText("�ùٸ� ��ȭ��ȣ�� �Է� �� �����ϼ���.");
			}
		}else if(e.getSource()==jbtnDelete) {
			String sql="UPDATE CUSTOMER SET CDELETE=? WHERE CID=?";
			String ctel = jtxtTel.getText();
			String cid = jtxtId.getText();
			if(ctel.length()!=0) {
				try {
					conn = DriverManager.getConnection(url,"scott","tiger");
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1,1);
					pstmt.setString(2,cid);
					int result = pstmt.executeUpdate();
					if(result>0) {
						jtxtPool.setText(ctel+"�� ȸ�� Ż�� �Ϸ�Ǿ����ϴ�.");						
					}else {
						jtxtPool.setText("Ż�����");
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				} finally {
					try {
						if(rs!=null) rs.close();
						if(pstmt!=null) pstmt.close();
						if(conn!=null) conn.close();
					} catch (Exception e2) {
						System.out.println(e2.getMessage()+2);
					}
				}						
			}else {
				jtxtId.setText("");
				jtxtTel.setText("");
				jtxtName.setText("");
				jtxtPoint.setText("");
				jcomLevel.setSelectedItem("");
				jtxtPool.setText("������ȭ ��ȣ �Է� �� Ż��ó�� �����մϴ�.");
			}						
		}else if(e.getSource()==jbtnExit) {
			setVisible(false);
			dispose();
			System.exit(0);
		}

	}
	public static void main(String[] args) {
		new CustomerFrame("��������");
	}
	}

package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import quanlythuvien.connect.MyConnectDB;

public class Login extends JFrame implements ActionListener, KeyListener{
	private JLabel labelTitle, labelUser, labelPassword;
	private JPasswordField passField;
	private JTextField tfUser;
	private JButton btnLogin, btnCancel, btnDangXuat;
	private String tenTK = "", matKhau = "", loaiTK = "", dangXuat = "";
	private MyConnectDB myConn;
	private MainDG mainDG = null;
	private MainNhanVien mainNV = null;
	private MainAdmin mainAd = null;
	private Main main = null;
	
	public Login() {
		myConn = new MyConnectDB();
		
		setLayout(new BorderLayout(10, 10));
		setSize(320, 380);
		setTitle("Login");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createMainPanel());
		setVisible(true);
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(10, 0, 0, 0));
		panel.add(createTitlePanel(), BorderLayout.PAGE_START);
		panel.add(createLoginPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		labelTitle = new JLabel("Hệ thống quản lý thư viện");
		labelTitle.setFont(new Font("Caribli", Font.BOLD, 18));
		labelTitle.setForeground(Color.YELLOW);
		panel.add(labelTitle);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JPanel createLoginPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
		panel.add(createLogin());
		panel.add(createButLogin());
		
		return panel;
	}
	
	private JPanel createLogin() {
		JPanel panel1 = new JPanel(new BorderLayout(10, 10));
		panel1.setBorder(new EmptyBorder(40, 20, 15, 20));
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new TitledBorder(null, "Tài khoản đăng nhập", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Caribli", Font.ITALIC, 16)));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 5, 5));
		panelL.add(labelUser = new JLabel("Tên đăng nhập"));
		panelL.add(labelPassword = new JLabel("Mật khẩu"));
		
		JPanel panelR = new JPanel(new GridLayout(2, 1, 5, 5));
		panelR.add(tfUser = new JTextField());
		panelR.add(passField = new JPasswordField()); 
		passField.addKeyListener(this);
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		
		panel1.add(panel);
		return panel1;
	}
	private JPanel createButLogin() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 5));
		panel.setBorder(new EmptyBorder(15, 25, 100, 25));
		panel.add(btnLogin = createButton("Đăng nhập"));
		btnLogin.setIcon(new ImageIcon(this.getClass().getResource("/login.png")));
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/huy.png")));
		
		return panel;
	}
	
	private JButton createButton(String name) {
		JButton btn = new JButton(name);
		btn.addActionListener(this);
		return btn;
	}
	
	private boolean checkTK(String user, String pw) {
		ResultSet rs = myConn.getDataID("TaiKhoan", "idTaiKhoan", "All", "");
		try {
			while(rs.next()) {
				tenTK = rs.getString(1);
				matKhau = rs.getString(2);
				loaiTK = rs.getString(3);
				if(tenTK.equalsIgnoreCase(user) && matKhau.equalsIgnoreCase(pw)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	private void cancel() {
		tfUser.setText("");
		passField.setText("");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogin) {
			if(tfUser.getText().equals("") || passField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Nhập tài khoản và mật khẩu để đăng nhập", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(checkTK(tfUser.getText().trim(), passField.getText().trim())) {
				cancel();
				if(loaiTK.equalsIgnoreCase("dg")) {
					mainDG = new MainDG(tenTK, loaiTK, myConn);
					btnDangXuat = mainDG.getDoiMK().getBtnDangXuat();
					btnDangXuat.addActionListener(this);
				}else if(loaiTK.equalsIgnoreCase("nv")) {
					mainNV = new MainNhanVien(tenTK, loaiTK, myConn);
					btnDangXuat = mainNV.getDoiMK().getBtnDangXuat();
					btnDangXuat.addActionListener(this);
				}else if(loaiTK.equalsIgnoreCase("admin")) {
					mainAd = new MainAdmin(myConn);
				}else if(loaiTK.equalsIgnoreCase("main")) {
					main = new Main(myConn);
				}
				setVisible(false);
			}else {
				JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			return;
		}
		if(e.getSource() == btnCancel) {
			cancel();
			return;
		}
		if(e.getSource() == btnDangXuat) {
			if(mainDG != null) mainDG.dispose();
			if(mainNV != null) mainNV.dispose();
			setVisible(true);
			
		}
		
	}
	
	public static void main(String[] args) {
		new Login();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(tfUser.getText().equals("") || passField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Nhập tài khoản và mật khẩu để đăng nhập", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(checkTK(tfUser.getText().trim(), passField.getText().trim())) {
				cancel();
				setVisible(false);
				if(loaiTK.equalsIgnoreCase("dg")) {
					mainDG = new MainDG(tenTK, loaiTK, myConn);
					btnDangXuat = mainDG.getDoiMK().getBtnDangXuat();
					btnDangXuat.addActionListener(this);
				}else if(loaiTK.equalsIgnoreCase("nv")) {
					mainNV = new MainNhanVien(tenTK, loaiTK, myConn);
					btnDangXuat = mainNV.getDoiMK().getBtnDangXuat();
					btnDangXuat.addActionListener(this);
				}else if(loaiTK.equalsIgnoreCase("admin")) {
					mainAd = new MainAdmin(myConn);
				}else if(loaiTK.equalsIgnoreCase("main")) {
					main = new Main(myConn);
				}
			}else {
				JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE, null);
			}
			return;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

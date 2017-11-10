package quanlythuvien.frame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import quanlythuvien.connect.MyConnectDB;

public class MainAdmin extends JFrame {
	private QuanLyNhanVien qlNV;
	private ThemTKDocGia qlTK;
	private MyConnectDB myConn;
	JTabbedPane tabbedPane;
	
	public MainAdmin(MyConnectDB myConn) {
		this.myConn = myConn;
		
		createGUI();
		setDisplay();
	}
	
	private void setDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 720);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createGUI() {
		add(tabbedPane = createTabbedPane());
		tabbedPane.setVisible(true);
	}
	
	private JTabbedPane createTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		qlNV = new QuanLyNhanVien(myConn);
		tabbedPane.addTab("Quản Lý Nhân Viên", new ImageIcon(this.getClass().getResource("/per.png")), qlNV);
		qlTK = new ThemTKDocGia(myConn);
		tabbedPane.addTab("Quản lý tài khoản", null, qlTK);
		
		return tabbedPane;
	}
	
}

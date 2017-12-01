package quanlythuvien.frame;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import quanlythuvien.connect.MyConnectDB;

public class MainAdmin extends JFrame {
	private QuanLyNhanVien qlNV;
	private ThemTKDocGia qlTK;
	private DoiMatKhau doiMK;
	private MyConnectDB myConn;
	JTabbedPane tabbedPane;
	private String tenTk = "", loaiTK = "";

	public MainAdmin(String tenTK, String loaiTK, MyConnectDB myConn) {
		this.tenTk = tenTK;
		this.loaiTK = loaiTK;
		this.myConn = myConn;

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createGUI();
		setDisplay();
	}

	private void setDisplay() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/iconF.png")));
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
		qlTK = new ThemTKDocGia(myConn, "nv");
		tabbedPane.addTab("Quản lý tài khoản NV", new ImageIcon(this.getClass().getResource("/account.png")), qlTK);
		doiMK = new DoiMatKhau(tenTk, loaiTK, myConn);
		tabbedPane.addTab("Đổi mật khẩu & đăng xuất", new ImageIcon(this.getClass().getResource("/key.png")), doiMK);

		return tabbedPane;
	}

	public DoiMatKhau getDoiMK() {
		return doiMK;
	}

	public void setDoiMK(DoiMatKhau doiMK) {
		this.doiMK = doiMK;
	}

}

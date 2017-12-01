package quanlythuvien.frame;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import quanlythuvien.connect.MyConnectDB;

public class MainNhanVien extends JFrame {
	private QuanLySach qlSach;
	private QuanLyDocGia qlDG;
	private QuanLyMuonTra qlMT;
	private ThemTKDocGia qlTK;
	private DoiMatKhau doiMK;
	private String tenTk = "", loaiTK = "";
	private MyConnectDB myConn;
	JTabbedPane tabbedPane;

	public MainNhanVien(String tenTK, String loaiTK, MyConnectDB myConn) {
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
		qlSach = new QuanLySach(myConn);
		tabbedPane.addTab("Quản Lý Sách", new ImageIcon(this.getClass().getResource("/book.png")), qlSach);
		qlDG = new QuanLyDocGia(myConn);
		tabbedPane.addTab("Quản Lý Độc Giả", new ImageIcon(this.getClass().getResource("/per1.png")), qlDG);
		qlMT = new QuanLyMuonTra(myConn);
		tabbedPane.addTab("Quản Lý Mượn Trả", new ImageIcon(this.getClass().getResource("/book1.png")), qlMT);
		qlTK = new ThemTKDocGia(myConn, "dg");
		tabbedPane.addTab("Quản lý tài khoản ĐG", new ImageIcon(this.getClass().getResource("/account.png")), qlTK);
		doiMK = new DoiMatKhau(tenTk, loaiTK, myConn);
		tabbedPane.addTab("Đổi mật khẩu & Đăng xuất", new ImageIcon(this.getClass().getResource("/key.png")), doiMK);

		return tabbedPane;
	}

	public DoiMatKhau getDoiMK() {
		return doiMK;
	}

	public void setDoiMK(DoiMatKhau doiMK) {
		this.doiMK = doiMK;
	}

}

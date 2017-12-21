package quanlythuvien.frame;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import quanlythuvien.connect.MyConnectDB;

public class Main extends JFrame {

	private QuanLySach qlS;
	private QuanLyDocGia qlDG;
	private QuanLyNhanVien qlNV;
	private QuanLyMuonTra qlMT;
	private MyConnectDB myConn;
	JTabbedPane tabbedPane;

	public Main(MyConnectDB myConn) {
		this.myConn = myConn;
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
		qlS = new QuanLySach(myConn);
		tabbedPane.addTab("Quản Lý Sách", new ImageIcon(this.getClass().getResource("/book.png")), qlS);
		qlDG = new QuanLyDocGia(myConn);
		tabbedPane.addTab("Quản Lý Độc Giả", new ImageIcon(this.getClass().getResource("/per1.png")), qlDG);
		qlNV = new QuanLyNhanVien(myConn);
		tabbedPane.addTab("Quản Lý Nhân Viên", new ImageIcon(this.getClass().getResource("/per.png")), qlNV);
		qlMT = new QuanLyMuonTra(myConn);
		tabbedPane.addTab("Quản Lý Mượn Trả", new ImageIcon(this.getClass().getResource("/book1.png")), qlMT);

		return tabbedPane;
	}

}

package quanlythuvien.frame;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import quanlythuvien.connect.MyConnectDB;

public class MainDG extends JFrame {
	private QuanLySach qlSach;
	private DoiMatKhau doiMK;
	private MyConnectDB myConn;
	private String tenTK = "", loaiTK = "";
	JTabbedPane tabbedPane;

	public MainDG(String tenTK, String loaiTK, MyConnectDB myConn) {
		this.tenTK = tenTK;
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
		qlSach.getInputPanelL().setVisible(false);
		qlSach.getInputPanelR().setVisible(false);
		qlSach.getBtnOtherPanel().setVisible(false);
		qlSach.getThongKeCB().setVisible(false);
		qlSach.getBtnThongKe().setVisible(false);
		tabbedPane.addTab("Thông tin sách", new ImageIcon(this.getClass().getResource("/book.png")), qlSach);
		doiMK = new DoiMatKhau(tenTK, loaiTK, myConn);
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

package quanlythuvien.frame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Main extends JFrame{

	private QuanLySach qlS;
	private QuanLyDocGia qlDG;
	private QuanLyNhanVien qlNV;
	private QuanLyMuonTra qlMT;
	
	
	public Main() {
		setDisplay();
		createGUI();
		
	}
	
	private void setDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 720);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createGUI() {
		add(createTabbedPane());
	}
	
	private JTabbedPane createTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		qlS = new QuanLySach();
		tabbedPane.addTab("Quản Lý Sách",  new ImageIcon(this.getClass().getResource("/book.png")), qlS);
		qlDG = new QuanLyDocGia();
		tabbedPane.addTab("Quản Lý Độc Giả", new ImageIcon(this.getClass().getResource("/per1.png")), qlDG);
		qlNV = new QuanLyNhanVien();
		tabbedPane.addTab("Quản Lý Nhân Viên", new ImageIcon(this.getClass().getResource("/per.png")), qlNV);
		qlMT = new QuanLyMuonTra();
		tabbedPane.addTab("Quản Lý Mượn Trả", new ImageIcon(this.getClass().getResource("/book1.png")), qlMT);
		
		return tabbedPane;
	}
	
	public static void main(String[] args) {
		Main main = new Main();
	}
}

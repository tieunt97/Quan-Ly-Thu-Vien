package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class QuanLyThuVien extends JFrame implements ActionListener{
	private JButton btnQLS, btnQLDG, btnQLNV, btnQLMT, btnLogin, btnCancel;
	private JLabel titleLab, labUser, labPassword;
	private JTextField tfUser;
	private JPasswordField pwd;
	Container conter;
	
	public QuanLyThuVien() {
		conter = this.getContentPane();
		conter.add(createMainPanel(1));
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 450);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private JPanel createMainPanel(int choose) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitlePanel(), BorderLayout.PAGE_START);
		if(choose == 0) 
			panel.add(createChoosePanel(), BorderLayout.CENTER);
		else
			panel.add(createLoginPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(35, 0, 0, 0));
		titleLab = new JLabel("Hệ Thống Quản Lý Thư Viện", JLabel.CENTER);
		titleLab.setFont(new Font("Caribli", Font.BOLD, 18));
		titleLab.setForeground(Color.BLUE);
		panel.add(titleLab);
		
		return panel;
	}

	private JPanel createChoosePanel() {
		JPanel panel = new JPanel(new GridLayout(5, 1, 5, 10));
		panel.setBorder(new EmptyBorder(50, 80, 100, 80));
		btnQLS = new JButton("Quản Lý Sách");
		btnQLS.setIcon(new ImageIcon(this.getClass().getResource("/book.png")));
		btnQLS.addActionListener(this);
		btnQLDG = new JButton("Quản Lý Độc Giả");
		btnQLDG.setIcon(new ImageIcon(this.getClass().getResource("/per1.png")));
		btnQLDG.addActionListener(this);
		btnQLNV = new JButton("Quản Lý Nhân Viên");
		btnQLNV.setIcon(new ImageIcon(this.getClass().getResource("/per.png")));
		btnQLNV.addActionListener(this);
		btnQLMT = new JButton("Quản Lý Mượn Trả");
		btnQLMT.setIcon(new ImageIcon(this.getClass().getResource("/book1.png")));
		btnQLMT.addActionListener(this);
		panel.add(btnQLS);
		panel.add(btnQLDG);
		panel.add(btnQLNV);
		panel.add(btnQLMT);
		
		return panel;
	}
	
	private JPanel createLoginPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBorder(new EmptyBorder(80, 60, 175, 60));
		
		JPanel panelLab = new JPanel(new GridLayout(2, 1, 15, 15));
		panelLab.add(labUser = new JLabel("Username: "));
		panelLab.add(labPassword = new JLabel("Password: "));
		
		JPanel panelTF = new JPanel(new GridLayout(2, 1, 15, 15));
		panelTF.add(tfUser = new JTextField());
		panelTF.add(pwd = new JPasswordField());
		
		JPanel panelBut = new JPanel(new GridLayout(1, 2, 10, 10));
		panelBut.setBorder(new EmptyBorder(0, 35, 0, 35));
		panelBut.add(btnLogin = new JButton("Đăng nhập"));
		panelBut.add(btnCancel = new JButton("Hủy"));
		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		
		panel.add(panelLab, BorderLayout.WEST);
		panel.add(panelTF, BorderLayout.CENTER);
		panel.add(panelBut, BorderLayout.SOUTH);
		
		
		return panel;
	}
	
	
	private void cancel() {
		tfUser.setText("");
		pwd.setText("");
	}
	private boolean checkUser() {
		if(tfUser.getText().equals("admin") && pwd.getText().equals("admin"))
			return true;
		else return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnQLS) {
			QuanLySach qlS = new QuanLySach();
			qlS.loadData("All", "");
		}
		if(e.getSource() == btnQLDG) {
			QuanLyDocGia qlDG = new QuanLyDocGia();
			qlDG.loadData("All", "");
		}
		if(e.getSource() == btnQLNV) {
			QuanLyNhanVien qlNV = new QuanLyNhanVien();
			qlNV.loadData("All", "");
		}
		if(e.getSource() == btnQLMT) {
			QuanLyMuonTra qlMT = new QuanLyMuonTra();
			qlMT.loadData("All", "");
		}
		if(e.getSource() == btnLogin) {
			if(checkUser()) {
				conter.removeAll();
				conter.add(createMainPanel(0));
			}else JOptionPane.showMessageDialog(this, "Lỗi đăng nhập");
			return;
		}
		if(e.getSource() == btnCancel) {
			cancel();
			return;
		}
	}
	
	
	public static void main(String[] args) {
		QuanLyThuVien qlTV = new QuanLyThuVien();
	}
}

package quanlythuvien.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import quanlythuvien.connect.MyConnectDB;

public class LoginQLTV extends JFrame {

	private JPanel contentPane;
	private JButton btnDangXuat;
	private String tenTK = "", matKhau = "", loaiTK = "";
	private MyConnectDB myConn;
	private MainDG mainDG = null;
	private MainNhanVien mainNV = null;
	private MainAdmin mainAd = null;
	private Main main = null;
	private JTextField tfUser;
	private JPasswordField pwPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginQLTV frame = new LoginQLTV();
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/iconF.png")));
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginQLTV() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResizable(false);
		myConn = new MyConnectDB();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 321);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(44, 62, 80));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 148, 6));
		panel.setBounds(0, 0, 458, 62);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblHThngQun = new JLabel("Hệ thống quản lý thư viện");
		lblHThngQun.setForeground(Color.WHITE);
		lblHThngQun.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHThngQun.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHThngQun);

		JLabel lblNguynTiTiu = new JLabel("Nguyễn Tài Tiêu - 20153752");
		lblNguynTiTiu.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNguynTiTiu.setForeground(Color.WHITE);
		lblNguynTiTiu.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNguynTiTiu);
		
		JLabel lblUserName = new JLabel("Tài khoản:");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUserName.setForeground(new Color(236, 240, 241));
		lblUserName.setBounds(32, 87, 96, 34);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Mật khẩu:");
		lblPassword.setForeground(new Color(236, 240, 241));
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(32, 137, 96, 34);
		contentPane.add(lblPassword);
		
		tfUser = new JTextField();
		tfUser.setForeground(new Color(228, 241, 254));
		tfUser.setBackground(new Color(108, 122, 137));
		tfUser.setBounds(127, 90, 207, 34);
		contentPane.add(tfUser);
		tfUser.setColumns(10);
		
		pwPass = new JPasswordField();
		pwPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					setAction();
					return;
				}
			}
		});
		pwPass.setForeground(new Color(228, 241, 254));
		pwPass.setBackground(new Color(108, 122, 137));
		pwPass.setBounds(127, 140, 207, 34);
		contentPane.add(pwPass);
		
		JButton btnDangNhap = new JButton("Đăng nhập");
		btnDangNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAction();
				return;
			}
		});
		btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDangNhap.setBackground(new Color(34, 167, 240));
		btnDangNhap.setForeground(new Color(255, 255, 255));
		btnDangNhap.setBounds(235, 193, 96, 34);
		contentPane.add(btnDangNhap);
		
		JButton btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHuy.setForeground(new Color(255, 255, 255));
		btnHuy.setBackground(new Color(192, 57, 43));
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
				return;
			}
		});
		btnHuy.setBounds(127, 193, 98, 34);
		contentPane.add(btnHuy);
		
		JLabel lblNhnVoy = new JLabel("Nhấn vào đây để tạo tài khoản");
		lblNhnVoy.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new DangKy(myConn);
				return;
			}
		});
		lblNhnVoy.setForeground(new Color(255, 255, 255));
		lblNhnVoy.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNhnVoy.setBounds(32, 250, 204, 31);
		contentPane.add(lblNhnVoy);
	}

	private void setAction() {
		if (tfUser.getText().equals("") || pwPass.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Nhập tài khoản và mật khẩu để đăng nhập", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (checkTK(tfUser.getText().trim(), pwPass.getText().trim())) {
			cancel();
			if (loaiTK.equalsIgnoreCase("dg")) {
				mainDG = new MainDG(tenTK, loaiTK, myConn);
				btnDangXuat = mainDG.getDoiMK().getBtnDangXuat();
			} else if (loaiTK.equalsIgnoreCase("nv")) {
				mainNV = new MainNhanVien(tenTK, loaiTK, myConn);
				btnDangXuat = mainNV.getDoiMK().getBtnDangXuat();
			} else if (loaiTK.equalsIgnoreCase("admin")) {
				mainAd = new MainAdmin(tenTK, loaiTK, myConn);
				btnDangXuat = mainAd.getDoiMK().getBtnDangXuat();
			} else if (loaiTK.equalsIgnoreCase("main")) {
				main = new Main(myConn);
			}
			btnDangXuat.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (mainDG != null)
						mainDG.dispose();
					if (mainNV != null)
						mainNV.dispose();
					if (mainAd != null) {
						mainAd.dispose();
					}
					setVisible(true);
				}
			});
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE,
					null);
		}
	}

	private boolean checkTK(String user, String pw) {
		ResultSet rs = myConn.getDataID("TaiKhoan", "idTaiKhoan", "All", "");
		try {
			while (rs.next()) {
				tenTK = rs.getString(1);
				matKhau = rs.getString(2);
				loaiTK = rs.getString(3);
				if (tenTK.equalsIgnoreCase(user) && matKhau.equalsIgnoreCase(pw)) {
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
		pwPass.setText("");
	}
}

package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.DocGia;
import quanlythuvien.object.TaiKhoan;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;

public class DangKy extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfTaiKhoan, tfHoTen, tfEmail, tfSoDT, tfDiaChi, tfNgaySinh;
	private JPasswordField pwMatKhau, pwXacNhan;
	private MyConnectDB myConn;
	private JRadioButton radNam, radNu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DangKy dialog = new DangKy(new MyConnectDB());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DangKy(MyConnectDB myConn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/iconF.png")));
		this.myConn = myConn;
		setBounds(100, 100, 448, 547);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 153, 153));
		panel.setBounds(10, 11, 414, 50);
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblHThngQun = new JLabel("Hệ thống quản lý thư viện");
		lblHThngQun.setForeground(Color.YELLOW);
		lblHThngQun.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblHThngQun.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHThngQun);

		JLabel lblNewLabel = new JLabel("Nguyễn Tài Tiêu - 20153752");
		lblNewLabel.setForeground(Color.YELLOW);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Đăng ký tài khoản", TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.BLUE));
		panel_1.setBounds(60, 72, 314, 392);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblTnngNhp = new JLabel("Tên đăng nhập*:");
		lblTnngNhp.setBounds(20, 24, 97, 24);
		panel_1.add(lblTnngNhp);

		JLabel lblMtKhu = new JLabel("Mật khẩu*:");
		lblMtKhu.setBounds(20, 59, 87, 24);
		panel_1.add(lblMtKhu);

		JLabel lblXcNhnMt = new JLabel("Xác nhận*:");
		lblXcNhnMt.setBounds(20, 94, 87, 24);
		panel_1.add(lblXcNhnMt);

		tfTaiKhoan = new JTextField();
		tfTaiKhoan.setBounds(117, 24, 177, 24);
		panel_1.add(tfTaiKhoan);
		tfTaiKhoan.setColumns(10);

		pwMatKhau = new JPasswordField();
		pwMatKhau.setBounds(117, 59, 176, 24);
		panel_1.add(pwMatKhau);

		pwXacNhan = new JPasswordField();
		pwXacNhan.setBounds(117, 94, 177, 24);
		panel_1.add(pwXacNhan);

		JLabel lblHTn = new JLabel("Họ tên*:");
		lblHTn.setBounds(20, 129, 87, 24);
		panel_1.add(lblHTn);

		JLabel lblEmail = new JLabel("Email*:");
		lblEmail.setBounds(20, 234, 87, 24);
		panel_1.add(lblEmail);

		JLabel lblNgySinh = new JLabel("Ngày sinh*:");
		lblNgySinh.setBounds(20, 164, 87, 24);
		panel_1.add(lblNgySinh);

		JLabel lblGiiTnh = new JLabel("Giới tính*:");
		lblGiiTnh.setBounds(20, 199, 87, 24);
		panel_1.add(lblGiiTnh);

		JLabel lblaCh = new JLabel("Địa chỉ*:");
		lblaCh.setBounds(20, 303, 87, 24);
		panel_1.add(lblaCh);

		JLabel lblSinThoai = new JLabel("Số điện thoai*:");
		lblSinThoai.setBounds(20, 269, 87, 24);
		panel_1.add(lblSinThoai);

		tfHoTen = new JTextField();
		tfHoTen.setColumns(10);
		tfHoTen.setBounds(117, 129, 177, 24);
		panel_1.add(tfHoTen);

		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(117, 234, 177, 24);
		panel_1.add(tfEmail);

		tfSoDT = new JTextField();
		tfSoDT.setColumns(10);
		tfSoDT.setBounds(117, 271, 177, 24);
		panel_1.add(tfSoDT);

		tfDiaChi = new JTextField();
		tfDiaChi.setColumns(10);
		tfDiaChi.setBounds(117, 305, 177, 24);
		panel_1.add(tfDiaChi);

		tfNgaySinh = new JTextField();
		tfNgaySinh.setColumns(10);
		tfNgaySinh.setBounds(116, 166, 177, 24);
		panel_1.add(tfNgaySinh);

		radNam = new JRadioButton("Nam");
		radNam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radNu.setSelected(false);
			}
		});
		radNam.setBounds(117, 199, 57, 23);
		panel_1.add(radNam);

		radNu = new JRadioButton("Nữ");
		radNu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radNam.setSelected(false);
			}
		});
		radNu.setBounds(176, 200, 57, 23);
		panel_1.add(radNu);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(this.getClass().getResource("/bg.jpg")));
		label.setBounds(0, 0, 434, 475);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DocGia dg = getDocGia();
						TaiKhoan tk = getTaiKhoan();
						try {
							if (dg != null && tk != null) {
								boolean ck = DangKy.this.myConn.insert("DocGia", null, dg, null, null, null);
								boolean ck1 = DangKy.this.myConn.insertTK(tk);
								if(ck && ck1) {
									JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công");
									cancel();
									return;
								}else if(ck && !ck1) {
									DangKy.this.myConn.deleteID("DocGia", "idDocGia", dg.getIdDocGia());
									JOptionPane.showMessageDialog(null, "Trùng tên tài khoản", "Error", JOptionPane.ERROR_MESSAGE);
									return;
								}else if(!ck && ck1) {
									DangKy.this.myConn.deleteID("TaiKhoan", "idTaiKhoan", tk.getTenTK());
									JOptionPane.showMessageDialog(null, "Trùng tên tài khoản", "Error", JOptionPane.ERROR_MESSAGE);
									return;
								}else {
									JOptionPane.showMessageDialog(null, "Trùng tên tài khoản", "Error", JOptionPane.ERROR_MESSAGE);
									return;
								}

							}
						} catch (Exception exc) {
							System.out.println("Error: " + exc);
							JOptionPane.showMessageDialog(null, "Trùng tên tài khoản", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				});
				btnOk.setActionCommand("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}

			JButton btnThot = new JButton("Thoát");
			btnThot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			buttonPane.add(btnThot);
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void cancel() {
		tfTaiKhoan.setText("");
		pwMatKhau.setText("");
		pwXacNhan.setText("");
		tfHoTen.setText("");
		tfNgaySinh.setText("");
		radNam.setSelected(false);
		radNu.setSelected(false);
		tfEmail.setText("");
		tfSoDT.setText("");
		tfDiaChi.setText("");
	}

	private DocGia getDocGia() {
		String id = tfTaiKhoan.getText();
		String ten = tfHoTen.getText();
		String ns = tfNgaySinh.getText();
		String gt = radNam.isSelected() ? "Nam" : (radNu.isSelected() ? "Nữ" : "");
		String email = tfEmail.getText();
		String sdt = tfSoDT.getText();
		String dc = tfDiaChi.getText();
		if (id.equals("") || ten.equals("") || ns.equals("") || gt.equals("") || email.equals("") || sdt.equals("")
				|| dc.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		DocGia dg = new DocGia(id, ten, ns, gt, email, sdt, dc);
		return dg;
	}
	
	private TaiKhoan getTaiKhoan() {
		String tenTK = tfTaiKhoan.getText();
		String matKhau = pwMatKhau.getText();
		String xacNhan = pwXacNhan.getText();
		if(matKhau.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập mật khẩu", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		if(!matKhau.equals(xacNhan)) {
			JOptionPane.showMessageDialog(null, "Xác nhận mật khẩu sai", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		TaiKhoan tk = new TaiKhoan(tenTK, matKhau, "DG");
		
		return tk;
	}
}

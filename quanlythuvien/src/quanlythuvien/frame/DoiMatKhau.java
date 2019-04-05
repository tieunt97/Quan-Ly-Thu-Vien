package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.TaiKhoan;

public class DoiMatKhau extends JPanel implements ActionListener {
	private JLabel lbTenTk, lbMatKhau;
	private JTextField tfTenTK = new JTextField();
	private JPasswordField pw, newPW, xacNhanPW;
	private JButton btnChapNhan, btnHuy, btnDangXuat;
	private MyConnectDB myConn;
	private String loaiTK = "", tenTK = "", dangXuat = "";

	public DoiMatKhau(String tenTk, String loaiTK, MyConnectDB myConn) {
		this.tenTK = tenTk;
		this.loaiTK = loaiTK;
		this.myConn = myConn;
		
		setLayout(new BorderLayout());
		add(createMainTitlePanel());
	}

	private JPanel createMainTitlePanel() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(new EmptyBorder(15, 20, 360, 800));
		panelMain.setBackground(new Color(0x009999));
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(new Color(0x009999));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.add(createTitlePanel(), BorderLayout.NORTH);
		panel.add(createMainPanel(), BorderLayout.CENTER);
		panel.add(createBottomPanel(), BorderLayout.SOUTH);
		
		
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder(null, ""));
		panel1.add(panel);
		panelMain.add(panel1);
		return panelMain;
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Thông tin tài khoản");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.add(label);
		panel.setBackground(new Color(0x009999));

		return panel;
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(new Color(0x009999));
		panel.add(createLabPanel(), BorderLayout.WEST);
		panel.add(createTFPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.setBackground(new Color(0x009999));
		panel.add(btnChapNhan = createButton("Chấp nhận"));
		panel.add(btnHuy = createButton("Hủy"));
		panel.add(btnDangXuat = createButton("Đăng xuất"));

		return panel;
	}

	private JPanel createLabPanel() {
		JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
		panel.setBackground(new Color(0x009999));
		if (loaiTK.equalsIgnoreCase("nv")) {
			panel.add(createLabel("Tên nhân viên:"));
		} else if (loaiTK.equalsIgnoreCase("dg")) {
			panel.add(createLabel("Tên độc giả:"));
		} else if (loaiTK.equalsIgnoreCase("admin")) {
			panel.add(new JLabel(""));
		}
		panel.add(createLabel("Tên tài khoản:"));
		panel.add(createLabel("Mật khẩu cũ:"));
		panel.add(createLabel("Mật khẩu mới:"));
		panel.add(createLabel("Xác nhận mật khẩu:"));

		return panel;
	}

	private JPanel createTFPanel() {
		JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
		panel.setBackground(new Color(0x009999));
		String ten = "";
		if (loaiTK.equalsIgnoreCase("nv")) {
			ResultSet rs = myConn.getNames("nhanvien", "tenNV", "idNhanVien", tenTK);
			try {
				if (rs.next())
					ten = rs.getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (loaiTK.equalsIgnoreCase("dg")) {
			ResultSet rs = myConn.getNames("docgia", "tenDG", "idDocGia", tenTK);
			try {
				if (rs.next())
					ten = rs.getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JLabel labelInfo = createLabel(ten);
		labelInfo.setForeground(Color.blue);
		labelInfo.setFont(new Font("Caribli", Font.ITALIC, 16));
		panel.add(labelInfo);
		panel.add(tfTenTK = new JTextField());
		tfTenTK.setEnabled(false);
		tfTenTK.setText(tenTK);
		panel.add(pw = new JPasswordField());
		panel.add(newPW = new JPasswordField());
		panel.add(xacNhanPW = new JPasswordField());

		return panel;
	}

	private JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setForeground(Color.white);
		
		return label;
	}
	
	private JButton createButton(String name) {
		JButton btn = new JButton(name);
		btn.addActionListener(this);
		return btn;
	}

	private void cancel() {
		pw.setText("");
		newPW.setText("");
		xacNhanPW.setText("");
	}

	private String getTaiKhoan() {
		String mk = pw.getText().trim();
		String mkMoi = newPW.getText().trim();
		String mkMoiXN = xacNhanPW.getText().trim();
		String mk1 = "";

		ResultSet rs = myConn.getNames("taikhoan", "passWord", "idTaiKhoan", tfTenTK.getText().trim());
		try {
			if (rs.next()) {
				mk1 = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (mk1 != "" && mk1.equals(mk)) {
			if (mkMoi.equals(mkMoiXN))
				return mkMoi;
		}
		return "";
	}

	private void update() {
		String mkMoi = getTaiKhoan();
		TaiKhoan tk = new TaiKhoan(tfTenTK.getText().trim(), mkMoi, loaiTK);
		if (!mkMoi.equals("")) {
			if (myConn.updateTK(tfTenTK.getText().trim(), tk))
				JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thành công");
			else {
				JOptionPane.showMessageDialog(null, "Mật khẩu sai");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Mật khẩu sai hoặc xác nhận mật khẩu sai", "Error update",
					JOptionPane.ERROR_MESSAGE);
		}
		cancel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnHuy) {
			cancel();
			return;
		}
		if (e.getSource() == btnChapNhan) {
			update();
			return;
		}
	}

	public JButton getBtnDangXuat() {
		return btnDangXuat;
	}

	public void setBtnDangXuat(JButton btnDangXuat) {
		this.btnDangXuat = btnDangXuat;
	}

}

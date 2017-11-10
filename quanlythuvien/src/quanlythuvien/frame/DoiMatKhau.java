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

import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.TaiKhoan;

public class DoiMatKhau extends JPanel implements ActionListener{
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
		
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(0, 20, 450, 800));
		add(createTitlePanel(), BorderLayout.NORTH);
		add(createMainPanel(), BorderLayout.CENTER);
		add(createBottomPanel(), BorderLayout.SOUTH);
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
		panel.add(createLabPanel(), BorderLayout.WEST);
		panel.add(createTFPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.add(btnChapNhan = createButton("Chấp nhận"));
		panel.add(btnHuy = createButton("Hủy"));
		panel.add(btnDangXuat = createButton("Đăng xuất"));
		
		return panel;
	}
	
	private JPanel createLabPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
		panel.add(new JLabel("Tên tài khoản:"));
		panel.add(new JLabel("Mật khẩu cũ:"));
		panel.add(new JLabel("Mật khẩu mới:"));
		panel.add(new JLabel("Xác nhận mật khẩu mới:"));
		
		return panel;
	}
	
	private JPanel createTFPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
		panel.add(tfTenTK = new JTextField());
		tfTenTK.setEnabled(false);
		tfTenTK.setText(tenTK);
		panel.add(pw = new JPasswordField());
		panel.add(newPW = new JPasswordField());
		panel.add(xacNhanPW = new JPasswordField());
		
		return panel;
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
		
		ResultSet rs = myConn.getNames("TaiKhoan", "passWord", "idTaiKhoan", tfTenTK.getText().trim());
		try {
			if(rs.next()) {
				mk1 = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mk1 != "" && mk1.equals(mk)) {
			if(mkMoi.equals(mkMoiXN))
				return mkMoi;
		}
		return "";
	}
	
	private void update() {
		String mkMoi = getTaiKhoan();
		TaiKhoan tk = new TaiKhoan(tfTenTK.getText().trim(), mkMoi, loaiTK);
		if(!mkMoi.equals("")) {
			if(myConn.updateTK(tfTenTK.getText().trim(), tk))
				JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thành công");
			else {
				JOptionPane.showMessageDialog(null, "Mật khẩu sai");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Mật khẩu sai hoặc xác nhận mật khẩu sai", "Error update", JOptionPane.ERROR_MESSAGE);
		}
		cancel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnHuy) {
			cancel();
			return;
		}
		if(e.getSource() == btnChapNhan) {
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

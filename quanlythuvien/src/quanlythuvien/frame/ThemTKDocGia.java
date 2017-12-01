package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.TaiKhoan;

public class ThemTKDocGia extends JPanel implements ActionListener, MouseListener {
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnHuy;
	private JTextField tfTenTK, tfMatKhau, tfTimKiem;
	private JLabel lbTenTK, lbMatKhau;
	private JTable table;
	private String[] titleCols = { "Tên tài khoản", "Mật khẩu", "Loại tài khoản" };
	private MyConnectDB myConn;
	private String loaiTK = null;
	private Color color = new Color(0x009999);

	public ThemTKDocGia(MyConnectDB myConn, String loaiTK) {
		this.myConn = myConn;
		this.loaiTK = loaiTK;

		setLayout(new BorderLayout());
		add(createMainPanel(), BorderLayout.CENTER);
		loadData("All", "", loaiTK);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 10, 120, 10));
		panel.add(createTableMainPanel());
		panel.add(createBottomPanel());

		return panel;
	}

	private JPanel createTableMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(10, 50, 15, 25));
		panel.setBackground(color);
		panel.add(createTitleTable(), BorderLayout.NORTH);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createTitleTable() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(color);
		JLabel label = new JLabel("Thông tin tài khoản", JLabel.CENTER);
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		panel.add(label);
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(new EmptyBorder(5, 50, 10, 50));
		panelMain.setBackground(color);
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder(null, ""));
		panel1.add(new JScrollPane(table = new JTable()));
		panelMain.add(panel1);
		
		return panelMain;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(50, 0, 0, 30));
		JPanel panelInput = new JPanel(new GridLayout());
		panelInput.setBackground(color);
		panelInput.add(createInputL());
		panel.add(panelInput, BorderLayout.NORTH);
		panel.add(createButtonPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createInputL() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 0, 225));
		JPanel panelL = new JPanel(new GridLayout(3, 1, 5, 5));
		panelL.setBackground(color);
		panelL.add(createLabel("Tên đăng nhập:"));
		panelL.add(createLabel("Mật khẩu:"));

		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.setBackground(color);
		panelR.add(tfTenTK = new JTextField());
		panelR.add(tfMatKhau = new JTextField());

		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout(15, 15));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(25, 0, 140, 225));
		JPanel paneltk = new JPanel(new BorderLayout(10, 10));
		paneltk.setBackground(color);
		paneltk.add(btnTimKiem = createButton("Tìm kiếm"), BorderLayout.WEST);
		paneltk.add(tfTimKiem = new JTextField(), BorderLayout.CENTER);

		JPanel panelButO = new JPanel(new GridLayout(2, 2, 10, 10));
		panelButO.setBackground(color);
		panelButO.add(btnThem = createButton("Thêm"));
		panelButO.setBorder(new EmptyBorder(15, 0, 55, 0));
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/add.png")));
		panelButO.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/update1.png")));
		panelButO.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/delete.png")));
		panelButO.add(btnHuy = createButton("Hủy"));
		btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));

		panel.add(paneltk, BorderLayout.NORTH);
		panel.add(panelButO, BorderLayout.CENTER);

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

	public void loadData(String Cot, String muonTim, String loaiTK) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCols);
		ResultSet rs = null;
		rs = myConn.getDataID("TaiKhoan", "idTaiKhoan", Cot, muonTim);

		String arr[] = new String[3];
		try {
			while (rs.next()) {
				for (int i = 0; i < arr.length; i++) {
					arr[i] = rs.getString(i + 1);
				}
				if (arr[2].equalsIgnoreCase(loaiTK)) {
					model.addRow(arr);
				} else
					continue;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setModel(model);
	}

	private TaiKhoan getTK() {
		String tenTK = tfTenTK.getText().toUpperCase().trim();
		String matKhau = tfMatKhau.getText().trim();
		if (tenTK.equals("") || matKhau.equals("")) {
			return null;
		} else {
			TaiKhoan tk = new TaiKhoan(tenTK, matKhau, loaiTK.toUpperCase());
			return tk;
		}
	}

	private void add() {
		TaiKhoan tk = getTK();
		if (tk != null) {
			boolean ck = myConn.insertTK(tk);
			if (ck) {
				JOptionPane.showMessageDialog(null, "Thêm thành công");
				loadData("All", "", loaiTK);
				cancel();
			} else {
				JOptionPane.showMessageDialog(null, "Trùng tên tài khoản", "Error insert", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Error insert", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void update() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một dòng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return;
		}
		TaiKhoan tk = getTK();
		if (tk != null) {
			boolean ck = myConn.updateTK(tk.getTenTK(), tk);
			if (ck) {
				JOptionPane.showMessageDialog(null, "Cập nhật thành công");
				loadData("All", "", loaiTK);
				cancel();
			} else {
				JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "Error update", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void delete() {
		int row = table.getSelectedRow();
		if (row < 0) {
			System.out.println("Error delete");
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Delete",
				JOptionPane.YES_NO_OPTION);
		if (select == 0) {
			boolean ck = myConn.deleteID("TaiKhoan", "idTaiKhoan", (String) table.getValueAt(row, 0));
			loadData("All", "", loaiTK);
			if (ck)
				JOptionPane.showMessageDialog(null, "Xóa thành công");
		}
	}

	private void cancel() {
		tfTenTK.setText("");
		tfTenTK.setEnabled(true);
		tfMatKhau.setText("");
		tfTimKiem.setText("");
		table.getSelectionModel().clearSelection();
		if(!btnThem.isEnabled()) {
			btnThem.setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnTimKiem) {
			loadData("idTaiKhoan", tfTimKiem.getText().trim(), loaiTK);
			return;
		}
		if (e.getSource() == btnThem) {
			add();
			return;
		}
		if (e.getSource() == btnSua) {
			update();
			return;
		}
		if (e.getSource() == btnXoa) {
			delete();
			return;
		}
		if (e.getSource() == btnHuy) {
			cancel();
			return;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		if(row >= 0) {
			tfTenTK.setText((String) table.getValueAt(row, 0));
			tfTenTK.setEnabled(false);
			tfMatKhau.setText((String) table.getValueAt(row, 1));	
			btnThem.setEnabled(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

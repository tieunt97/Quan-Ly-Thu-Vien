package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.TaiKhoan;

public class ThemTKDocGia extends JPanel implements ActionListener{
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnCapNhat, btnHuy;
	private JTextField tfTenTK, tfMatKhau, tfLoaiTK, tfTimKiem;
	private JLabel lbTenTK, lbMatKhau;
	private JTable table;
	private String[] titleCols = {"Tên tài khoản", "Mật khẩu", "Loại tài khoản"};
	private MyConnectDB myConn; 
//	= new MyConnectDB(); 
	
	
	public ThemTKDocGia(MyConnectDB myConn) {
		this.myConn = myConn;
		
		setLayout(new BorderLayout());
		add(createMainPanel(), BorderLayout.CENTER);
		loadData("All", "");
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		panel.setBorder(new EmptyBorder(10, 50, 120, 50));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());
		
		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel =  new JPanel(new BorderLayout());
		panel.add(new JScrollPane(table = new JTable()));
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(createInputL());
		panel.add(createInputR());
		panel.add(createButtonPanel());
		
		return panel;
	}
	
	private JPanel createInputL() {
		JPanel panel =  new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 195, 80));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 5, 5));
		panelL.add(new JLabel("Tên đăng nhập"));
		panelL.add(new JLabel("Mật khẩu"));
		
		JPanel panelR = new JPanel(new GridLayout(2, 1, 5, 5));
		panelR.add(tfTenTK = new JTextField());
		panelR.add(tfMatKhau = new JTextField());
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createInputR() {
		JPanel panel =  new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 195, 80));
		JPanel panelL = new JPanel(new GridLayout(2, 1, 5, 5));
		panelL.add(new JLabel("Loại tài khoản"));
		panelL.add(new JLabel(""));
		
		JPanel panelR = new JPanel(new GridLayout(2, 1, 5, 5));
		panelR.add(tfLoaiTK = new JTextField());
		panelR.add(btnCapNhat = createButton("Cập nhật"));
		btnCapNhat.setVisible(false);
		
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 40, 100, 0));
		JPanel paneltk = new JPanel(new BorderLayout(10, 10));
//		paneltk.setBorder(new EmptyBorder(0, 0, 35, 0));
		paneltk.add(btnTimKiem = createButton("Tìm kiếm"), BorderLayout.WEST);
		paneltk.add(tfTimKiem = new JTextField(), BorderLayout.CENTER);
		
		JPanel panelButO = new JPanel(new GridLayout(2, 2, 10, 10));
		panelButO.setBorder(new EmptyBorder(10, 0, 25, 0));
		panelButO.add(btnThem = createButton("Thêm"));
		panelButO.add(btnSua = createButton("Sửa"));
		panelButO.add(btnXoa = createButton("Xóa"));
		panelButO.add(btnHuy = createButton("Hủy"));
		
		panel.add(paneltk, BorderLayout.NORTH);
		panel.add(panelButO, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JButton createButton(String name) {
		JButton btn = new JButton(name);
		btn.addActionListener(this);
		return btn;
	}

	public void loadData(String Cot, String muonTim) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCols);
		ResultSet rs = null;
		rs = myConn.getDataID("TaiKhoan", "idTaiKhoan", Cot, muonTim);
		
		String arr[] = new String[3];
		try {
			while(rs.next()) {
				for(int i = 0; i < arr.length; i++) {
					arr[i] = rs.getString(i + 1);
				}
				model.addRow(arr);
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
		String loaiTK = tfLoaiTK.getText().toUpperCase().trim();
		
		if(tenTK.equals("") || matKhau.equals("") || loaiTK.equals("")) {
			return null;
		}
		else {
			TaiKhoan tk = new TaiKhoan(tenTK, matKhau, loaiTK);
			return tk;
		}
	}
	
	private void add() {
		TaiKhoan tk = getTK();
		if(tk != null) {
			myConn.insertTK(tk);
			loadData("All", "");
		}else {
			JOptionPane.showMessageDialog(null, "Trùng tên tài khoản hoặc có trường dữ liệu trống", "Notify", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private boolean setUpdate() {
		if(table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một dòng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return false;
		}else {
			int row = table.getSelectedRow();
			tfTenTK.setText((String) table.getValueAt(row, 0));
			tfTenTK.setEnabled(false);
			tfMatKhau.setText((String) table.getValueAt(row, 1));
			tfLoaiTK.setText((String) table.getValueAt(row, 2));
			tfLoaiTK.setEnabled(false);
			btnCapNhat.setVisible(true);
			return true;
		}
	}
	
	private void update() {
		TaiKhoan tk = getTK();
		if(setUpdate() && tk != null) {
			myConn.updateTK(tk.getTenTK(), tk);
			loadData("All", "");
		}
		cancel();
	}
	
	private void delete() {
		int row = table.getSelectedRow();
		if(row < 0) {
			System.out.println("Error delete");
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Bạn có chắc muốn xóa không", "Delete", 0, JOptionPane.YES_NO_OPTION, null, null, 1);
		if(select == 0) {
			boolean ck = myConn.deleteID("TaiKhoan", "idTaiKhoan", (String) table.getValueAt(row, 0));
			loadData("All", "");
			if(ck) JOptionPane.showMessageDialog(null, "Xóa thành công");
		}
	}
	
	private void cancel() {
		tfTenTK.setText("");
		tfTenTK.setEnabled(true);
		tfMatKhau.setText("");
		tfLoaiTK.setText("");
		tfLoaiTK.setEnabled(true);
		tfTimKiem.setText("");
		btnCapNhat.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnTimKiem) {
			loadData("idTaiKhoan", tfTimKiem.getText().trim());
			return;
		}
		if(e.getSource() == btnThem) {
			add();
			return;
		}
		if(e.getSource() == btnSua) {
			setUpdate();
			return;
		}
		if(e.getSource() == btnXoa) {
			delete();
			return;
		}
		if(e.getSource() == btnCapNhat) {
			update();
			return;
		}
		if(e.getSource() == btnHuy) {
			cancel();
			loadData("All", "");
			return;
		}
		
	}
	
	public static void main(String[] args) {
		MyConnectDB myConn = new MyConnectDB();
		JFrame frame = new JFrame();
		ThemTKDocGia addTK;
		frame.add(addTK =new ThemTKDocGia(myConn));
		addTK.loadData("All", "");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(1200, 720);
	}
	
}

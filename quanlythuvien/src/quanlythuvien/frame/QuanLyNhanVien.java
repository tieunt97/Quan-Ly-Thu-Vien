package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import quanlythuvien.connect.Check;
import quanlythuvien.connect.ExportFile;
import quanlythuvien.connect.ImportFile;
import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.DocGia;
import quanlythuvien.object.NhanVien;

public class QuanLyNhanVien extends JPanel implements ActionListener, MouseListener{
	private String titleCol[] = { "Mã nhân viên", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Số điện thoại" };
	private JTable table;
	final JFileChooser fileDialog = new JFileChooser();
	private ExportFile ef;
	private ImportFile imp;
	private String exFile;
	private JButton btnThem, btnSua, btnCancel, btnXuatFile, btnXoa, btnTimKiem, btnThongKe, btnThemFile;
	private JComboBox<String> timKiemCB, thongKeCB;
	private JRadioButton radNam, radNu;
	private String[] timKiemVal = { "All", "idNhanVien", "TenNV", "NgaySinh", "DiaChi", "GioiTinh" };
	private String[] thongKeVal = { "TenNV", "NgaySinh", "DiaChi", "GioiTinh"};
	private JTextField tfIdNV, tfTimKiem, tfTenNV, tfNgaySinhNV, tfDiaChiNV, tfSdtNV;
	MyConnectDB myConn;
	private String gt = null;
	private Color color = new Color(0x009999);

	public QuanLyNhanVien(MyConnectDB connect) {
		myConn = connect;

		setLayout(new BorderLayout());
		add(createMainPanel());
		loadData("All", "");
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitlePanel(), BorderLayout.PAGE_START);
		panel.add(createGridPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createGridPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(createTablePanel());
		panel.add(createBottomPanel());

		return panel;

	}

	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Thông tin nhân viên");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		;
		panel.add(label);
		panel.setBackground(color);

		return panel;
	}

	private JPanel createTablePanel() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(new EmptyBorder(5, 50, 10, 50));
		panelMain.setBackground(color);
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder(null, ""));
		JPanel panel = new JPanel(new GridLayout());
		panel.add(new JScrollPane(table = createTable()));
		panel.setBorder(new EmptyBorder(10, 15, 10, 15));
		panel.setBackground(color);
	
		panel1.add(panel);
		panelMain.add(panel1);
		
		return panelMain;
	}

	private JTable createTable() {
		JTable table = new JTable();
		table.addMouseListener(this);
		return table;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 50, 10, 50));
		panel.add(createInputPanelL());
		panel.add(createInputPanelR());
		panel.add(createButtonPanel());
		return panel;
	}

	private JPanel createInputPanelL() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 150, 20));
		JPanel panelLeft = new JPanel(new GridLayout(4, 1));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Mã nhân viên:"));
		panelLeft.add(createLabel("Giới tính:"));
		panelLeft.add(createLabel("Ngày sinh:"));
		panelLeft.add(new JLabel(""));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfIdNV = new JTextField());
		panelRight.add(panelGioiTinh());
		panelRight.add(tfNgaySinhNV = new JTextField());
		panelRight.add(new JLabel(""));

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 150, 20));
		JPanel panelLeft = new JPanel(new GridLayout(4, 1));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Họ tên:"));
		panelLeft.add(createLabel("Địa chỉ:"));
		panelLeft.add(createLabel("Số điện thoại:"));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfTenNV = new JTextField());
		panelRight.add(tfDiaChiNV = new JTextField());
		panelRight.add(tfSdtNV = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}
	
	private JPanel panelGioiTinh() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 0, 135));
		panel.add(radNam = createRadio("Nam"));
		panel.add(radNu = createRadio("Nữ"));
		
		return panel;
	}
	
	private JRadioButton createRadio(String name) {
		JRadioButton radio = new JRadioButton(name);
		radio.setBackground(color);
		radio.setForeground(Color.white);
		radio.addActionListener(this);
		
		return radio;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 80, 0));
		panel.add(createTKTKPanel());
		panel.add(createButPanel());

		return panel;
	}

	private JPanel createTKTKPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
		panel.setBackground(color);
		panel.add(createButCBPanel());
		panel.add(createtfTKTKPanel());

		return panel;
	}

	private JPanel createtfTKTKPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 15));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 5, 24, 0));
		tfTimKiem = new JTextField();
		panel.add(tfTimKiem);

		return panel;
	}

	private JPanel createButPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(5, 0, 10, 5));
		panel.add(createButAUD());
		panel.add(createButCO());

		return panel;
	}

	private JPanel createButAUD() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel.add(btnThem = createButton("Thêm"));
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/addp3.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/updatep2.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/deletep.png")));

		return panel;
	}

	private JPanel createButCO() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnThemFile = createButton("Nhập file"));
		btnThemFile.setIcon(new ImageIcon(this.getClass().getResource("/nf.png")));
		panel.add(btnXuatFile = createButton("Xuất file"));
		btnXuatFile.setToolTipText("Xuất file");
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/print.png")));
		return panel;
	}

	private JPanel createButCBPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 15));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 25, 0));
		panel.add(btnTimKiem = createButton(""));
		btnTimKiem.setIcon(new ImageIcon(this.getClass().getResource("/search.png")));
		btnTimKiem.setToolTipText("Tìm kiếm");
		timKiemCB = new JComboBox<String>(timKiemVal);
		panel.add(timKiemCB);
		panel.add(btnThongKe = createButton(""));
		btnThongKe.setIcon(new ImageIcon(this.getClass().getResource("/tk.png")));
		btnThongKe.setToolTipText("Thống kê");
		thongKeCB = new JComboBox<String>(thongKeVal);
		panel.add(thongKeCB);
		return panel;
	}

	private JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setForeground(Color.white);
		
		return label;
	}
	
	private JButton createButton(String str) {
		JButton btn = new JButton(str);
		btn.addActionListener(this);
		return btn;
	}

	// load data in database
	public void loadData(String Cot, String muonTim) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCol);

		ResultSet rs = myConn.getDataID("NhanVien", "idNhanVien", Cot, muonTim);
		String arr[] = new String[6];
		try {
			while (rs.next()) {
				for (int i = 0; i < arr.length; i++) {
					arr[i] = rs.getString(i + 1);
				}
				model.addRow(arr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(172);
		table.getColumnModel().getColumn(4).setPreferredWidth(172);
	}

	private String[] getTK() {
		String[] arr = new String[2];
		arr[0] = thongKeCB.getSelectedItem().toString().trim();
		if (arr[0].equals("TenNV"))
			arr[1] = "Tên nhân viên";
		if (arr[0].equals("GioiTinh"))
			arr[1] = "Giới tính";
		if (arr[0].equals("DiaChi"))
			arr[1] = "Địa chỉ";
		if (arr[0].equals("NgaySinh"))
			arr[1] = "Ngày sinh";

		return arr;
	}

	// Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK();
		String[] titleVar = { "TT", str[1], "Số lượng" };
		model.setColumnIdentifiers(titleVar);
		ResultSet rs = null;
		rs = myConn.getVar("NhanVien", "idNhanVien", str[0]);

		String arr[] = new String[3];

		try {
			int j = 1;
			while (rs.next()) {
				for (int i = 0; i < arr.length; i++) {
					if (i == 0) {
						arr[i] = "" + (j++) + "";
						continue;
					}
					arr[i] = rs.getString(i);
				}
				model.addRow(arr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setModel(model);
		cancel();
	}

	private NhanVien getNhanVien() {
		String id = tfIdNV.getText().trim().toUpperCase();
		String ten = tfTenNV.getText().trim();
		String ngaysinh = tfNgaySinhNV.getText().trim();
		String sdt = tfSdtNV.getText().trim();
		String diachi = tfDiaChiNV.getText().trim();
		if(radNam.isSelected()) gt = "Nam";
		else if(radNu.isSelected()) gt = "Nữ";
		else gt = null;
		if (id.equals("") || ten.equals("") || ngaysinh.equals("") || gt == null ||diachi.equals("") || sdt.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		NhanVien nv = new NhanVien(id, ten, ngaysinh, gt, diachi, sdt);

		return nv;
	}

	private boolean checkTTinNV(NhanVien nv) {
		if(!Check.checkDate(nv.getNgaySinh())) {
			JOptionPane.showMessageDialog(null, "Sai ngày sinh \nhoặc sai định dạng ngày sinh: dd-MM-yyyy", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(!Check.checkPhone(nv.getSoDT())) {
			JOptionPane.showMessageDialog(null, "Số điện thoại chỉ chứa chữ số\n Độ dài quy định: 9 -> 12 chữ số", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	private void add() {
		NhanVien nv = getNhanVien();
		if (nv != null) {
			if(!checkTTinNV(nv)) {
				return;
			}
			boolean ck = myConn.insert("NhanVien", null, null, nv, null, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Thêm thành công");
				cancel();
			} else {
				JOptionPane.showMessageDialog(null, "Trùng mã nhân viên", "Error insert", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void update() {
		int row = table.getSelectedRow();
		if(row < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return;
		}
		NhanVien nv = getNhanVien();
		if (nv != null) {
			if(!checkTTinNV(nv)) {
				return;
			}
			boolean ck = myConn.update(nv.getIdNhanVien(), null, null, nv, null, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Cập nhật thành công");
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
			boolean ck = myConn.deleteIDRef("NhanVien", "idNhanVien", (String) table.getValueAt(row, 0));
			loadData("All", "");
			if (ck)
				JOptionPane.showMessageDialog(null, "Xóa thành công");
		}
	}

	private void cancel() {
		tfIdNV.setText("");
		tfIdNV.setEnabled(true);
		tfTenNV.setText("");
		tfNgaySinhNV.setText("");
		tfDiaChiNV.setText("");
		tfSdtNV.setText("");
		gt = null;
		radNam.setSelected(false);
		radNu.setSelected(false);
		tfTimKiem.setText("");
		timKiemCB.setSelectedIndex(0);
		thongKeCB.setSelectedIndex(0);
		table.getSelectionModel().clearSelection();
		btnThem.setEnabled(true);
	}

	private String[] getSearch() {
		String[] arr = new String[2];
		arr[0] = timKiemCB.getSelectedItem().toString().trim();
		arr[1] = tfTimKiem.getText().toString().trim();
		return arr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == radNam) {
			if(radNu.isSelected()) radNu.setSelected(false);
			gt = "Nam";
		}
		if(e.getSource() == radNu) {
			if(radNam.isSelected()) radNam.setSelected(false);
			gt = "Nữ";
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
		if (e.getSource() == btnCancel) {
			cancel();
			return;
		}
		if (e.getSource() == btnTimKiem) {
			String arr[] = getSearch();
			loadData(arr[0], arr[1]);
			return;
		}
		if (e.getSource() == btnThongKe) {
			loadVar();
			return;
		}
		if (e.getSource() == btnThemFile) {
			imp = new ImportFile();
			int returnVal = fileDialog.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = fileDialog.getCurrentDirectory().toString() + "\\"
						+ fileDialog.getSelectedFile().getName();
				try {
					imp.importFileNV(path, myConn);
					loadData("All", "");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				System.out.println("\ncanceled.");
				return;
			}

			return;
		}
		if (e.getSource() == btnXuatFile) {
			ef = new ExportFile();
			int returnVal = fileDialog.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = fileDialog.getCurrentDirectory().toString() + "\\"
						+ fileDialog.getSelectedFile().getName();
				if (path.indexOf(".docx") >= 0) {
					exFile = path;
				} else {
					exFile = path + ".docx";
				}
				System.out.println(exFile);
			} else {
				System.out.println("\ncanceled.");
				return;
			}

			XWPFDocument doc = new XWPFDocument();
			try {
				String infoNV = "";
				String search = "";
				if (table.getModel().getColumnCount() > 3) {
					infoNV = "Thông Tin Nhân Viên";
					if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
						search = "";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idNhanVien")) {
						search = "Tìm Kiếm Theo Mã Nhân Viên";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenNV")) {
						search = "Tìm Kiếm Theo Tên Nhân Viên";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgaySinh")) {
						search = "Tìm Kiếm Theo Ngày Sinh";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("DiaChi")) {
						search = "Tìm Kiếm Theo Địa Chỉ";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("GioiTinh")) {
						search = "Tìm Kiếm Theo Giới Tính";
					}
				} else {
					infoNV = "Thống Kê Nhân Viên Theo " + table.getModel().getColumnName(1);
				}
				ef.printHeader(exFile, doc, infoNV, search);
				ef.printContentNV(exFile, doc, table);
				ef.printEnd(exFile, doc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		if(row >= 0) {
			tfIdNV.setText((String) table.getValueAt(row, 0));
			tfIdNV.setEnabled(false);
			tfTenNV.setText((String) table.getValueAt(row, 1));
			tfNgaySinhNV.setText((String) table.getValueAt(row, 2));
			if(table.getValueAt(row, 3).equals("Nam")) {
				radNam.setSelected(true);
				radNu.setSelected(false);
			}
			else {
				radNu.setSelected(true);
				radNam.setSelected(false);
			}
			tfDiaChiNV.setText((String) table.getValueAt(row, 4));
			tfSdtNV.setText((String) table.getValueAt(row, 5));
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
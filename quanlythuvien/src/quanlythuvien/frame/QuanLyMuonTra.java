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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import quanlythuvien.object.ChiTietMuonTra;
import quanlythuvien.object.MuonTra;

public class QuanLyMuonTra extends JPanel implements ActionListener, MouseListener {
	private String titleCol[] = { "Mã mượn trả", "Mã độc giả", "Mã nhân viên", "Ngày mượn", "Ngày hẹn trả", "Đặt cọc" };
	private String titleCol1[] = { "Mã mượn trả", "Mã sách", "Ngày trả", "Tiền phạt" };
	private String itemsTable[] = { "Mượn trả", "Chi tiết mượn trả" };
	private JTable table1, table2;
	final JFileChooser fileDialog = new JFileChooser();
	private String exFile;
	private ExportFile ef = new ExportFile();
	private ImportFile imp;
	private JButton btnThem, btnXuatFile, btnCancel, btnSua, btnXoa, btnTimKiem, btnThongKe, btnInPhieu, btnNhapFile;
	private JComboBox<String> timKiemCB, thongKeCB, idNhanVienCB, idDocGiaCB, tableCB;
	private String[] timKiemVal = { "All", "idMuonTra", "idDocGia", "idNhanVien", "NgayMuon" };
	private String[] thongKeVal = { "idDocGia", "idNhanVien", "NgayMuon", "đặt cọc - DG", "đặt cọc - NV", "tiền phạt - DG", "tiền phạt - NV"};
	private JTextField tfIdMT, tfTimKiem, tfNgayMuon, tfNgayHenTra, tfDatCoc, tfIdSach, tfNgayTra;
	private JLabel labTenDG = new JLabel(""), labTenNV = new JLabel("");
	MyConnectDB myConn;
	private Color color = new Color(0x009999);

	public QuanLyMuonTra(MyConnectDB connectDB) {
		myConn = connectDB;

		setLayout(new BorderLayout());
		add(createMainPanel());
		loadData("All", "");
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitlePanel("Quản lý mượn trả"), BorderLayout.PAGE_START);
		panel.add(createGridPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createGridPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 0, 0));
		panel.setBackground(color);
		panel.add(createTable1Panel());
		panel.add(createBottomPanel());

		return panel;

	}

	private JPanel createTitlePanel(String title) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(title);
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		;
		panel.add(label);
		panel.setBackground(color);

		return panel;
	}

	private JPanel createTable1Panel() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(new EmptyBorder(5, 50, 10, 50));
		panelMain.setBackground(color);
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder(null, ""));
		panel1.add(new JScrollPane(table1 = createTable()));
		panelMain.add(panel1);

		return panelMain;
	}

	private JPanel createTable2Panel() {
		JPanel panelMain = new JPanel(new BorderLayout());
		panelMain.setBorder(new EmptyBorder(0, 50, 5, 30));
		panelMain.setBackground(color);
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBorder(new TitledBorder(null, ""));
		panel1.add(new JScrollPane(table2 = createTable()));
		panelMain.add(panel1);

		return panelMain;
	}

	private JTable createTable() {
		JTable table = new JTable();
		table.addMouseListener(this);
		return table;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel.add(createTableBottomLeftPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);

		return panel;
	}

	private JPanel createTableBottomLeftPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 0, 15));
		panel.add(createCTMTPanel());
		panel.add(createInputPanel());

		return panel;
	}

	private JPanel createCTMTPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.add(createTitlePanel("Chi tiết mượn trả"), BorderLayout.PAGE_START);
		panel.add(createTable2Panel());

		return panel;
	}

	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(5, 50, 0, 30));
		panel.add(createInputPanelL());
		panel.add(createInputPanelR());

		return panel;
	}

	private JComboBox<String> createCBBoxID(String table) {
		JComboBox<String> cb = new JComboBox<String>();
		if (table.equals("NhanVien")) {
			ResultSet rs = myConn.getID("NhanVien", "idNhanVien");
			try {
				while (rs.next()) {
					cb.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (table.equals("DocGia")) {
			ResultSet rs = myConn.getID("DocGia", "idDocGia");
			try {
				while (rs.next()) {
					cb.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		cb.addActionListener(this);
		return cb;
	}

	private JPanel createInputPanelL() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		JPanel panelLeft = new JPanel(new GridLayout(5, 1, 5, 5));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Mã mượn trả:"));
		panelLeft.add(createLabel("Mã độc giả:"));
		panelLeft.add(createLabel("Mã nhân viên:"));
		panelLeft.add(createLabel("Mã sách:"));

		JPanel panelRight = new JPanel(new GridLayout(5, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfIdMT = new JTextField());
		panelRight.add(createPanelLabel("DocGia"));
		panelRight.add(createPanelLabel("NhanVien"));
		panelRight.add(tfIdSach = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createPanelLabel(String table) {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(color);
		if (table.equals("DocGia")) {
			panel.add(idDocGiaCB = createCBBoxID("DocGia"), BorderLayout.WEST);
			panel.add(labTenDG = new JLabel(""), BorderLayout.CENTER);
			labTenDG.setForeground(Color.white);
		} else {
			panel.add(idNhanVienCB = createCBBoxID("NhanVien"), BorderLayout.WEST);
			panel.add(labTenNV = new JLabel(""), BorderLayout.CENTER);
			labTenNV.setForeground(Color.white);
		}

		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		JPanel panelLeft = new JPanel(new GridLayout(5, 1, 5, 5));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Ngày mượn:"));
		panelLeft.add(createLabel("Ngày hẹn trả:"));
		panelLeft.add(createLabel("Đặt cọc:"));
		panelLeft.add(createLabel("Ngày trả:"));

		JPanel panelRight = new JPanel(new GridLayout(5, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfNgayMuon = new JTextField());
		panelRight.add(tfNgayHenTra = new JTextField());
		panelRight.add(tfDatCoc = new JTextField());
		panelRight.add(tfNgayTra = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 15, 50));
		panel.add(createTKTKPanel());
		panel.add(createButPanel());

		return panel;
	}

	private JPanel createTKTKPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
		panel.setBorder(new EmptyBorder(35, 0, 0, 0));
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
		tableCB = new JComboBox<String>(itemsTable);
		tableCB.setSelectedIndex(0);
		panel.add(tableCB);

		return panel;
	}

	private JPanel createButPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(createButAUD());
		panel.add(createButCO());
		panel.add(createButXN());

		return panel;
	}

	private JPanel createButAUD() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel.add(btnThem = createButton("Thêm"));
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/add.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/update1.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/delete.png")));

		return panel;
	}

	private JPanel createButCO() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 5));
		panel.setBorder(new EmptyBorder(5, 30, 0, 30));
		panel.setBackground(color);
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnInPhieu = createButton("In phiếu"));
		btnInPhieu.setToolTipText("In phiếu");
		btnInPhieu.setIcon(new ImageIcon(this.getClass().getResource("/print.png")));

		return panel;
	}
	
	private JPanel createButXN() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 10, 5));
		panel.setBorder(new EmptyBorder(5, 30, 0, 30));
		panel.setBackground(color);
		panel.add(btnXuatFile = createButton("Xuất file"));
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/print.png")));
		btnXuatFile.setToolTipText("Xuất file");
		panel.add(btnNhapFile = createButton("Nhập file"));
		btnNhapFile.setIcon(new ImageIcon(this.getClass().getResource("/nf.png")));
		
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
		DefaultTableModel model2 = new DefaultTableModel();

		ResultSet rs = myConn.getDataID("MuonTra", "idMuonTra", Cot, muonTim);
		String arr[] = new String[6];
		try {
			while (rs.next()) {
				for (int i = 0; i < arr.length; i++) {
					if (i == 0) {
						model2.setColumnIdentifiers(titleCol1);
						ResultSet rs2 = myConn.getDataID("ChiTietMuonTra", "idMuonTra", "idMuonTra",
								rs.getString(i + 1));
						String arr2[] = new String[4];
						while (rs2.next()) {
							for (int j = 0; j < arr2.length; j++) {
								arr2[j] = rs2.getString(j + 1);
							}
							model2.addRow(arr2);
						}
					}
					arr[i] = rs.getString(i + 1);
				}
				table2.setModel(model2);
				model.addRow(arr);
			}
			if (!rs.next()) {
				model2.setColumnIdentifiers(titleCol1);
				table2.setModel(model2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table1.setModel(model);
	}

	private void loadDataCTMT(String muonTim) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCol1);
		ResultSet rs = null;
		if (muonTim.equals("")) {
			rs = myConn.getDataID("ChiTietMuonTra", "idMuonTra", "All", "");
		} else {
			rs = myConn.getDataID("ChiTietMuonTra", "idMuonTra", "idMuonTra", muonTim);
		}
		String arr2[] = new String[4];
		try {
			while (rs.next()) {
				for (int j = 0; j < arr2.length; j++) {
					arr2[j] = rs.getString(j + 1);
				}
				model.addRow(arr2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table2.setModel(model);
	}

	private String[] getTK() {
		String[] arr = new String[2];
		String tmp = thongKeCB.getSelectedItem().toString().trim();
		if (tmp.equals("idDocGia")) {
			arr[0] = tmp;
			arr[1] = "Mã Độc Giả";
		}
		if (tmp.equals("idNhanVien")) {
			arr[0] = tmp;
			arr[1] = "Mã Nhân Viên";
		}
		if (tmp.equals("NgayMuon")) {
			arr[0] = tmp;
			arr[1] = "Ngày Mượn";
		}
		if(tmp.equals("đặt cọc - DG")) {
			arr[0] = "idDocGia";
			arr[1] = "DatCoc";
		}
		if(tmp.equals("đặt cọc - NV")) {
			arr[0] = "idNhanVien";
			arr[1] = "DatCoc";
		}
		if(tmp.equals("tiền phạt - DG")) {
			arr[0] = "idDocGia";
			arr[1] = "TienPhat";
		}
		if(tmp.equals("tiền phạt - NV")) {
			arr[0] = "idNhanVien";
			arr[1] = "TienPhat";
		}

		return arr;
	}

	private String[] getSearch() {
		String[] arr = new String[2];
		arr[0] = timKiemCB.getSelectedItem().toString().trim();
		arr[1] = tfTimKiem.getText().toString().trim();
		return arr;
	}

	// Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK();
		ResultSet rs = null;
		if(str[1].equals("DatCoc")) {
			String[] titleVar = { "TT", str[0].equals("idDocGia")?"Mã độc giả":"Mã nhân viên", "Tổng tiền cọc"};
			model.setColumnIdentifiers(titleVar);
			rs = myConn.getVar(str[0], "");
		}else if(str[1].equals("TienPhat")){
			String[] titleVar = {"TT", str[0].equals("idDocGia")?"Mã độc giả":"Mã nhân viên", "Tổng tiền phạt"};
			model.setColumnIdentifiers(titleVar);
			rs = myConn.getVar(str[0], "TienPhat");
		}else {
			String[] titleVar = { "TT", str[1], "Số lượng"};
			model.setColumnIdentifiers(titleVar);
			rs = myConn.getVar("MuonTra", "idMuonTra", str[0]);
		}

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
		table1.setModel(model);
		cancel();
	}

	private MuonTra getMuonTra() {
		String id = tfIdMT.getText().trim().toUpperCase();
		String idDG = idDocGiaCB.getSelectedItem().toString().trim();
		String idNV = idNhanVienCB.getSelectedItem().toString().trim();
		String ngaymuon = tfNgayMuon.getText().trim();
		String ngayhentra = tfNgayHenTra.getText().trim();
		String tmp = tfDatCoc.getText().trim();
		if (id.equals("") || idDG.equals("") || idNV.equals("") || ngaymuon.equals("") || ngayhentra.equals("")
				|| tmp.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		if (!Check.checkInt(tmp)) {
			JOptionPane.showMessageDialog(null, "Tiền đặt cọc phải là số nguyên", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
		int datcoc = Integer.parseInt(tmp);
		MuonTra mt = new MuonTra(id, idDG, idNV, ngaymuon, ngayhentra, datcoc);

		return mt;
	}

	private ChiTietMuonTra getCTMT() {
		String idMT = tfIdMT.getText().trim().toUpperCase();
		String idS = tfIdSach.getText().trim();
		String ngayTra = tfNgayTra.getText().trim();
		if (idMT.equals("") || idS.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		String ngayMuon = "", ngayHenTra = "";
		int datCoc = 45000;
		
		ResultSet rs = myConn.getDataID("MuonTra", "idMuonTra", "idMuonTra", idMT);
		try {
			if(rs.next()) {
				ngayMuon = rs.getString(4);
				ngayHenTra = rs.getString(5);
				datCoc = Integer.parseInt(rs.getString(6));
				System.out.println("ngaymuon: " + ngayMuon);
				System.out.println("ngayhentra: " + ngayHenTra);
				System.out.println("datcoc: " + datCoc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		int tienphat = Check.getTienPhat(ngayMuon, ngayHenTra, ngayTra, datCoc);
		System.out.println("Tien phat: " + tienphat);
		if(tienphat == -1) {
			JOptionPane.showMessageDialog(null, "Ngày trả sai \nĐịnh dạng ngày: dd-MM-yyyy", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		
		ChiTietMuonTra ctmt = new ChiTietMuonTra(idMT, idS, ngayTra, tienphat);
		return ctmt;
	}

	private boolean checkMT(MuonTra mt) {
		if (!Check.checkDate(mt.getNgayMuon())) {
			JOptionPane.showMessageDialog(null, "Sai ngày mượn \nHoặc sai định dạng ngày mượn: dd-MM-yyyy", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (!Check.checkDateMT(mt.getNgayMuon(), mt.getNgayHenTra())) {
			JOptionPane.showMessageDialog(null,
					"Sai ngày hẹn trả \nHoặc sai định dạng ngày hẹn trả: dd-MM-yyyy \nNgày hẹn trả tối đa 6 tháng từ ngày mượn",
					"Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void add() {
		ChiTietMuonTra ctmt = null;
		MuonTra mt = null;
		if(tableCB.getSelectedItem().equals("Mượn trả"))
			mt = getMuonTra();
		else ctmt = getCTMT();
		if (mt != null) {
			if (!checkMT(mt))
				return;
			boolean ck = myConn.insert("MuonTra", null, null, null, mt, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Thêm mượn trả thành công");
				cancel();
			} else
				JOptionPane.showMessageDialog(null, "Trùng khóa mượn trả", "Error insert", JOptionPane.ERROR_MESSAGE);
		} else if (ctmt != null) {
			boolean ck = myConn.insert("ChiTietMuonTra", null, null, null, null, ctmt);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Thêm chi tiết mượn trả thành công");
				cancel();
				tableCB.setSelectedIndex(1);
				tfIdMT.setText(ctmt.getIdMuonTra());
				
			} else
				JOptionPane.showMessageDialog(null, "Trùng khóa chi tiết mượn trả \nhoặc mã mượn trả không tồn tại",
						"Error insert", JOptionPane.ERROR_MESSAGE);
		} 
	}

	private void update() {
		int row = table1.getSelectedRow();
		int row1 = table2.getSelectedRow();
		if (row < 0 && row1 < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return;
		}
		MuonTra mt = null;
		ChiTietMuonTra ctmt = null;
		if(row >= 0 && row1 < 0)
			mt = getMuonTra();
		else ctmt = getCTMT();
		boolean ck = false;
		if (mt != null) {
			if (!checkMT(mt))
				return;
			ck = myConn.update(mt.getIdMuonTra(), null, null, null, mt, null);
		} else if (ctmt != null) {
			ck = myConn.update(ctmt.getIdMuonTra(), null, null, null, null, ctmt);
		}
		if (ck) {
			loadData("All", "");
			JOptionPane.showMessageDialog(null, "Cập nhật thành công");
			cancel();
		}
	}

	private void delete() {
		int row = table1.getSelectedRow();
		int row1 = table2.getSelectedRow();

		if ((row < 0 && row1 < 0)) {
			System.out.println("Error delete");
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Delete",
				JOptionPane.YES_NO_OPTION);
		if (select == 0) {
			boolean ck = false;
			if (row1 >= 0 || (row >= 0 && row1 >= 0)) {
				ck = myConn.deleteCTMT((String) table2.getValueAt(row1, 0), (String) table2.getValueAt(row1, 1));
			} else if (row >= 0) {
				ck = myConn.deleteIDRef("MuonTra", "idMuonTra", (String) table1.getValueAt(row, 0));
			}
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Xóa thành công");
				cancel();
			}
		}
	}

	private void cancel() {
		tfIdMT.setText("");
		tfIdMT.setEnabled(true);
		idDocGiaCB.setSelectedIndex(0);
		idNhanVienCB.setSelectedIndex(0);
		labTenNV.setText("");
		labTenDG.setText("");
		tfNgayMuon.setText("");
		tfNgayHenTra.setText("");
		tfDatCoc.setText("");
		tfIdSach.setText("");
		tfIdSach.setEnabled(true);
		tfNgayTra.setText("");
		tfTimKiem.setText("");
		tableCB.setSelectedIndex(0);
		table1.getSelectionModel().clearSelection();
		table2.getSelectionModel().clearSelection();
		btnThem.setEnabled(true);
	}

	private void changeLabel(String name, int choose) {
		if (choose == 1) {
			ResultSet rs = myConn.getNames("DocGia", "tenDG", "idDocGia", name);
			try {
				while (rs.next()) {
					labTenDG.setText(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ResultSet rs = myConn.getNames("NhanVien", "tenNV", "idNhanVien", name);
			try {
				while (rs.next()) {
					labTenNV.setText(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			loadDataCTMT("");
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
		if (e.getSource() == idDocGiaCB) {
			changeLabel(idDocGiaCB.getSelectedItem().toString(), 1);
			return;
		}
		if (e.getSource() == idNhanVienCB) {
			changeLabel(idNhanVienCB.getSelectedItem().toString(), 0);
			return;
		}
		if(e.getSource() == btnNhapFile) {
			imp = new ImportFile();
			int returnVal = fileDialog.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = fileDialog.getCurrentDirectory().toString() + "\\"
						+ fileDialog.getSelectedFile().getName();
				try {
					imp.importFileMuonTra(path, myConn);
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
		if (e.getSource() == btnInPhieu) {

			int row = table1.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "Cần chọn hàng trong bảng mượn trả chứa mã phiếu đó để in", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
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
				ef.printHeader(exFile, doc, "Phiếu Mượn Trả", "");
				ef.printContentPhieuMT(exFile, doc, table1, row, myConn);
				ef.printEnd(exFile, doc);
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		if (e.getSource() == btnXuatFile) {
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
				String infoMT = "";
				String search = "";
				if (table1.getModel().getColumnCount() > 3) {
					infoMT = "Thông Tin Mượn Trả";
					if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
						search = "";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idMuonTra")) {
						search = "Tìm Kiếm Theo Mã Mượn Trả";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idDocGia")) {
						search = "Tìm Kiếm Theo Mã Độc Giả";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgayMuon")) {
						search = "Tìm Kiếm Theo Ngày Mượn";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgayTra")) {
						search = "Tìm Kiếm Theo Ngày Trả";
					}
				} else {
					if(table1.getModel().getColumnName(2).equals("Tổng tiền cọc")) {
						infoMT = "Thống Kê mượn trả"; 
						search = "Theo " + table1.getModel().getColumnName(1) + " - Tiền Cọc";
					}else if(table1.getModel().getColumnName(2).equals("Tổng tiền phạt")){
						infoMT = "Thống Kê mượn trả"; 
						search = "Theo " + table1.getModel().getColumnName(1) + " - Tiền phạt";
					}else {
						infoMT = "Thống Kê Mượn Trả Theo " + table1.getModel().getColumnName(1);
					}
				}
				ef.printHeader(exFile, doc, infoMT, search);
				ef.printContentMT(exFile, doc, table1);
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
	public void mouseClicked(MouseEvent e) {
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
		int row = table1.getSelectedRow();
		int row1 = table2.getSelectedRow();
		if (e.getSource() == table1 && table1.getModel().getColumnCount() > 3) {
			if (row >= 0) {
				table2.getSelectionModel().clearSelection();
				loadDataCTMT((String) table1.getValueAt(row, 0));
				tfIdMT.setText((String) table1.getValueAt(row, 0));
				tfIdMT.setEnabled(false);
				idDocGiaCB.setSelectedItem(table1.getValueAt(row, 1));
				idNhanVienCB.setSelectedItem(table1.getValueAt(row, 2));
				tfNgayMuon.setText((String) table1.getValueAt(row, 3));
				tfNgayHenTra.setText((String) table1.getValueAt(row, 4));
				tfDatCoc.setText((String) table1.getValueAt(row, 5));
				btnThem.setEnabled(false);
			}
		} else if (e.getSource() == table2) {
			if (row1 >= 0) {
				tfIdMT.setText((String) table2.getValueAt(row1, 0));
				tfIdMT.setEnabled(false);
				tfIdSach.setText((String) table2.getValueAt(row1, 1));
				tfIdSach.setEnabled(false);
				tfNgayTra.setText((String) table2.getValueAt(row1, 2));
				btnThem.setEnabled(false);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

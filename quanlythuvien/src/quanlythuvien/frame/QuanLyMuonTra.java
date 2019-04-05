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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import quanlythuvien.connect.Check;
import quanlythuvien.connect.ExportFile;
import quanlythuvien.connect.ImportFile;
import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.MuonTra;

public class QuanLyMuonTra extends JPanel implements ActionListener, MouseListener {
	private String titleCol[] = { "Mã mượn trả", "Mã độc giả", "Mã nhân viên", "Ngày mượn", "Ngày hẹn trả", "Đặt cọc" };
	private JTable table, tableSach;
	final JFileChooser fileDialog = new JFileChooser();
	private String exFile;
	private ExportFile ef = new ExportFile();
	private ImportFile imp;
	private JButton btnThem, btnXuatFile, btnCancel, btnSua, btnXoa, btnTimKiem, btnThongKe, btnNhapFile, btnXemCTMT;
	private JComboBox<String> timKiemCB, thongKeCB, idNhanVienCB, idDocGiaCB;
	private String[] timKiemVal = { "All", "idMuonTra", "idDocGia", "idNhanVien", "NgayMuon" };
	private String[] thongKeVal = { "idDocGia", "idNhanVien", "NgayMuon", "đặt cọc - DG", "đặt cọc - NV",
			"tiền phạt - DG", "tiền phạt - NV" };
	private JTextField tfIdMT, tfTimKiem, tfNgayMuon, tfNgayHenTra, tfDatCoc;
	private JLabel labTenDG = new JLabel(""), labTenNV = new JLabel("");
	MyConnectDB myConn;
	private Color color = new Color(0x009999);

	public QuanLyMuonTra(MyConnectDB connectDB, JTable tableSach) {
		myConn = connectDB;
		this.tableSach = tableSach;
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
		panel.add(createTablePanel());
		panel.add(createBottomPanel());

		return panel;

	}

	private JPanel createTitlePanel(String title) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(title);
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
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
		panel1.add(new JScrollPane(table = createTable()));
		panelMain.add(panel1);

		return panelMain;
	}

	private JTable createTable() {
		JTable table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(this);
		return table;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 50, 10, 50));
		panel.setBackground(color);
		panel.add(createInputPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);

		return panel;
	}

	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 140, 30));
		panel.add(createInputPanelL());
		panel.add(createInputPanelR());

		return panel;
	}

	private JComboBox<String> createCBBoxID(String table) {
		JComboBox<String> cb = new JComboBox<String>();
		if (table.equals("NhanVien")) {
			ResultSet rs = myConn.getID("nhanvien", "idNhanVien");
			try {
				while (rs.next()) {
					cb.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (table.equals("DocGia")) {
			ResultSet rs = myConn.getID("docgia", "idDocGia");
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
		JPanel panelLeft = new JPanel(new GridLayout(4, 1, 5, 5));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Mã mượn trả:"));
		panelLeft.add(createLabel("Mã độc giả:"));
		panelLeft.add(createLabel("Mã nhân viên:"));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfIdMT = new JTextField());
		panelRight.add(createPanelLabel("DocGia"));
		panelRight.add(createPanelLabel("NhanVien"));

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
		JPanel panelLeft = new JPanel(new GridLayout(4, 1, 5, 5));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Ngày mượn:"));
		panelLeft.add(createLabel("Ngày hẹn trả:"));
		panelLeft.add(createLabel("Đặt cọc:"));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfNgayMuon = new JTextField());
		panelRight.add(tfNgayHenTra = new JTextField());
		panelRight.add(tfDatCoc = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 65, 0));
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
		JPanel panel = new JPanel(new GridLayout(2, 1, 5, 15));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 25, 0));
		tfTimKiem = new JTextField();
		panel.add(tfTimKiem);

		return panel;
	}

	private JPanel createButPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(createButAUD());
		panel.add(createButCXN());
		panel.add(createButCTMT());

		return panel;
	}

	private JPanel createButAUD() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBackground(color);
		panel.add(btnThem = createButton("Thêm"));
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/add.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/update1.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/delete.png")));

		return panel;
	}

	private JPanel createButCXN() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBackground(color);
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnXuatFile = createButton("Xuất file"));
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/print.png")));
		btnXuatFile.setToolTipText("Xuất file");
		panel.add(btnNhapFile = createButton("Nhập file"));
		btnNhapFile.setIcon(new ImageIcon(this.getClass().getResource("/nf.png")));

		return panel;
	}

	private JPanel createButCTMT() {
		JPanel panel = new JPanel(new GridLayout(1, 1, 10, 5));
		panel.setBackground(color);
		panel.add(btnXemCTMT = createButton("Xem chi tiết mượn trả"));

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
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		model.setColumnIdentifiers(titleCol);
		DefaultTableModel model2 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		ResultSet rs = myConn.getDataID("muontra", "idMuonTra", Cot, muonTim);
		String arr[] = new String[titleCol.length];
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
		if (tmp.equals("đặt cọc - DG")) {
			arr[0] = "idDocGia";
			arr[1] = "DatCoc";
		}
		if (tmp.equals("đặt cọc - NV")) {
			arr[0] = "idNhanVien";
			arr[1] = "DatCoc";
		}
		if (tmp.equals("tiền phạt - DG")) {
			arr[0] = "idDocGia";
			arr[1] = "TienPhat";
		}
		if (tmp.equals("tiền phạt - NV")) {
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
		if (str[1].equals("DatCoc")) {
			String[] titleVar = { "TT", str[0].equals("idDocGia") ? "Mã độc giả" : "Mã nhân viên", "Tổng tiền cọc" };
			model.setColumnIdentifiers(titleVar);
			rs = myConn.getVar(str[0], "");
		} else if (str[1].equals("TienPhat")) {
			String[] titleVar = { "TT", str[0].equals("idDocGia") ? "Mã độc giả" : "Mã nhân viên", "Tổng tiền phạt" };
			model.setColumnIdentifiers(titleVar);
			rs = myConn.getVar(str[0], "TienPhat");
		} else {
			String[] titleVar = { "TT", str[1], "Số lượng" };
			model.setColumnIdentifiers(titleVar);
			rs = myConn.getVar("muontra", "idMuonTra", str[0]);
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
		table.setModel(model);
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
		MuonTra mt = null;
		mt = getMuonTra();
		if (mt != null) {
			if (!checkMT(mt))
				return;
			boolean ck = myConn.insert("muontra", null, null, null, mt, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Thêm mượn trả thành công");
				cancel();
			} else
				JOptionPane.showMessageDialog(null, "Trùng khóa mượn trả", "Error insert", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void update() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return;
		}
		MuonTra mt = null;
		mt = getMuonTra();
		boolean ck = false;
		if (mt != null) {
			if (!checkMT(mt))
				return;
			ck = myConn.update(mt.getIdMuonTra(), null, null, null, mt, null);
		}
		if (ck) {
			loadData("All", "");
			JOptionPane.showMessageDialog(null, "Cập nhật thành công");
			cancel();
		}
	}

	private void delete() {
		int row = table.getSelectedRow();

		if ((row < 0)) {
			System.out.println("Error delete");
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Delete",
				JOptionPane.YES_NO_OPTION);
		if (select == 0) {
			boolean ck = false;
			ck = myConn.deleteIDRef("muontra", "idMuonTra", (String) table.getValueAt(row, 0));
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
		tfTimKiem.setText("");
		table.getSelectionModel().clearSelection();
		btnThem.setEnabled(true);
	}

	private void changeLabel(String name, int choose) {
		if (choose == 1) {
			ResultSet rs = myConn.getNames("docgia", "tenDG", "idDocGia", name);
			try {
				while (rs.next()) {
					labTenDG.setText(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ResultSet rs = myConn.getNames("nhanvien", "tenNV", "idNhanVien", name);
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

	private void nhapFile() {
		imp = new ImportFile();
		int returnVal = fileDialog.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fileDialog.getCurrentDirectory().toString() + "\\" + fileDialog.getSelectedFile().getName();
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
	}

	private void xuatFile() {
		int returnVal = fileDialog.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fileDialog.getCurrentDirectory().toString() + "\\" + fileDialog.getSelectedFile().getName();
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
			if (table.getModel().getColumnCount() > 3) {
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
				if (table.getModel().getColumnName(2).equals("Tổng tiền cọc")) {
					infoMT = "Thống Kê mượn trả";
					search = "Theo " + table.getModel().getColumnName(1) + " - Tiền Cọc";
				} else if (table.getModel().getColumnName(2).equals("Tổng tiền phạt")) {
					infoMT = "Thống Kê mượn trả";
					search = "Theo " + table.getModel().getColumnName(1) + " - Tiền phạt";
				} else {
					infoMT = "Thống Kê Mượn Trả Theo " + table.getModel().getColumnName(1);
				}
			}
			ef.printHeader(exFile, doc, infoMT, search);
			ef.printContentMT(exFile, doc, table);
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

	private void xemCTMT() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn mượn trả để xem chi tiết mượn trả!!!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String idMT = (String) table.getValueAt(row, 0);
		String idDG = (String) table.getValueAt(row, 1);
		String idNV = (String) table.getValueAt(row, 2);
		String ngayM = (String) table.getValueAt(row, 3);
		String ngayHT = (String) table.getValueAt(row, 4);
		String datCoc = (String) table.getValueAt(row, 5);
		String[] muonTra = { idMT, idDG, idNV, ngayM, ngayHT, datCoc };

		new ChiTietMuonTra(myConn, muonTra, tableSach);
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
		if (e.getSource() == btnNhapFile) {
			nhapFile();
			return;
		}
		if (e.getSource() == btnXuatFile) {
			xuatFile();
			return;
		}
		if (e.getSource() == btnXemCTMT) {
			xemCTMT();
			return;
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
		int row = table.getSelectedRow();
		if (e.getSource() == table) {
			if (row >= 0) {
				tfIdMT.setText((String) table.getValueAt(row, 0));
				tfIdMT.setEnabled(false);
				idDocGiaCB.setSelectedItem(table.getValueAt(row, 1));
				idNhanVienCB.setSelectedItem(table.getValueAt(row, 2));
				tfNgayMuon.setText((String) table.getValueAt(row, 3));
				tfNgayHenTra.setText((String) table.getValueAt(row, 4));
				tfDatCoc.setText((String) table.getValueAt(row, 5));
				btnThem.setEnabled(false);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

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

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import quanlythuvien.object.DocGia;

public class QuanLyDocGia extends JPanel implements ActionListener, MouseListener {
	private String titleCol[] = { "Mã độc giả", "Họ tên", "Ngày sinh", "Giới tính", "Email", "Số điện thoại",
			"Địa chỉ" };
	private JTable table;
	final JFileChooser fileDialog = new JFileChooser();
	private String exFile;
	private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThongKe, btnXuatFile, btnThemFile, btnCancel;
	private JComboBox<String> timKiemCB, thongKeCB;
	ButtonGroup bg;
	private JRadioButton radNam, radNu;
	private String[] timKiemVal = { "All", "idDocGia", "TenDG", "NgaySinh", "DiaChi", "GioiTinh", "Email", "SoDT" };
	private String[] thongKeVal = { "TenDG", "GioiTinh", "DiaChi", "NgaySinh"};
	private JTextField tfIdDG, tfTenDG, tfNgaySinhDG, tfEmail, tfSdtDG, tfDiaChi, tfTimKiem;
	MyConnectDB myConn;
	private ExportFile ef;
	private ImportFile imp;
	private Color color = new Color(0x009999);

	public QuanLyDocGia(MyConnectDB connectDB) {
		myConn = connectDB;

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
		JLabel label = new JLabel("Thông tin độc giả");
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
		panel.setBorder(new EmptyBorder(10, 0, 140, 20));
		JPanel panelLeft = new JPanel(new GridLayout(4, 1));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Mã độc giả:"));
		panelLeft.add(createLabel("Ngày sinh:"));
		panelLeft.add(createLabel("Email:"));
		panelLeft.add(createLabel("Địa chỉ:"));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfIdDG = new JTextField());
		panelRight.add(tfNgaySinhDG = new JTextField());
		panelRight.add(tfEmail = new JTextField());
		panelRight.add(tfDiaChi = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 140, 20));
		JPanel panelLeft = new JPanel(new GridLayout(4, 1));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Tên độc giả:"));
		panelLeft.add(createLabel("Giới tính:"));
		panelLeft.add(createLabel("Số điện thoại:"));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfTenDG = new JTextField());
		panelRight.add(panelGioiTinh());
		panelRight.add(tfSdtDG = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}
	
	private JPanel panelGioiTinh() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 0, 135));
		bg = new ButtonGroup();
		panel.add(radNam = createRadio("Nam"));
		panel.add(radNu = createRadio("Nữ"));
		bg.add(radNam);
		bg.add(radNu);
		
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
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/print.png")));
		btnXuatFile.setToolTipText("Xuất fle");

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

		ResultSet rs = myConn.getDataID("docgia", "idDocGia", Cot, muonTim);
		String arr[] = new String[7];
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
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(165);
		table.getColumnModel().getColumn(4).setPreferredWidth(165);
		table.getColumnModel().getColumn(6).setPreferredWidth(175);
	}

	private String[] getTK() {
		String[] arr = new String[2];
		arr[0] = thongKeCB.getSelectedItem().toString().trim();
		if (arr[0].equals("TenDG"))
			arr[1] = "Tên độc giả";
		if (arr[0].equals("GioiTinh"))
			arr[1] = "Giới tính";
		if (arr[0].equals("DiaChi"))
			arr[1] = "Địa chỉ";

		return arr;
	}

	// Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK();
		String[] titleVar = { "TT", str[1], "Số lượng" };
		model.setColumnIdentifiers(titleVar);
		ResultSet rs = null;
		rs = myConn.getVar("docgia", "idDocGia", str[0]);

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
	}

	private DocGia getDocGia() {
		String id = tfIdDG.getText().trim().toUpperCase();
		String ten = tfTenDG.getText().trim();
		String ns = tfNgaySinhDG.getText().trim();
		String email = tfEmail.getText().trim();
		String sdt = tfSdtDG.getText().trim();
		String dc = tfDiaChi.getText().trim();
		String gt = null;
		if(radNam.isSelected()) gt = "Nam";
		else if(radNu.isSelected()) gt = "Nữ";
		if (id.equals("") || ten.equals("") || ns.equals("") || gt == null || email.equals("") || sdt.equals("")
				|| dc.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		DocGia dg = new DocGia(id, ten, ns, gt, email, sdt, dc);
		return dg;
	}

	private boolean checkTTinDG(DocGia dg) {
		if(!Check.checkDate(dg.getNgaySinh())) {
			JOptionPane.showMessageDialog(null, "Sai ngày sinh \nhoặc sai định dạng ngày sinh: dd-MM-yyyy", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(!Check.checkPhone(dg.getSoDT())) {
			JOptionPane.showMessageDialog(null, "Số điện thoại chỉ chứa chữ số\n Độ dài quy đinh: 9->12 chữ số", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void add() {
		DocGia dg = getDocGia();
		if (dg != null) {
			if(!checkTTinDG(dg)) {
				return;
			}
			boolean ck = myConn.insert("docgia", null, dg, null, null, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Thêm thành công");
				cancel();
			} else {
				JOptionPane.showMessageDialog(null, "Trùng mã độc giả", "Error insert", JOptionPane.ERROR_MESSAGE);
			}
		} 
	}

	private void update() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return;
		}
		DocGia dg = getDocGia();
		if (dg != null) {
			if(!checkTTinDG(dg)) {
				return;
			}
			boolean ck = myConn.update(dg.getIdDocGia(), null, dg, null, null, null);
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
			boolean ck = myConn.deleteIDRef("docgia", "idDocGia", (String) table.getValueAt(row, 0));
			loadData("All", "");
			if (ck) {
				JOptionPane.showMessageDialog(null, "Xóa thành công");
				cancel();
			}
		}
	}

	private void cancel() {
		tfIdDG.setText("");
		tfIdDG.setEnabled(true);
		tfTenDG.setText("");
		tfDiaChi.setText("");
		bg.clearSelection();
		tfNgaySinhDG.setText("");
		tfSdtDG.setText("");
		tfEmail.setText("");
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
	
	private void nhapFile() {
		imp = new ImportFile();
		int returnVal = fileDialog.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = fileDialog.getCurrentDirectory().toString() + "\\"
					+ fileDialog.getSelectedFile().getName();
			try {
				imp.importFileDG(path, myConn);
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
			String infoDG = "";
			String search = "";
			if (table.getModel().getColumnCount() > 3) {
				infoDG = "Thông Tin ĐỘc giả";
				if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("All")) {
					search = "";
				} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idDocGia")) {
					search = "TÌm Kiếm Theo Mã Độc Giả";
				} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenDG")) {
					search = "TÌm Kiếm Theo Tên Độc Giả";
				} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgaySinh")) {
					search = "TÌm Kiếm Theo Ngày Sinh";
				} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("DiaChi")) {
					search = "TÌm Kiếm Theo Địa Chỉ";
				} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("Giới Tính")) {
					search = "TÌm Kiếm Theo Giới Tính";
				}
			} else {
				infoDG = "Thống Kê Độc Giả Theo " + table.getModel().getColumnName(1);
			}
			ef.printHeader(exFile, doc, infoDG, search);
			ef.printContentDG(exFile, doc, table);
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
		if (e.getSource() == btnThemFile) {
			nhapFile();
			return;
		}
		if (e.getSource() == btnXuatFile) {
			xuatFile();
			return;
		}
		if (e.getSource() == btnThongKe) {
			loadVar();
			return;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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
		if(row >= 0) {
			tfIdDG.setText((String) table.getValueAt(row, 0));
			tfIdDG.setEnabled(false);
			tfTenDG.setText((String) table.getValueAt(row, 1));
			tfNgaySinhDG.setText((String) table.getValueAt(row, 2));
			if(table.getValueAt(row, 3).equals("Nam")) {
				radNam.setSelected(true);
				radNu.setSelected(false);
			}
			else {
				radNu.setSelected(true);
				radNam.setSelected(false);
			}
			tfEmail.setText((String) table.getValueAt(row, 4));
			tfSdtDG.setText((String) table.getValueAt(row, 5));
			tfDiaChi.setText((String) table.getValueAt(row, 6));
			btnThem.setEnabled(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

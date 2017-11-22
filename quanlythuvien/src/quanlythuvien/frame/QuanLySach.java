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
import org.apache.xmlbeans.impl.piccolo.xml.EntityManager;

import quanlythuvien.connect.Check;
import quanlythuvien.connect.ExportFile;
import quanlythuvien.connect.ImportFile;
import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.Sach;

public class QuanLySach extends JPanel implements ActionListener, MouseListener {
	private String titleCol[] = { "Mã sách", "Tên sách", "Nhà xuất bản", "Tên tác giả", "Năm xuất bản", "Giá sách",
			"Thể loại", "Ngôn ngữ" };
	private JTable table;
	final JFileChooser fileDialog = new JFileChooser();
	private String exFile;
	private ExportFile ef;
	private ImportFile imp;
	private JButton btnThem, btnCancel, btnSua, btnXoa, btnTimKiem, btnThongKe, btnNhapFile, btnXuatFile;
	private JComboBox<String> timKiemCB, thongKeCB;
	private String[] timKiemVal = { "All", "idSach", "TenSach", "NhaXB", "TenTG", "NamXB", "NgonNgu"};
	private String[] thongKeVal = { "NgonNgu", "TheLoai", "TenTG", "NhaXB", "NamXB" };
	private JTextField tfIdS, tfTimKiem, tfTenS, tfNhaXB, tfTenTG, tfNamXB, tfGiaS, tfTheLoai, tfNgonNgu;
	MyConnectDB myConn;
	private Color color = new Color(0x009999);
	
	private JPanel inputPanelL, inputPanelR, btnOtherPanel;

	public QuanLySach(MyConnectDB connectDB) {
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
		JLabel label = new JLabel("Thông tin sách trong thư viện");
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
		panel.add(inputPanelL = createInputPanelL());
		panel.add(inputPanelR = createInputPanelR());
		panel.add(createButtonPanel());

		return panel;
	}

	private JPanel createInputPanelL() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		JPanel panelLeft = new JPanel(new GridLayout(5, 1));
		panel.setBorder(new EmptyBorder(10, 0, 125, 20));
		panelLeft.setBackground(color);
		panelLeft.add(createLabel("Mã sách:"));
		panelLeft.add(createLabel("Nhà xuất bản:"));
		panelLeft.add(createLabel("Năm xuất bản:"));
		panelLeft.add(createLabel("Thể loại:"));
		panelLeft.add(new JLabel(""));

		JPanel panelRight = new JPanel(new GridLayout(5, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.add(tfIdS = new JTextField());
		panelRight.add(tfNhaXB = new JTextField());
		panelRight.add(tfNamXB = new JTextField());
		panelRight.add(tfTheLoai = new JTextField());
		panelRight.add(new JLabel(""));

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(color);
		JPanel panelLeft = new JPanel(new GridLayout(5, 1));
		panelLeft.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 125, 20));
		panelLeft.add(createLabel("Tên sách:"));
		panelLeft.add(createLabel("Tên tác giả:"));
		panelLeft.add(createLabel("Giá sách:"));
		panelLeft.add(createLabel("Ngôn ngữ:"));

		JPanel panelRight = new JPanel(new GridLayout(5, 1, 5, 5));
		panelRight.setBackground(color);
		panelRight.setBorder(new EmptyBorder(0, 0, 0, 20));
		panelRight.add(tfTenS = new JTextField());
		panelRight.add(tfTenTG = new JTextField());
		panelRight.add(tfGiaS = new JTextField());
		panelRight.add(tfNgonNgu = new JTextField());

		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);

		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(10, 0, 80, 0));
		panel.add(createTKTKPanel());
		panel.add(btnOtherPanel = createButPanel());

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
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/addbook.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/updatebook.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/deletebook3.png")));

		return panel;
	}

	private JPanel createButCO() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBackground(color);
		panel.setBorder(new EmptyBorder(0, 0, 5, 0));
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnNhapFile = createButton("Nhập file"));
		btnNhapFile.setIcon(new ImageIcon(this.getClass().getResource("/nf.png")));
		panel.add(btnXuatFile = createButton("Xuất file"));
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/print.png")));
		btnXuatFile.setToolTipText("Xuất file");

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

	private JButton createButton(String str) {
		JButton btn = new JButton(str);
		btn.addActionListener(this);
		return btn;
	}
	
	private JLabel createLabel(String name) {
		JLabel label = new JLabel(name);
		label.setForeground(Color.white);
		
		return label;
	}

	// load data in database
	public void loadData(String Cot, String muonTim) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCol);
		ResultSet rs = null;
		rs = myConn.getDataID("Sach", "idSach", Cot, muonTim);

		String arr[] = new String[8];
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
		table.getColumnModel().getColumn(1).setPreferredWidth(225);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(122);
		table.getColumnModel().getColumn(4).setPreferredWidth(107);
	}

	private String[] getTK() {
		String[] arr = new String[2];
		arr[0] = thongKeCB.getSelectedItem().toString().trim();
		if (arr[0].equals("NgonNgu"))
			arr[1] = "Ngôn ngữ";
		if (arr[0].equals("TenTG"))
			arr[1] = "Tên tác giả";
		if (arr[0].equals("TheLoai"))
			arr[1] = "Thể loại";
		if (arr[0].equals("NhaXB"))
			arr[1] = "Nhà xuất bản";
		if (arr[0].equals("NamXB"))
			arr[1] = "Năm xuất bản";

		return arr;
	}

	// Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK();
		String[] titleVar = { "TT", str[1], "Số lượng" };
		model.setColumnIdentifiers(titleVar);
		ResultSet rs = null;
		rs = myConn.getVar("Sach", "idSach", str[0]);
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

	private Sach getSach() {
		String id = tfIdS.getText().trim().toUpperCase();
		String ten = tfTenS.getText().trim();
		String nxb = tfNhaXB.getText().trim();
		String tg = tfTenTG.getText().trim();
		String namxb = tfNamXB.getText().trim();
		String tl = tfTheLoai.getText().trim();
		String nn = tfNgonNgu.getText().trim();
		String tmp = tfGiaS.getText().trim();
		if (!Check.checkInt(tmp)) {
			JOptionPane.showMessageDialog(null, "Giá sách phải là số nguyên", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		if (id.equals("") || ten.equals("") || nxb.equals("") || tg.equals("") || nxb.equals("") || tl.equals("")
				|| nn.equals("") || tmp.equals("")) {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		int gia = Integer.parseInt(tmp);
		Sach s = new Sach(id, ten, nxb, tg, namxb, gia, tl, nn);

		return s;
	}

	private boolean checkTTinSach(Sach s) {
		if(!Check.checkDate(s.getNamXB())) {
			JOptionPane.showMessageDialog(null, "Sai năm xuất bản \nhoặc sai định dạng năm xuất bản: dd-MM-yyyy", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	private void add() {
		Sach s = getSach();
		if (s != null) {
			if(!checkTTinSach(s)) return;
			boolean ck = myConn.insert("Sach", s, null, null, null, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Thêm thành công.");
				cancel();
			} else {
				JOptionPane.showMessageDialog(null, "Trùng mã sách", "Error insert", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void update() {
		if (table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Sach s = getSach();
		if (s != null) {
			if(!checkTTinSach(s)) return;
			boolean ck = myConn.update(s.getIdSach(), s, null, null, null, null);
			if (ck) {
				loadData("All", "");
				JOptionPane.showMessageDialog(null, "Cập nhật thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "Error update", JOptionPane.ERROR_MESSAGE);
			}
		}
		cancel();
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
			boolean ck = myConn.deleteID("Sach", "idSach", (String) table.getValueAt(row, 0));
			loadData("All", "");
			if (ck)
				JOptionPane.showMessageDialog(null, "Xóa thành công");
		}
	}

	private void cancel() {
		tfIdS.setText("");
		tfIdS.setEnabled(true);
		tfTenS.setText("");
		tfNamXB.setText("");
		tfTenTG.setText("");
		tfNhaXB.setText("");
		tfGiaS.setText("");
		tfTheLoai.setText("");
		tfNgonNgu.setText("");
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
		if (e.getSource() == btnNhapFile) {
			imp = new ImportFile();
			int returnVal = fileDialog.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String path = fileDialog.getCurrentDirectory().toString() + "\\"
						+ fileDialog.getSelectedFile().getName();
				System.out.println(path);
				try {
					imp.importFileBook(path, myConn);
					loadData("All", "");
				} catch (IOException e1) {
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
				String infoS = "";
				String search = "";
				if (table.getModel().getColumnCount() > 3) {
					infoS = "Thông Tin Sách";
					if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
						search = "";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idSach")) {
						search = "Tìm Kiếm Theo Mã Sách";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenSach")) {
						search = "Tìm Kiếm Theo Tên Sách";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NhaXB")) {
						search = "Tìm Kiếm Theo Nhà Xuất Bản";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenTG")) {
						search = "Tìm Kiếm Theo Tên Tác Giả";
					} else if (timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NamXB")) {
						search = "Tìm Kiếm Theo Năm Xuất Bản";
					}
				} else {
					infoS = "Thống Kê Sách Theo " + table.getModel().getColumnName(1);
				}
				ef.printHeader(exFile, doc, infoS, search);
				ef.printContentS(exFile, doc, table);
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

	public JButton getBtnThongKe() {
		return btnThongKe;
	}

	public void setBtnThongKe(JButton btnThongKe) {
		this.btnThongKe = btnThongKe;
	}

	public JComboBox<String> getThongKeCB() {
		return thongKeCB;
	}

	public void setThongKeCB(JComboBox<String> thongKeCB) {
		this.thongKeCB = thongKeCB;
	}

	public JPanel getInputPanelL() {
		return inputPanelL;
	}

	public void setInputPanelL(JPanel inputPanelL) {
		this.inputPanelL = inputPanelL;
	}

	public JPanel getInputPanelR() {
		return inputPanelR;
	}

	public void setInputPanelR(JPanel inputPanelR) {
		this.inputPanelR = inputPanelR;
	}

	public JPanel getBtnOtherPanel() {
		return btnOtherPanel;
	}

	public void setBtnOtherPanel(JPanel btnOtherPanel) {
		this.btnOtherPanel = btnOtherPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		if(row >= 0) {
			tfIdS.setText((String) table.getValueAt(row, 0));
			tfIdS.setEnabled(false);
			tfTenS.setText((String) table.getValueAt(row, 1));
			tfNhaXB.setText((String) table.getValueAt(row, 2));
			tfTenTG.setText((String) table.getValueAt(row, 3));
			tfNamXB.setText((String) table.getValueAt(row, 4));
			tfGiaS.setText((String) table.getValueAt(row, 5));
			tfTheLoai.setText((String) table.getValueAt(row, 6));
			tfNgonNgu.setText((String) table.getValueAt(row, 7));
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
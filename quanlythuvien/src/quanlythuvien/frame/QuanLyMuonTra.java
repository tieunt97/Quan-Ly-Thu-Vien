package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import quanlythuvien.connect.ExportFile;
import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.ChiTietMuonTra;
import quanlythuvien.object.MuonTra;

public class QuanLyMuonTra extends JFrame implements ActionListener{
	private String titleCol[] = {"Mã Mượn Trả", "Mã Độc Giả", "Mã Nhân Viên", "Ngày Mượn", "Ngày Hẹn Trả", "Đặt Cọc"};
	private String titleCol1[] = {"Mã Mượn Trả", "Mã Sách", "Ngày Trả", "Tiền Phạt"};
	private String itemsTable[] = {"Mượn Trả", "Chỉ Tiết Mượn Trả"};
	private JTable table1, table2;
	final JFileChooser  fileDialog = new JFileChooser();
	private String exFile;
	private ExportFile ef = new ExportFile();
	private JButton btnThem, btnXuatFile, btnCancel, btnSua, btnXoa, btnTimKiem, btnThongKe;
	private JComboBox timKiemCB, thongKeCB, idNhanVienCB, idDocGiaCB, tableCB;
	private String[] timKiemVal = {"All", "idMuonTra", "idDocGia", "idNhanVien", "NgayMuon", "NgayTra"};
	private String[] thongKeVal = {"idDocGia", "idNhanVien", "NgayMuon"};
	private JTextField tfIdMT, tfTimKiem, tfNgayMuon, tfNgayHenTra, tfDatCoc, tfIdSach, tfNgayTra, tfTienPhat;
	private boolean isupdate = false;
	MyConnectDB myConn = new MyConnectDB();
	
	
	
	public QuanLyMuonTra() {
		add(createMainPanel());
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(1200, 720);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitlePanel("Quản Lý Mượn Trả"), BorderLayout.PAGE_START);
		panel.add(createGridPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createGridPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(createTable1Panel());
		panel.add(createBottomPanel());
		
		return panel;
		
	}

	private JPanel createTitlePanel(String title) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(title);
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.BLUE);;
		panel.add(label);
		
		return panel;
	}

	private JPanel createTable1Panel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBorder(new EmptyBorder(5, 50, 10, 50));
		panel.add(new JScrollPane(table1 = createTable()));
		
		return panel;
	}
	
	private JPanel createTable2Panel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBorder(new EmptyBorder(5, 50, 10, 30));
		panel.add(new JScrollPane(table2 = createTable()));
		
		return panel;
	}
	
	private JTable createTable() {
		JTable table = new JTable();
		return table;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(5, 0, 10, 0));
		panel.add(createTableBottomLeftPanel(), BorderLayout.CENTER);
		panel.add(createButtonPanel(), BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createTableBottomLeftPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(createCTMTPanel());
		panel.add(createInputPanel());
		
		return panel;
	}
	
	private JPanel createCTMTPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createTitlePanel("Chi Tiết Mượn Trả"), BorderLayout.PAGE_START);
		panel.add(createTable2Panel());
		
		return panel;
	}
	
	private JPanel createInputPanel(){
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(createInputPanelL());
		panel.add(createInputPanelR());
		
		return panel;
	}
	
	private JComboBox<String> createCBBoxID(String table) {
		JComboBox<String> cb = new JComboBox<String>();
		if(table.equals("NhanVien")) {
			ResultSet rs = myConn.getID("NhanVien", "idNhanVien");
			try {
				while(rs.next()) {
					cb.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(table.equals("DocGia")) {
			ResultSet rs = myConn.getID("DocGia", "idDocGia");
			try {
				while(rs.next()) {
					cb.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cb;
	}
	
	private JPanel createInputPanelL() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 50, 0, 30));
		JPanel panelLeft = new JPanel(new GridLayout(5, 1, 5, 5));
		panelLeft.add(new JLabel("Mã Mượn Trả"));
		panelLeft.add(new JLabel("Mã Độc Giả"));
		panelLeft.add(new JLabel("Mã Nhân Viên"));
		panelLeft.add(new JLabel("Mã Sách"));
		panelLeft.add(new JLabel("Ngày Trả"));
		
		JPanel panelRight = new JPanel(new GridLayout(5, 1, 5, 5));
		panelRight.add(tfIdMT = new JTextField());
		panelRight.add(idDocGiaCB = createCBBoxID("DocGia"));
		panelRight.add(idNhanVienCB = createCBBoxID("NhanVien"));
		panelRight.add(tfIdSach = new JTextField());
		panelRight.add(tfNgayTra = new JTextField());
		
		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);
		
		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(0, 50, 32, 30));
		JPanel panelLeft = new JPanel(new GridLayout(4, 1, 5, 5));
		panelLeft.add(new JLabel("Ngày Mượn"));
		panelLeft.add(new JLabel("Ngày Hẹn Trả"));
		panelLeft.add(new JLabel("Đặt Cọc"));
		panelLeft.add(new JLabel("Tiền Phạt"));
		
		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.add(tfNgayMuon = new JTextField());
		panelRight.add(tfNgayHenTra = new JTextField());
		panelRight.add(tfDatCoc = new JTextField());
		panelRight.add(tfTienPhat = new JTextField());
		
		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.setBorder(new EmptyBorder(40, 0, 60, 50));
		panel.add(createTKTKPanel());
		panel.add(createButPanel());
		
		return panel;
	}
	
	private JPanel createTKTKPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(createButCBPanel());
		panel.add(createtfTKTKPanel());
		
		return panel;
	}
	
	private JPanel createtfTKTKPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 15));
		panel.setBorder(new EmptyBorder(0, 5, 24, 0));													
		tfTimKiem = new JTextField();
		panel.add(tfTimKiem);
		tableCB = new JComboBox<String>(itemsTable);
		tableCB.setSelectedIndex(0);
		panel.add(tableCB);
	
		return panel;
	}
	
	private JPanel createButPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
		panel.setBorder(new EmptyBorder(5, 0, 10, 5));
		panel.add(createButAUD());
		panel.add(createButCO());
		
		return panel;
	}
	
	private JPanel createButAUD() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 5));
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(btnThem = createButton("Thêm"));
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/add.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/update1.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/delete.png")));
		
		return panel;
	}
	
	private JPanel createButCO() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 15, 5));
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnXuatFile = createButton("Xuất File"));
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/update.png")));
		
		return panel;
	}
	
	private JPanel createButCBPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 15));
		panel.setBorder(new EmptyBorder(0, 0, 25, 0));
		panel.add(btnTimKiem = createButton(""));
		btnTimKiem.setIcon(new ImageIcon(this.getClass().getResource("/search.png")));
		btnTimKiem.setToolTipText("Tìm Kiếm");
		timKiemCB = new JComboBox(timKiemVal);
		panel.add(timKiemCB);
		panel.add(btnThongKe = createButton(""));
		btnThongKe.setIcon(new ImageIcon(this.getClass().getResource("/tk.png")));
		btnThongKe.setToolTipText("Thống Kê");
		thongKeCB = new JComboBox(thongKeVal);
		panel.add(thongKeCB);
		
		return panel;
	}
	
	private JButton createButton(String str) {
		JButton btn = new JButton(str);
		btn.addActionListener(this);
		return btn;
	}
	
	//load data in database
	public void loadData(String Cot, String muonTim) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCol);
		DefaultTableModel model2 = new DefaultTableModel();
		
		ResultSet rs = myConn.getDataID("MuonTra", "idMuonTra", Cot, muonTim);
		String arr[] = new String[6];
		try {
			while(rs.next()) {
				for(int i = 0; i < arr.length; i++) {
					if(i == 0) {
						model2.setColumnIdentifiers(titleCol1);
						ResultSet rs2 = myConn.getDataID("ChiTietMuonTra", "idMuonTra", "idMuonTra", rs.getString(i + 1));
						String arr2[] = new String[4];
						while(rs2.next()) {
							for(int j = 0; j < arr2.length; j++) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table1.setModel(model);
	}
	
	private String[] getTK() {
		String[] arr = new String[2];
		arr[0] = thongKeCB.getSelectedItem().toString().trim();
		if(arr[0].equals("idDocGia")) arr[1] = "Mã Độc Giả";
		if(arr[0].equals("idNhanVien")) arr[1] = "Mã Nhân Viên";
		if(arr[0].equals("NgayMuon")) arr[1] = "Ngày Mượn";

		return arr;
	}
	
	private String[] getSearch() {
		String[] arr = new String[2];
		arr[0] = timKiemCB.getSelectedItem().toString().trim();
		arr[1] = tfTimKiem.getText().toString().trim();
		return arr;
	}
	
	//Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK(); 
		String[] titleVar = {"TT", str[1], "Số lượng"};
		model.setColumnIdentifiers(titleVar);
		ResultSet rs = null;
		rs = myConn.getVar("MuonTra", "idMuonTra", str[0]);
		
		String arr[] = new String[3];
		
		try {
			int j = 1;
			while(rs.next()) {
				for(int i = 0; i < arr.length; i++) {
					if(i == 0) {
						arr[i] = ""+ (j++) + "";
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
	private boolean setDisplayInput(boolean update) {
		if(update && table1.getSelectedRow() < 0 && table2.getSelectedRow() < 0 || (table1.getSelectedRow() >= 0 && table2.getSelectedRow() >= 0)) 
			return false;
		else if(update && table1.getSelectedRow() >= 0) {
			int row = table1.getSelectedRow();
			tfIdMT.setText((String) table1.getValueAt(row, 0));
			idDocGiaCB.setSelectedItem(table1.getValueAt(row, 1));
			idNhanVienCB.setSelectedItem(table1.getValueAt(row, 2));
			tfNgayMuon.setText((String) table1.getValueAt(row, 3));
			tfNgayHenTra.setText((String) table1.getValueAt(row, 4));
			tfDatCoc.setText((String) table1.getValueAt(row, 5));
		}else if(update && table2.getSelectedRow() >= 0) {
			int row = table2.getSelectedRow();
			tfIdMT.setText((String) table2.getValueAt(row, 0));
			tfIdSach.setText((String) table2.getValueAt(row, 1));
			tfNgayTra.setText((String) table2.getValueAt(row, 2));
			tfTienPhat.setText((String) table2.getValueAt(row, 3));
		}
		
		return true;
	}
	
	private MuonTra getMuonTra() {
		String id = tfIdMT.getText().trim();
		String idDG = idDocGiaCB.getSelectedItem().toString().trim();
		String idNV = idNhanVienCB.getSelectedItem().toString().trim();
		String ngaymuon = tfNgayMuon.getText().trim();
		String ngayhentra = tfNgayHenTra.getText().trim();
		String tmp = tfDatCoc.getText().trim();
		if(id.equals("") ||idDG.equals("")||idNV.equals("")||
				ngaymuon.equals("") || ngayhentra.equals("") || tmp.equals("")) return null;
		int  datcoc = Integer.parseInt(tmp);
		MuonTra mt = new MuonTra(id, idDG, idNV, ngaymuon, ngayhentra, datcoc);
		
		return mt;
	}
	private ChiTietMuonTra getCTMT() {
		String idMT = tfIdMT.getText().trim().toUpperCase(); //trim() dùng để loại bỏ khảng trắng ở hai đầu tf
		String idS = tfIdSach.getText().trim();
		String ngaytra  = tfNgayTra.getText().trim();
		if(idMT.equals("") || idS.equals("")) return null;
		int tienphat;
		if(tfTienPhat.getText().trim().equals("")) {
			tienphat = 0;
		}else
			tienphat = Integer.parseInt(tfTienPhat.getText().trim());
		ChiTietMuonTra ctmt = new ChiTietMuonTra(idMT, idS, ngaytra, tienphat);
		
		return ctmt;
	}
	private void addOrUpdate() {
		MuonTra mt = getMuonTra();
		ChiTietMuonTra ctmt = getCTMT();
		if(mt != null) {
			if(isupdate) {
				myConn.update(mt.getIdMuonTra(), null, null, null, mt, null);
				loadData("All", "");
				isupdate = false;
			}else {
				myConn.insert("MuonTra", null, null, null, mt, null);
				myConn.insert("ChiTietMuonTra", null, null, null, null, ctmt);
				loadData("All", "");
			}
			setDisplayInput(false);
		}else {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống hoặc trùng khóa", "Error insert", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void addOrUpdate1() {
		ChiTietMuonTra ctmt = getCTMT();
		if(ctmt != null) {
			if(isupdate) {
				myConn.update(ctmt.getIdMuonTra(), null, null, null, null, ctmt);
				isupdate = false;
			}else {
				myConn.insert("ChiTietMuonTra", null, null, null, null, ctmt);
			}
			loadData("All", "");
			setDisplayInput(false);
		}else {
			JOptionPane.showMessageDialog(null, "Có trường dữ liệu trống hoặc trùng khóa", "Error insert", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void update() {
		if(setDisplayInput(true)) {
			isupdate = true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Bạn phải chọn một hàng để sửa", "Error update", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void delete() {
		int row = table1.getSelectedRow();
		int row1 = table2.getSelectedRow();
		if((row < 0 && row1 < 0) || (row >= 0 && row1 >= 0)) {
			System.out.println("Error delete");
			JOptionPane.showMessageDialog(null, "Bạn phải chọn một hàng để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Bạn có chắc muốn xóa không", "Delete", 0, JOptionPane.YES_NO_OPTION, null, null, 1);
		if(select == 0) {
			boolean ck = false;
			if(row >= 0) {
				ck = myConn.deleteIDRef("MuonTra", "idMuonTra", (String) table1.getValueAt(row, 0));
			}else if(row1 >= 0) {
				ck = myConn.deleteCTMT((String) table2.getValueAt(row1, 0), (String) table2.getValueAt(row1, 1));
			}
			loadData("All", "");
			if(ck) JOptionPane.showInternalMessageDialog(this.getContentPane(), "Xóa thành công");
		}
	}
	
	private void cancel() {
		tfIdMT.setText("");
		idDocGiaCB.setSelectedIndex(0);;
		idNhanVienCB.setSelectedIndex(0);
		tfNgayMuon.setText("");
		tfNgayHenTra.setText("");
		tfDatCoc.setText("");
		tfIdSach.setText("");
		tfNgayTra.setText("");
		tfTienPhat.setText("");
		tfTimKiem.setText("");
		tableCB.setSelectedIndex(0);
		setDisplayInput(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnThem) {
			if(tableCB.getSelectedItem().toString().equalsIgnoreCase("Mượn Trả")) {
				addOrUpdate();
			}else
				addOrUpdate1();
			return;
		}
		if(e.getSource() == btnSua) {
			
			update();
			return;
		}
		if(e.getSource() == btnXoa) {
			delete();
			return;
		}
		if(e.getSource() == btnCancel) {
			cancel();
			loadData("All", "");
			return;
		}
		if(e.getSource() == btnTimKiem) {
			String arr[] = getSearch();
			loadData(arr[0], arr[1]);
			return;
		}
		if(e.getSource() == btnThongKe) {
			loadVar();
			return;
		}
		if(e.getSource() == btnXuatFile) {
			int returnVal = fileDialog.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	   			String path = fileDialog.getCurrentDirectory().toString()
				       	   + "\\" + fileDialog.getSelectedFile().getName();
	   			if(path.indexOf(".docx") >= 0) {
	   				exFile = path;
	   			}
	   			else {
	   				exFile = path + ".docx";
	   			}
	   			System.out.println(exFile);
	        }
			
			XWPFDocument doc = new XWPFDocument();
			try {
				String infoMT = "";
				String search = "";
				if(table1.getModel().getColumnCount() > 3) {
					infoMT = "Thông Tin Mượn Trả";
					if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
						search = "";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idMuonTra")) {
						search = "Tìm Kiếm Theo Mã Mượn Trả";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idDocGia")) {
						search = "Tìm Kiếm Theo Mã Độc Giả";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgayMuon")) {
						search = "Tìm Kiếm Theo Ngày Mượn";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgayTra")) {
						search = "Tìm Kiếm Theo Ngày Trả";
					}
				}else {
					infoMT = "Thống Kê Mượn Trả Theo " + table1.getModel().getColumnName(1);
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
	
	
	public static void main(String[] args) {
		QuanLyMuonTra qlMT = new QuanLyMuonTra();
		qlMT.loadData("All", "");
	}
}

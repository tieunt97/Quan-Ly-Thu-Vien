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
import quanlythuvien.connect.ImportFile;
import quanlythuvien.connect.MyConnectDB;
import quanlythuvien.object.NhanVien;

public class QuanLyNhanVien extends JPanel implements ActionListener {
	private String titleCol[] = {"Mã NV", "Họ Tên", "Ngày Sinh", "Giới Tính", "Địa Chỉ", "Số ĐT"};
	private JTable table;
	final JFileChooser fileDialog = new JFileChooser();
	private ExportFile ef;
	private ImportFile imp;
	private String exFile;
	private JButton btnThem, btnSua, btnCancel, btnXuatFile, btnXoa, btnTimKiem, btnThongKe, btnThemFile;
	private JComboBox timKiemCB, thongKeCB;
	private String[] timKiemVal = {"All", "idNhanVien", "TenNV", "NgaySinh", "DiaChi", "GioiTinh"};
	private String[] thongKeVal = {"TenNV", "NgaySinh", "DiaChi", "GioiTinh"};
	private JTextField tfIdNV, tfTimKiem, tfTenNV, tfNgaySinhNV, tfGioiTinhNV, tfDiaChiNV, tfSdtNV;
	private boolean isupdate = false;
	MyConnectDB myConn;
	
	 
	
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
		JLabel label = new JLabel("Quản Lý Nhân Viên");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);;
		panel.add(label);
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}

	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBorder(new EmptyBorder(5, 50, 10, 50));
		panel.add(new JScrollPane(table = createTable()));
		panel.setBackground(new Color(0x009999));
		
		return panel;
	}
	
	private JTable createTable() {
		JTable table = new JTable();
		return table;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.setBorder(new EmptyBorder(10, 50, 10, 50));
		panel.add(createInputPanelL());
		panel.add(createInputPanelR());
		panel.add(createButtonPanel());
		return panel;
	}
	
	private JPanel createInputPanelL() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(10, 0, 180, 20));
		JPanel panelLeft = new JPanel(new GridLayout(3, 1));
		panelLeft.add(new JLabel("Mã NV"));
		panelLeft.add(new JLabel("Giới Tính"));
		panelLeft.add(new JLabel("Ngày Sinh"));
		
		JPanel panelRight = new JPanel(new GridLayout(3, 1, 5, 5));
		panelRight.add(tfIdNV = new JTextField());
		panelRight.add(tfGioiTinhNV = new JTextField());
		panelRight.add(tfNgaySinhNV = new JTextField());
		
		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);
		
		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(new EmptyBorder(10, 0, 180, 20));
		JPanel panelLeft = new JPanel(new GridLayout(3, 1));
		panelLeft.add(new JLabel("Họ Tên"));
		panelLeft.add(new JLabel("Địa Chỉ"));
		panelLeft.add(new JLabel("Số ĐT"));
		
		JPanel panelRight = new JPanel(new GridLayout(3, 1, 5, 5));
		panelRight.add(tfTenNV = new JTextField());
		panelRight.add(tfDiaChiNV = new JTextField());
		panelRight.add(tfSdtNV = new JTextField());
		
		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.setBorder(new EmptyBorder(10, 0, 80, 0));
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
		panel.setBorder(new EmptyBorder(0, 5, 24, 0));													/////
		tfTimKiem = new JTextField();
		panel.add(tfTimKiem);
		
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
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/addp3.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/updatep2.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/deletep.png")));
		
		return panel;
	}
	
	private JPanel createButCO() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnThemFile = createButton("Nhập File"));
		panel.add(btnXuatFile = createButton("Xuất File"));
		btnXuatFile.setToolTipText("Xuất File");
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
		
		ResultSet rs = myConn.getDataID("NhanVien", "idNhanVien", Cot, muonTim);
		String arr[] = new String[6];
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

	private String[] getTK() {
		String[] arr = new String[2];
		arr[0] = thongKeCB.getSelectedItem().toString().trim();
		if(arr[0].equals("TenNV")) arr[1] = "Tên Nhân Viên";
		if(arr[0].equals("GioiTinh")) arr[1] = "Giới Tính";
		if(arr[0].equals("DiaChi")) arr[1] = "Địa Chỉ";
		if(arr[0].equals("NgaySinh")) arr[1] = "Ngày Sinh";

		return arr;
	}
	
	//Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK(); 
		String[] titleVar = {"TT", str[1], "Số lượng"};
		model.setColumnIdentifiers(titleVar);
		ResultSet rs = null;
		rs = myConn.getVar("NhanVien", "idNhanVien", str[0]);
		
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
		table.setModel(model);
		cancel();
	}
	
	private boolean setDisplayInput(boolean update) {
		if(update && table.getSelectedRow() < 0) {
			return false;
		}else if(update) {
			int row = table.getSelectedRow();
			tfIdNV.setText((String) table.getValueAt(row, 0));
			tfTenNV.setText((String) table.getValueAt(row, 1));
			tfNgaySinhNV.setText((String) table.getValueAt(row, 2));
			tfGioiTinhNV.setText((String) table.getValueAt(row, 3));
			tfDiaChiNV.setText((String) table.getValueAt(row, 4));
			tfSdtNV.setText((String) table.getValueAt(row, 5));
		}
		return true;
	}
	
	private NhanVien getNhanVien() {
		String id = tfIdNV.getText().trim().toUpperCase(); 
		String ten = tfTenNV.getText().trim();
		if(id.equals("") || ten.equals("")) {
			return null;
		}
		String ngaysinh = tfNgaySinhNV.getText().trim();
		String gioitinh = tfGioiTinhNV.getText().trim();
		String diachi = tfDiaChiNV.getText().trim();
		String sdt = tfSdtNV.getText().trim();
		NhanVien nv = new NhanVien(id, ten, ngaysinh, gioitinh, diachi, sdt);
		
		return nv;
	}
	
	private void addOrUpdate() {
		NhanVien nv = getNhanVien();
		if(nv != null) {
			if(isupdate) {
				myConn.update(nv.getIdNhanVien(), null, null, nv, null, null);
				loadData("All", "");
				isupdate = false;
			}else {
				myConn.insert("NhanVien", null, null, nv, null, null);
				loadData("All", "");
			}
			cancel();
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
		int row = table.getSelectedRow();
		if(row < 0) {
			System.out.println("Error delete");
			JOptionPane.showMessageDialog(null, "Bạn phải chọn một hàng trong cột để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Bạn có chắc muốn xóa không", "Delete", 0, JOptionPane.YES_NO_OPTION, null, null, 1);
		if(select == 0) {
			boolean ck = myConn.deleteIDRef("NhanVien", "idNhanVien", (String) table.getValueAt(row, 0));
			loadData("All", "");
			if(ck) JOptionPane.showMessageDialog(null, "Xóa thành công");
		}
	}
	
	private void cancel() {
		tfIdNV.setText("");
		tfTenNV.setText("");
		tfNgaySinhNV.setText("");
		tfDiaChiNV.setText("");
		tfSdtNV.setText("");
		tfGioiTinhNV.setText("");
		tfTimKiem.setText("");
		timKiemCB.setSelectedIndex(0);
		thongKeCB.setSelectedIndex(0);
		setDisplayInput(false);
	}
	
	private String[] getSearch() {
		String[] arr = new String[2];
		arr[0] = timKiemCB.getSelectedItem().toString().trim();
		arr[1] = tfTimKiem.getText().toString().trim();
		return arr;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnThem) {
			addOrUpdate();
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
		if(e.getSource() == btnThemFile) {
			imp = new ImportFile();
			int returnVal = fileDialog.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	   			String path = fileDialog.getCurrentDirectory().toString()
				       	   + "\\" + fileDialog.getSelectedFile().getName();
	   			try {
					imp.importFileNV(path, myConn);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	   		}else {
	        	System.out.println("\ncanceled.");
	        	return;
	        }
			
			return;
		}
		if(e.getSource() == btnXuatFile) {
			ef  = new ExportFile();
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
	        }else {
	        	System.out.println("\ncanceled.");
	        	return;
	        }
			
			XWPFDocument doc = new XWPFDocument();
			try {
				String infoNV = "";
				String search = "";
				if(table.getModel().getColumnCount() > 3) {
					infoNV = "Thông Tin Nhân Viên";
					if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
						search = "";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idNhanVien")) {
						search = "Tìm Kiếm Theo Mã Nhân Viên";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenNV")) {
						search = "Tìm Kiếm Theo Tên Nhân Viên";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NgaySinh")) {
						search = "Tìm Kiếm Theo Ngày Sinh";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("DiaChi")) {
						search = "Tìm Kiếm Theo Địa Chỉ";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("GioiTinh")) {
						search = "Tìm Kiếm Theo Giới Tính";
					}
				}else {
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
	
}
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
import quanlythuvien.object.Sach;

public class QuanLySach extends JPanel implements ActionListener {
	private String titleCol[] = {"Mã Sách", "Tên Sách", "Nhà Xuất Bản", "Tên Tác Giả", "Năm Xuất Bản", "Giá Sách", "Thể Loại", "Ngôn Ngữ"};
	private JTable table;
	final JFileChooser  fileDialog = new JFileChooser();
	private String exFile;
	private ExportFile ef = new ExportFile();
	private ImportFile imp = new ImportFile();
	private JButton btnThem, btnCancel, btnSua, btnXoa, btnTimKiem, btnThongKe, btnNhapFile, btnXuatFile;
	private JComboBox timKiemCB, thongKeCB;
	private String[] timKiemVal = {"All", "idSach", "TenSach", "NhaXB", "TenTG", "NamXB"};
	private String[] thongKeVal = {"NgonNgu", "TheLoai", "TenTG", "NhaXB", "NamXB"};
	private JTextField tfIdS, tfTimKiem, tfTenS, tfNhaXB, tfTenTG, tfNamXB, tfGiaS, tfTheLoai, tfNgonNgu;
	private boolean isupdate = false;
	MyConnectDB myConn = new MyConnectDB();
	 
	
	
	public QuanLySach() {
		
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
		JLabel label = new JLabel("Quản Lý Sách");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.BLUE);
		panel.add(label);
		
		return panel;
	}

	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new GridLayout());
		panel.setBorder(new EmptyBorder(5, 50, 10, 50));
		panel.add(new JScrollPane(table = createTable()));
		
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
		JPanel panelLeft = new JPanel(new GridLayout(4, 1));
		panel.setBorder(new EmptyBorder(10, 0, 145, 20));
		panelLeft.add(new JLabel("Mã Sách"));
		panelLeft.add(new JLabel("Nhà Xuất Bản"));
		panelLeft.add(new JLabel("Năm Xuất Bản"));
		panelLeft.add(new JLabel("Thể Loại"));

		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.add(tfIdS = new JTextField());
		panelRight.add(tfNhaXB = new JTextField());
		panelRight.add(tfNamXB = new JTextField());
		panelRight.add(tfTheLoai = new JTextField());
		
		panel.add(panelLeft, BorderLayout.WEST);
		panel.add(panelRight, BorderLayout.CENTER);
		
		return panel;
	}

	private JPanel createInputPanelR() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel panelLeft = new JPanel(new GridLayout(4, 1));
		panel.setBorder(new EmptyBorder(10, 0, 145, 20));
		panelLeft.add(new JLabel("Tên Sách"));
		panelLeft.add(new JLabel("Tên Tác Giả"));
		panelLeft.add(new JLabel("Giá Sách"));
		panelLeft.add(new JLabel("Ngôn Ngữ"));
		
		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
		panelRight.add(tfTenS = new JTextField());
		panelRight.add(tfTenTG = new JTextField());
		panelRight.add(tfGiaS = new JTextField());
		panelRight.add(tfNgonNgu = new JTextField());
		
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
		panel.setBorder(new EmptyBorder(0, 5, 24, 0));												
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
		btnThem.setIcon(new ImageIcon(this.getClass().getResource("/addbook.png")));
		panel.add(btnSua = createButton("Sửa"));
		btnSua.setIcon(new ImageIcon(this.getClass().getResource("/updatebook.png")));
		panel.add(btnXoa = createButton("Xóa"));
		btnXoa.setIcon(new ImageIcon(this.getClass().getResource("/deletebook3.png")));
		
		return panel;
	}
	
	private JPanel createButCO() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel.add(btnCancel = createButton("Hủy"));
		btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/cancel.png")));
		panel.add(btnNhapFile = createButton("Nhập File"));
		panel.add(btnXuatFile = createButton("Xuất File"));
		btnXuatFile.setIcon(new ImageIcon(this.getClass().getResource("/update.png")));
		btnXuatFile.setToolTipText("Xuất File");
		
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
		ResultSet rs = null;
		rs = myConn.getDataID("Sach", "idSach", Cot, muonTim);
		
		String arr[] = new String[8];
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
		if(arr[0].equals("NgonNgu")) arr[1] = "Ngôn Ngữ";
		if(arr[0].equals("TenTG")) arr[1] = "Tên Tác Giả";
		if(arr[0].equals("TheLoai")) arr[1] = "Thể Loại";
		if(arr[0].equals("NhaXB")) arr[1] = "Nhà Xuất Bản";
		if(arr[0].equals("NamXB")) arr[1] = "Năm Xuất Bản";
		
		return arr;
	}
	
	//Thong ke du lieu
	public void loadVar() {
		DefaultTableModel model = new DefaultTableModel();
		String[] str = getTK(); 
		String[] titleVar = {"TT", str[1], "Số lượng"};
		model.setColumnIdentifiers(titleVar);
		ResultSet rs = null;
		rs = myConn.getVar("Sach", "idSach", str[0]);
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
			tfIdS.setText((String) table.getValueAt(row, 0));
			tfTenS.setText((String) table.getValueAt(row, 1));
			tfNhaXB.setText((String) table.getValueAt(row, 2));
			tfTenTG.setText((String) table.getValueAt(row, 3));
			tfNamXB.setText((String) table.getValueAt(row, 4));
			tfGiaS.setText((String) table.getValueAt(row, 5));
			tfTheLoai.setText((String) table.getValueAt(row, 6));
			tfNgonNgu.setText((String) table.getValueAt(row, 7));
		}

		return true;
	}
	
	private Sach getSach() {
		String id = tfIdS.getText().trim().toUpperCase(); //trim() dùng để loại bỏ khảng trắng ở hai đầu tf
		String ten = tfTenS.getText().trim();
		String nxb = tfNhaXB.getText().trim();
		String tg = tfTenTG.getText().trim();
		String namxb = tfNamXB.getText().trim();
		String tl = tfTheLoai.getText().trim();
		String nn = tfNgonNgu.getText().trim();
		String tmp = tfGiaS.getText().trim();
		if(id.equals("") || ten.equals("") || nxb.equals("") || tg.equals("") ||
				nxb.equals("") || tl.equals("") || nn.equals("") || tmp.equals("")) return null;
		int gia = Integer.parseInt(tmp);
		Sach s = new Sach(id, ten, nxb, tg, namxb, gia, tl, nn);
		
		return s;
	}
	
	private void addOrUpdate() {
		Sach s = getSach();
		if(s != null) {
			if(isupdate) {
				myConn.update(s.getIdSach(), s, null, null, null, null);
				loadData("All", "");
				isupdate = false;
			}else {
				myConn.insert("Sach", s, null, null, null, null);
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
			JOptionPane.showMessageDialog(null, "Bạn phải chọn một hàng trong bảng để xóa", "Error delete", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Bạn có chắc muốn xóa không", "Delete", 0, JOptionPane.YES_NO_OPTION, null, null, 1);
		if(select == 0) {
			boolean ck = myConn.deleteID("Sach", "idSach", (String) table.getValueAt(row, 0));
			loadData("All", "");
			if(ck) JOptionPane.showMessageDialog(null, "Xóa thành công");
		}
	}
	
	private void cancel() {
		tfIdS.setText("");
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
		if(e.getSource() == btnNhapFile) {
			int returnVal = fileDialog.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				String path = fileDialog.getCurrentDirectory().toString()
						+ "\\" + fileDialog.getSelectedFile().getName();
				System.out.println(path);
				try {
					imp.importFileBook(path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
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
				String infoS = "";
				String search = "";
				if(table.getModel().getColumnCount() > 3) {
					infoS = "Thông Tin Sách";
					if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
						search = "";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("idSach")) {
						search = "Tìm Kiếm Theo Mã Sách";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenSach")) {
						search = "Tìm Kiếm Theo Tên Sách";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NhaXB")) {
						search = "Tìm Kiếm Theo Nhà Xuất Bản";
					}else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("TenTG")) {
						search = "Tìm Kiếm Theo Tên Tác Giả";
					}
					else if(timKiemCB.getSelectedItem().toString().equalsIgnoreCase("NamXB")) {
						search = "Tìm Kiếm Theo Năm Xuất Bản";
					}
				}else {
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
	
}
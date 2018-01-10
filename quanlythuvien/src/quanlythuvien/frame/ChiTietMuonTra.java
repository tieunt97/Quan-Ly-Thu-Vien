package quanlythuvien.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import quanlythuvien.connect.MyConnectDB;

public class ChiTietMuonTra extends JDialog implements ActionListener {
	private JTextField tfIdSach, tfNgayTra;
	private JButton btnTraSach, btnInPhieu, btnThemSach, btnHuy, btnXoa;
	private JTable table, tableSach;
	private MyConnectDB myConn;
	private String[] muonTra;
	private String[] titleCols = { "Mã sách", "Tên sách", "Ngày trả", "Tiền phạt" };
	private JFileChooser fileDialog = new JFileChooser();
	private ExportFile ef = new ExportFile();
	private Color color = new Color(0x009999);

	public ChiTietMuonTra(MyConnectDB myConn, String[] muonTra, JTable tableSach) {
		this.myConn = myConn;
		this.muonTra = muonTra;
		this.tableSach = tableSach;
		
		setSize(850, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		add(createMainPanel());
		setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(color);
		panel.add(createTitlePanel(), BorderLayout.NORTH);
		panel.add(createMain(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Chi tiết mượn trả");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		;
		panel.add(label);
		panel.setBackground(color);

		return panel;
	}

	private JPanel createMain() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 20, 10, 20));
		panel.setBackground(color);
		panel.add(createLeftPanel(), BorderLayout.WEST);
		panel.add(createRightPanel(), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createLeftPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(0, 0, 120, 0));
		panel.setBackground(color);
		panel.add(createDatePanel(), BorderLayout.NORTH);
		panel.add(createDetailPanel(), BorderLayout.CENTER);
		panel.add(createTFPanel(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel createDatePanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(new JLabel("Ngày:"));
		String[] date = Check.getDate();
		String ngay  = date[0] + "-" + date[1] + "-" + date[2];
		panel.add(new JLabel(ngay));
		
		return panel;
	}

	private JPanel createDetailPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(color);
		JPanel panelL = new JPanel(new GridLayout(3, 1, 5, 5));
		panelL.setBackground(color);
		panelL.add(createLabel("Mã mượn trả: "));
		panelL.add(createLabel("Tên độc giả: "));
		panelL.add(createLabel("Tên nhân viên: "));
		String[] info = getDeTail();
		JPanel panelR = new JPanel(new GridLayout(3, 1, 5, 5));
		panelR.setBackground(color);
		panelR.add(createLabel(info[0]));
		panelR.add(createLabel(info[1] != null ? info[1] : ""));
		panelR.add(createLabel(info[2] != null ? info[2] : ""));
		panel.add(panelL, BorderLayout.WEST);
		panel.add(panelR, BorderLayout.CENTER);

		return panel;
	}

	private String[] getDeTail() {
		String[] info = new String[3];
		info[0] = muonTra[0];
		ResultSet rs1 = myConn.getNames("DocGia", "tenDG", "idDocGia", muonTra[1]);
		ResultSet rs2 = myConn.getNames("NhanVien", "tenNV", "idNhanVien", muonTra[2]);
		try {
			if (rs1.next() && rs2.next())
				info[1] = rs1.getString(1);
			info[2] = rs2.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}

	private JPanel createTFPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.setBackground(color);
		panel.add(createLabel("Mã sách: "));
		panel.add(tfIdSach = new JTextField(10));
		panel.add(btnThemSach = createButton("Thêm sách"));

		return panel;
	}

	private JPanel createRightPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBackground(color);
		panel.add(createTablePanel(), BorderLayout.CENTER);
		panel.add(createBottomPanel(), BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(""));
		panel.add(new JScrollPane(table = new JTable()));
		loadData();
		return panel;
	}

	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
		panel.setBorder(new EmptyBorder(0, 45, 0, 45));
		panel.setBackground(color);
		JPanel panelLL = new JPanel(new GridLayout(2, 1, 5, 5));
		panelLL.setBackground(color);
		JPanel panelL = new JPanel(new BorderLayout());
		panelL.setBackground(color);
		panelL.add(createLabel("Ngày trả: "), BorderLayout.WEST);
		panelL.add(tfNgayTra = new JTextField(), BorderLayout.CENTER);
		JPanel panelS = new JPanel(new GridLayout(1, 2, 10, 10));
		panelS.setBackground(color);
		panelS.add(btnHuy = createButton("Hủy"));
		panelS.add(btnXoa = createButton("Xóa"));
		JPanel panelR = new JPanel(new GridLayout(2, 1, 5, 5));
		panelR.setBackground(color);
		panelR.add(btnTraSach = createButton("Trả sách"));
		panelR.add(btnInPhieu = createButton("In phiếu"));
		panelLL.add(panelL);
		panelLL.add(panelS);
		panel.add(panelLL);
		panel.add(panelR);

		return panel;
	}

	private void loadData() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(titleCols);
		ResultSet rs = myConn.getDataID("ChiTietMuonTra", "idMuonTra", "idMuonTra", muonTra[0]);
		String arr2[] = new String[4];
		try {
			while (rs.next()) {
				for (int i = 0; i < arr2.length; i++) {
					if(i == 0) 
						arr2[i] = rs.getString(2);
					if(i == 1) {
						ResultSet rs1 = myConn.getNames("Sach", "TenSach", "idSach", rs.getString(2));
						if(rs1.next()) arr2[i] = rs1.getString(1);
					}
					if (i > 1)
						arr2[i] = rs.getString(i + 1);
				}
				model.addRow(arr2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(175);
	}

	private JLabel createLabel(String name) {
		JLabel lab = new JLabel(name);
		lab.setForeground(Color.WHITE);
		return lab;
	}

	private JButton createButton(String name) {
		JButton btn = new JButton(name);
		btn.addActionListener(this);
		return btn;
	}

	private void huy() {
		tfIdSach.setText("");
		tfNgayTra.setText("");
		table.getSelectionModel().clearSelection();
	}
	
	private void xoa() {
		int row = table.getSelectedRow();
		if(row < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn một hàng để xóa!!!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		String idS = (String) table.getValueAt(row, 0);
		String ngayTra = (String) table.getValueAt(row, 2);
		if(myConn.deleteCTMT(muonTra[0], (String) table.getValueAt(row, 0))) {
			if(ngayTra.equals("")) {
				//cap nhat lai trang thai cua sach
				myConn.updateTTSach((String) table.getValueAt(row, 0), 1);
				for(int i = 0; i < tableSach.getRowCount(); i++) {
					String idSach = ((String) tableSach.getValueAt(i, 0));
					if(idSach.equals(idS)) {
						tableSach.setValueAt(1, i, tableSach.getColumnCount() - 1);
						break;
					}
				}
			}
			((DefaultTableModel) table.getModel()).removeRow(row);
			JOptionPane.showMessageDialog(null, "Xóa thành công!!!");
		}
	}
	
	private void themSach() {
		String idSach = tfIdSach.getText().trim().toUpperCase();
		if (idSach.equals("")) {
			JOptionPane.showMessageDialog(null, "Mã sách trống!!!", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		ResultSet rs = myConn.getNames("Sach", "TenSach", "idSach", idSach);
		String tenSach = "";
		try {
			if (rs.next()) {
				ResultSet rs1 = myConn.getNames("Sach", "TrangThai", "idSach", idSach);
				if(rs1.next()) {
					int trangThai = rs1.getInt(1);
					if(trangThai == 0) {
						JOptionPane.showMessageDialog(null, "Sách đã được mượn!!!");
						return;
					}
				}
				tenSach = rs.getString(1);
				if (myConn.insert("ChiTietMuonTra", null, null, null, null,
						new quanlythuvien.object.ChiTietMuonTra(muonTra[0], idSach, "", 0))) {
					((DefaultTableModel) table.getModel()).addRow(new Object[] { idSach, tenSach, "", "" });
					//cap nhat lai trang thai cua sach
					myConn.updateTTSach(idSach, 0);
					for(int i = 0; i < tableSach.getRowCount(); i++) {
						String idS = ((String) tableSach.getValueAt(i, 0));
						if(idSach.equals(idS)) {
							tableSach.setValueAt(0, i, tableSach.getColumnCount() - 1);
							break;
						}
					}
					JOptionPane.showMessageDialog(null, "Thêm thành công!!!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Sách không tồn tại!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void traSach() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Cần chọn sách muốn trả!!!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String ngayTra = tfNgayTra.getText().trim();
		if (ngayTra.equals("")) {
			JOptionPane.showMessageDialog(null, "Ngày trả trống!!!", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int tienphat = Check.getTienPhat(muonTra[3], muonTra[4], ngayTra, Integer.parseInt(muonTra[5]));
		if (tienphat == -1) {
			JOptionPane.showMessageDialog(null, "Ngày trả sai \nĐịnh dạng ngày: dd-MM-yyyy", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		quanlythuvien.object.ChiTietMuonTra ctmt = new quanlythuvien.object.ChiTietMuonTra(muonTra[0],
				(String) table.getValueAt(row, 0), ngayTra, tienphat);
		if (myConn.update(muonTra[0], null, null, null, null, ctmt)) {
			table.setValueAt(ngayTra, row, 2);
			table.setValueAt(tienphat + "", row, 3);
			//cap nhat lai trang thai cua sach
			myConn.updateTTSach(ctmt.getIdSach(), 1);
			for(int i = 0; i < tableSach.getRowCount(); i++) {
				String idSach = ((String) tableSach.getValueAt(i, 0));
				if(idSach.equals((String) table.getValueAt(row, 0))) {
					tableSach.setValueAt(1, i, tableSach.getColumnCount() - 1);
					break;
				}
			}
			JOptionPane.showMessageDialog(null, "Trả sách thành công!!!");
		}
	}

	private void inPhieu() {
		String exFile;
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
			ef.printHeader(exFile, doc, "Phiếu Mượn Trả", "");
			ef.printContentPhieuMT(exFile, doc, muonTra, myConn);
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnTraSach) {
			traSach();
			return;
		}
		if (e.getSource() == btnInPhieu) {
			inPhieu();
			return;
		}
		if (e.getSource() == btnThemSach) {
			themSach();
			return;
		}
		if(e.getSource() == btnHuy) {
			huy();
			return;
		}
		if(e.getSource() == btnXoa) {
			xoa();
			return;
		}
	}

}

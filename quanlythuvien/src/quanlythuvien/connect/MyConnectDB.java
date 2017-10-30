package quanlythuvien.connect;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import quanlythuvien.object.ChiTietMuonTra;
import quanlythuvien.object.DocGia;
import quanlythuvien.object.MuonTra;
import quanlythuvien.object.NhanVien;
import quanlythuvien.object.Sach;

public class MyConnectDB {
	private final String className = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/quanlythuvien_20153752";
	private final String user = "root";
	private final String pwd = "1234";
	private Connection  conn;
	Statement stmt;

	
	
	public MyConnectDB() {
		try {
			Class.forName(className);// connect driver
			conn = (Connection) DriverManager.getConnection(url, user, pwd);
			System.out.print("\nConnect success.");
			System.out.print("\nNguyễn Tài Tiêu - 20153752");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getDataID(String table, String id, String Cot, String muonTim) {
		ResultSet rs = null;
		if(Cot.equalsIgnoreCase("All")) { 		///get datas from table
			String qSql = "select * from " + table;
			try {
				Statement stmt = (Statement) conn.createStatement();
				rs = stmt.executeQuery(qSql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String qSql = "select * from " + table + " where " + Cot + " like ? ";
			PreparedStatement ps;
			try {
				ps = (PreparedStatement) conn.clientPrepareStatement(qSql);
				ps.setString(1, "%"+ muonTim + "%");
				rs = ps.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rs;
	}
	
	public ResultSet getID(String table, String idName) {
		ResultSet rs = null;
		String qSql = "select " + idName + " from " + table + " order by " + idName + " asc";
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery(qSql);
		} catch (SQLException e) {
			System.out.println("error select" + e);
		}
		
		return rs;
	}
	
	public ResultSet getNames(String table, String cot, String idName, String id) {
		ResultSet rs = null;
		String qSql = "select " + cot + " from " + table + " where " + idName + " = ?";
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	public ResultSet getContentTablePhieuMT(String idMT) {
		ResultSet rs = null;
		String qSql = "select CTMT.idMuonTra, CTMT.idSach, S.tenSach, CTMT.ngayTra, CTMT.tienPhat " + 
				"from ChiTietMuonTra CTMT, Sach S " + 
				"where CTMT.idSach = S.idSach and CTMT.idMuonTra = ?";
		
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setString(1, idMT);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	//Thong ke 
	public ResultSet getVar(String table, String idT,String muonTK) {
		ResultSet rs = null;
		String qSql = "select " + muonTK + ", count(" + idT +") from " + table + " group by " + muonTK;
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery(qSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public boolean deleteID(String table, String idT,String id) {
		String qSql = "delete from " + table + " where " + idT + " = ?";
		PreparedStatement ps = null;
		
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setString(1, id);
			if(ps.executeUpdate() > 0) {
				System.out.println("delete success.");
				return true;
			}else { 
				System.out.println("delete error");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("lỗi delete " + e);
			JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	
	public boolean deleteCTMT(String idMT, String idS) {
		String qSql = "delete from ChiTietMuonTra where idMuonTra = ? and idSach = ?";
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setString(1, idMT);
			ps.setString(2, idS);
			if(ps.executeUpdate() > 0) {
				System.out.println("delele success");
				return true;
			}else {
				System.out.println("delete error????");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteIDRef(String table, String idT, String id) {
		if(table.equalsIgnoreCase("MuonTra")) {
			deleteID("ChiTietMuonTra", idT, id);
			deleteID(table, idT, id);
			return true;
		}else if(table.equalsIgnoreCase("NhanVien") || table.equalsIgnoreCase("DocGia")){
			String qSql = "select idMuonTra from MuonTra, " + table + " where MuonTra." + idT + " = " + table + "." + idT + " and MuonTra." + idT + " = ?";
			PreparedStatement ps;
			ResultSet rs = null;
			try {
				ps = (PreparedStatement) conn.clientPrepareStatement(qSql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				while(rs.next()) {
				String id1 = rs.getString(1);
				deleteID("ChiTietMuonTra", "idMuonTra", id1);
				deleteID("MuonTra", "idMuonTra", id1);
				}
				deleteID(table, idT, id);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return false;
	}
	
	public void insert(String table, Sach s, DocGia dg, NhanVien nv, MuonTra mt, ChiTietMuonTra ctmt) {
		String qSql = "";
		if(table.equals("Sach")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("DocGia")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("NhanVien")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("MuonTra")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("ChiTietMuonTra")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?)";
		}
		
		PreparedStatement ps = null;
		
		try {
			
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			if(table.equals("Sach")) {
				ps.setString(1, s.getIdSach());
				ps.setString(2, s.getTenSach());
				ps.setString(3, s.getNhaXB());
				ps.setString(4, s.getTenTG());
				ps.setString(5, s.getNamXB());
				ps.setInt(6, s.getGiaSach());
				ps.setString(7, s.getTheLoai());
				ps.setString(8, s.getNgonNgu());
			}
			if(table.equals("DocGia")) {
				ps.setString(1, dg.getIdDocGia());
				ps.setString(2, dg.getTenDG());
				ps.setString(3, dg.getNgaySinh());
				ps.setString(4, dg.getGioiTinh());
				ps.setString(5, dg.getEmail());
				ps.setString(6, dg.getSoDT());
				ps.setString(7, dg.getDiaChi());
			}
			if(table.equals("NhanVien")) {
				ps.setString(1, nv.getIdNhanVien());
				ps.setString(2, nv.getTenNV());
				ps.setString(3, nv.getNgaySinh());
				ps.setString(4, nv.getGioiTinh());
				ps.setString(5, nv.getDiaChi());
				ps.setString(6, nv.getSoDT());
			}
			if(table.equals("MuonTra")) {
				ps.setString(1, mt.getIdMuonTra());
				ps.setString(2, mt.getIdDocGia());
				ps.setString(3, mt.getIdNhanVien());
				ps.setString(4, mt.getNgayMuon());
				ps.setString(5, mt.getNgayHenTra());
				ps.setInt(6, mt.getDatCoc());
			}
			if(table.equals("ChiTietMuonTra")) {
				ps.setString(1, ctmt.getIdMuonTra());
				ps.setString(2, ctmt.getIdSach());
				ps.setString(3, ctmt.getNgayTra());
				ps.setInt(4, ctmt.getTienPhat());
			}
			
			
			if(ps.executeUpdate() > 0) {
				System.out.println("\ninsert success.");
				JOptionPane.showMessageDialog(null, "Thêm Thành Công");
			}else 
				System.out.println("insert error");
		} catch (SQLException e) {
			System.out.println("insert error" + e);
			JOptionPane.showMessageDialog(null, "Trùng mã hoặc có trường dữ liệu trống", "Error insert", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void update(String id, Sach s, DocGia dg, NhanVien nv, MuonTra mt, ChiTietMuonTra ctmt) {
		String qSql = "";
		if(s != null) {
			qSql = "update Sach set TenSach = ?, NhaXB = ?, TenTG = ?, NamXB = ?, GiaSach = ?, TheLoai = ?, NgonNgu = ? where idSach = ?";
		}
		if(dg != null) {
			qSql = "update DocGia set TenDG = ?, NgaySinh = ?, GioiTinh = ?, Email = ?, SoDT = ?, DiaChi = ? where idDocGia = ?";
		}
		if(nv != null) {
			qSql = "update NhanVien set TenNV = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SoDT = ? where idNhanVien = ?";
		}
		if(mt != null) {
			qSql = "update MuonTra set idDocGia = ?, idNhanVien = ?, NgayMuon = ?, NgayHenTra = ?, DatCoc = ? where idMuonTra = ?";
		}
		if(ctmt != null) {
			qSql = "update ChiTietMuonTra set NgayTra = ?, TienPhat = ? where idMuonTra = ? and idSach = ?";
		}
		PreparedStatement ps = null;
		
		try {
			if(s != null) {
				ps = (PreparedStatement) conn.prepareStatement(qSql);
				ps.setString(1, s.getTenSach());
				ps.setString(2, s.getNhaXB());
				ps.setString(3, s.getTenTG());
				ps.setString(4, s.getNamXB());
				ps.setInt(5, s.getGiaSach());
				ps.setString(6, s.getTheLoai());
				ps.setString(7, s.getNgonNgu());
				ps.setString(8, id);
				
			}
			if(dg != null) {
				ps = (PreparedStatement) conn.prepareStatement(qSql);
				ps.setString(1, dg.getTenDG());
				ps.setString(2, dg.getNgaySinh());
				ps.setString(3, dg.getGioiTinh());
				ps.setString(4, dg.getEmail());
				ps.setString(5, dg.getSoDT());
				ps.setString(6, dg.getDiaChi());
				ps.setString(7, id);
			}
			if(nv != null) {
				ps = (PreparedStatement) conn.prepareStatement(qSql);
				ps.setString(1, nv.getTenNV());
				ps.setString(2, nv.getNgaySinh());
				ps.setString(3, nv.getGioiTinh());
				ps.setString(4, nv.getDiaChi());
				ps.setString(5, nv.getSoDT());
				ps.setString(6, id);
			}
			if(mt != null) {
				ps = (PreparedStatement) conn.prepareStatement(qSql);
				ps.setString(1, mt.getIdDocGia());
				ps.setString(2, mt.getIdNhanVien());
				ps.setString(3, mt.getNgayMuon());
				ps.setString(4, mt.getNgayHenTra());
				ps.setInt(5, mt.getDatCoc());
				ps.setString(6, id);
			}
			if(ctmt != null) {
				ps = (PreparedStatement) conn.prepareStatement(qSql);
				ps.setString(1, ctmt.getNgayTra());
				ps.setInt(2, ctmt.getTienPhat());
				ps.setString(3, id);
				ps.setString(4, ctmt.getIdSach());
			}
			
			if(ps.executeUpdate() > 0) {
				System.out.println("\nupdate success.");
			}else 
				System.out.println("\nupdate error");
		} catch (SQLException e) {
			System.out.println("update error" + e);
		}
	}
	
	public void showData(ResultSet rs, String table) {
		try {
			while(rs.next()) {
									//*printf
				if(table.equals("Sach")) {
					System.out.printf("%-10s %-27s %-15s %-27s %-11s %-10d %-15s %-15s\n", rs.getString(1), 
						rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8));
				}	
				if(table.equals("DocGia")) {
					System.out.printf("%-10s %-27s %-10s %-10s %-27s %-18s %-27s", rs.getString(1),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
				}
				if(table.equals("NhanVien")) {
					System.out.printf("%-10s %-27s %-10s %-10s %-27s %-18s", rs.getString(1),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				}
				if(table.equals("MuonTra")) {
					System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10d", rs.getString(1),
							rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6));
				}
				if(table.equals("ChiTietMuonTra")) {
					System.out.printf("%-10s %-10s %-10s %-10d", rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//main
	public static void main(String[] args) {
		MyConnectDB myConn = new MyConnectDB();
//		myConn.insert("Sach", new Sach("S0003", "Lập trình hướng đối tượng", "BKHN", 
//				"Trịnh Thành Trung", "2017-01-18",75000, "lập trình", "Tiếng Việt"), null, null, null, null);
//		//myConn.deleteID("Sach", "idSach", "S0003");
//		myConn.showData(myConn.getDataID("Sach", "idSach", "All", ""), "Sach");
////		myConn.showData(myConn.getDataID("Sach", "idSach", "S0002"), "Sach");
//		System.out.println("Dữ liệu độc giả");
////		myConn.insert("DocGia", null, new DocGia("DG001", "Nguyễn Tài Tiêu", "1997-10-27", "Nam", "abc@gmail.com", "123456789", "Hà Nội"), null, null, null);
//		myConn.update("DG001", null, new DocGia("DG001", "Nguyễn Tài Tiêu", "27-10-1997", "Nam", "tieunt@gmail.com", "123456789", "Hà Nội"), null, null, null);
//		myConn.showData(myConn.getDataID("DocGia", "idDocGia", "All", ""), "DocGia");
//		System.out.println("\nDữ liệu nhân viên");
////		myConn.insert("NhanVien", null, null, new NhanVien("NV001", "Bùi Thị Diệu", "1997-01-18", "Nữ", "Hà Nội", "18271997"), null, null);
//		myConn.showData(myConn.getDataID("NhanVien", "idNhanVien", "All", ""), "NhanVien");
//		System.out.println("\n Dữ liệu mượn trả");
//		myConn.insert("MuonTra", null, null, null, new MuonTra("MT001", "DG001", "NV001", "2017-10-17", "2017-10-27", 50000), null);
//		myConn.showData(myConn.getDataID("MuonTra", "idMuonTra", "All", ""), "MuonTra");
//		myConn.insert("Sach", new Sach("LT001", "Lập trình Java", "NXB Thông tin và truyền thông", "Herbert Schildt", "2015-11-18", 125000, "Lập trình", "Tiếng Việt"), null, null, null, null);
		myConn.deleteCTMT("MT001", "LT001");
	}
}

package quanlythuvien.connect;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.w3.x2000.x09.xmldsig.TransformDocument;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import quanlythuvien.object.ChiTietMuonTra;
import quanlythuvien.object.DocGia;
import quanlythuvien.object.MuonTra;
import quanlythuvien.object.NhanVien;
import quanlythuvien.object.Sach;
import quanlythuvien.object.TaiKhoan;

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
				"from chitietmuontra CTMT, sach S " + 
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
	
	public ResultSet getVar(String muonThongKe, String sum) {
		ResultSet rs = null;
		String qSql = null;
		if(sum.equals("")) {
			qSql = "select " + muonThongKe + ", sum(DatCoc) from muontra group by " + muonThongKe; 
		}else {
			qSql = "select " + muonThongKe + ", sum(TienPhat) from muontra natural join chitietmuontra group by " + muonThongKe;
		}
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
//			JOptionPane.showMessageDialog(null, "Xóa lỗi", "Error delete", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	
	public boolean deleteCTMT(String idMT, String idS) {
		String qSql = "delete from chitietmuontra where idMuonTra = ? and idSach = ?";
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
			deleteID("chitietmuontra", idT, id);
			deleteID(table, idT, id);
			return true;
		}else if(table.equalsIgnoreCase("NhanVien") || table.equalsIgnoreCase("DocGia")){
			String qSql = "select idMuonTra from muontra, " + table + " where muontra." + idT + " = " + table + "." + idT + " and muontra." + idT + " = ?";
			PreparedStatement ps;
			ResultSet rs = null;
			try {
				ps = (PreparedStatement) conn.clientPrepareStatement(qSql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				while(rs.next()) {
					String id1 = rs.getString(1);
					deleteID("chitietmuontra", "idMuonTra", id1);
					deleteID("muontra", "idMuonTra", id1);
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
	
	public boolean insert(String table, Sach s, DocGia dg, NhanVien nv, MuonTra mt, ChiTietMuonTra ctmt) {
		System.out.println("table: " + table);
		String qSql = "";
		if(table.equals("sach")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("docgia")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("nhanvien")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("muontra")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?, ?, ?)";
		}
		if(table.equals("chitietmuontra")) {
			qSql = "insert into " + table + " values(?, ?, ?, ?)";
		}
		
		PreparedStatement ps = null;
		
		try {
			
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			if(table.equals("sach")) {
				ps.setString(1, s.getIdSach());
				ps.setString(2, s.getTenSach());
				ps.setString(3, s.getNhaXB());
				ps.setString(4, s.getTenTG());
				ps.setString(5, s.getNamXB());
				ps.setInt(6, s.getGiaSach());
				ps.setString(7, s.getTheLoai());
				ps.setString(8, s.getNgonNgu());
				ps.setInt(9, s.getTrangThai());
			}
			if(table.equals("docgia")) {
				ps.setString(1, dg.getIdDocGia());
				ps.setString(2, dg.getTenDG());
				ps.setString(3, dg.getNgaySinh());
				ps.setString(4, dg.getGioiTinh());
				ps.setString(5, dg.getEmail());
				ps.setString(6, dg.getSoDT());
				ps.setString(7, dg.getDiaChi());
			}
			if(table.equals("nhanvien")) {
				ps.setString(1, nv.getIdNhanVien());
				ps.setString(2, nv.getTenNV());
				ps.setString(3, nv.getNgaySinh());
				ps.setString(4, nv.getGioiTinh());
				ps.setString(5, nv.getDiaChi());
				ps.setString(6, nv.getSoDT());
			}
			if(table.equals("muontra")) {
				ps.setString(1, mt.getIdMuonTra());
				ps.setString(2, mt.getIdDocGia());
				ps.setString(3, mt.getIdNhanVien());
				ps.setString(4, mt.getNgayMuon());
				ps.setString(5, mt.getNgayHenTra());
				ps.setInt(6, mt.getDatCoc());
			}
			if(table.equals("chitietmuontra")) {
				ps.setString(1, ctmt.getIdMuonTra());
				ps.setString(2, ctmt.getIdSach());
				ps.setString(3, ctmt.getNgayTra());
				ps.setInt(4, ctmt.getTienPhat());
			}
			
			
			if(ps.executeUpdate() > 0) {
				System.out.println("\ninsert success.");
				return true;
//				JOptionPane.showMessageDialog(null, "Thêm Thành Công");
			}else 
				System.out.println("insert error");
				return false;
		} catch (SQLException e) {
//			System.out.println("insert error" + e);
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Trùng mã hoặc có trường dữ liệu trống", "Error insert", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public boolean update(String id, Sach s, DocGia dg, NhanVien nv, MuonTra mt, ChiTietMuonTra ctmt) {
		String qSql = "";
		if(s != null) {
			qSql = "update sach set TenSach = ?, NhaXB = ?, TenTG = ?, NamXB = ?, GiaSach = ?, TheLoai = ?, NgonNgu = ? where idSach = ?";
		}
		if(dg != null) {
			qSql = "update docgia set TenDG = ?, NgaySinh = ?, GioiTinh = ?, Email = ?, SoDT = ?, DiaChi = ? where idDocGia = ?";
		}
		if(nv != null) {
			qSql = "update nhanvien set TenNV = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SoDT = ? where idNhanVien = ?";
		}
		if(mt != null) {
			qSql = "update muontra set idDocGia = ?, idNhanVien = ?, NgayMuon = ?, NgayHenTra = ?, DatCoc = ? where idMuonTra = ?";
		}
		if(ctmt != null) {
			qSql = "update chitietmuontra set NgayTra = ?, TienPhat = ? where idMuonTra = ? and idSach = ?";
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
				return true;
			}else {
				System.out.println("\nupdate error");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("update error" + e);
			return false;
		}
	}
	
	public boolean updateTTSach(String idSach, int trangThai) {
		String qSql = "update sach set TrangThai = ? where idSach = ?";
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setInt(1, trangThai);
			ps.setString(2, idSach);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(ps.executeUpdate() > 0) {
				System.out.println("\n update TrangThai success.");
				return true;
			}else return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			System.out.println("\n update TrangThai error.");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean insertTK(TaiKhoan tk) {
		String qSql = "insert into taikhoan values(?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setString(1, tk.getTenTK());
			ps.setString(2, tk.getMatKhau());
			ps.setString(3, tk.getLoaiTK());
			
			if(ps.executeUpdate() > 0) {
				System.out.print("\ninsert TK success.");
				return true;
			}else {
				System.out.println("insert TK error.");
				return false;
			}
		} catch (SQLException e) {
			System.out.print("\ninsert TK error.");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateTK(String idTaiKhoan, TaiKhoan tk) {
		String qSql = "update taikhoan set passWord = ? where idTaiKhoan = ?";
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(qSql);
			ps.setString(1, tk.getMatKhau());
			ps.setString(2, idTaiKhoan);
			
			if(ps.executeUpdate() > 0) {
				System.out.println("update TK success.");
				return true;
			}else {
				System.out.println("update TK error.");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("update TK error --- sql.");
			e.printStackTrace();
			return false;
		}
	}
}

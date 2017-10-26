package quanlythuvien.object;

public class MuonTra {
	private String idMuonTra;
	private String idDocGia;
	private String idNhanVien;
	private String ngayMuon;
	private String ngayHenTra;
	private int datCoc;
	
	public MuonTra() {
		
	}
	public MuonTra(String id, String idDG, String idNV, String ngaymuon, 
			String ngayhentra, int datcoc) {
		this.idMuonTra = id;
		this.idDocGia = idDG;
		this.idNhanVien = idNV;
		this.ngayMuon = ngaymuon;
		this.ngayHenTra = ngayhentra;
		this.datCoc = datcoc;
	}
	
	public String getIdMuonTra() {
		return idMuonTra;
	}
	public void setIdMuonTra(String idMuonTra) {
		this.idMuonTra = idMuonTra;
	}
	public String getIdDocGia() {
		return idDocGia;
	}
	public void setIdDocGia(String idDocGia) {
		this.idDocGia = idDocGia;
	}
	public String getIdNhanVien() {
		return idNhanVien;
	}
	public void setIdNhanVien(String idNhanVien) {
		this.idNhanVien = idNhanVien;
	}
	public String getNgayMuon() {
		return ngayMuon;
	}
	public void setNgayMuon(String ngayMuon) {
		this.ngayMuon = ngayMuon;
	}
	public String getNgayHenTra() {
		return ngayHenTra;
	}
	public void setNgayHenTra(String ngayHenTra) {
		this.ngayHenTra = ngayHenTra;
	}
	public int getDatCoc() {
		return datCoc;
	}
	public void setDatCoc(int datCoc) {
		this.datCoc = datCoc;
	}
}

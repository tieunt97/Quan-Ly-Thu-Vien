package quanlythuvien.object;

public class ChiTietMuonTra {
	private String idMuonTra;
	private String idSach;
	private String ngayTra;
	private int tienPhat;
	
	public ChiTietMuonTra() {
		
	}
	public ChiTietMuonTra(String idMT, String idS, String ngaytra, int tienphat) {
		this.idMuonTra = idMT;
		this.idSach = idS;
		this.ngayTra = ngaytra;
		this.tienPhat = tienphat;
	}
	
	public String getIdMuonTra() {
		return idMuonTra;
	}
	public void setIdMuonTra(String idMuonTra) {
		this.idMuonTra = idMuonTra;
	}
	public String getIdSach() {
		return idSach;
	}
	public void setIdSach(String idSach) {
		this.idSach = idSach;
	}
	public String getNgayTra() {
		return ngayTra;
	}
	public void setNgayTra(String ngayTra) {
		this.ngayTra = ngayTra;
	}
	public int getTienPhat() {
		return tienPhat;
	}
	public void setTienPhat(int tienPhat) {
		this.tienPhat = tienPhat;
	}
}

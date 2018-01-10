package quanlythuvien.object;

public class Sach {
	private String idSach;
	private String tenSach;
	private String nhaXB;
	private String tenTG;
	private String namXB;
	private int giaSach;
	private String theLoai;
	private String ngonNgu;
	private int trangThai;
	
	public Sach(){
		
	}
	public Sach(String idSach, String tenSach, String nhaXB, String tenTG, String namXB,
			int giaSach, String theLoai, String ngonNgu) {
		this.idSach = idSach;
		this.tenSach = tenSach;
		this.nhaXB = nhaXB;
		this.tenTG = tenTG;
		this.namXB = namXB;
		this.giaSach = giaSach;
		this.theLoai = theLoai;
		this.ngonNgu = ngonNgu;
		this.trangThai = 1;
	}
	
	public String getIdSach() {
		return idSach;
	}
	public void setIdSach(String idSach) {
		this.idSach = idSach;
	}
	public String getTenSach() {
		return tenSach;
	}
	public void setTenSach(String tenSach) {
		this.tenSach = tenSach;
	}
	public String getNhaXB() {
		return nhaXB;
	}
	public void setNhaXB(String nhaXB) {
		this.nhaXB = nhaXB;
	}
	public String getTenTG() {
		return tenTG;
	}
	public void setTenTG(String tenTG) {
		this.tenTG = tenTG;
	}
	public String getNamXB() {
		return namXB;
	}
	public void setNamXB(String namXB) {
		this.namXB = namXB;
	}
	public int getGiaSach() {
		return giaSach;
	}
	public void setGiaSach(int giaSach) {
		this.giaSach = giaSach;
	}
	public String getTheLoai() {
		return theLoai;
	}
	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}
	public String getNgonNgu() {
		return ngonNgu;
	}
	public void setNgonNgu(String ngonNgu) {
		this.ngonNgu = ngonNgu;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	
}

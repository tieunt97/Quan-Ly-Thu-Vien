package quanlythuvien.object;

public class NhanVien {
	private String idNhanVien;
	private String tenNV;
	private String ngaySinh;
	private String gioiTinh;
	private String diaChi;
	private String soDT;
	
	public NhanVien() {
		
	}
	public NhanVien(String id, String ten, String ngaysinh, String gioitinh,
			String diachi, String sdt) {
		this.idNhanVien = id;
		this.tenNV = ten;
		this.ngaySinh = ngaysinh;
		this.gioiTinh = gioitinh;
		this.diaChi = diachi;
		this.soDT = sdt;
	}
	
	public String getIdNhanVien() {
		return idNhanVien;
	}
	public void setIdNhanVien(String idNhanVien) {
		this.idNhanVien = idNhanVien;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
}

package quanlythuvien.object;

public class DocGia {
	private String idDocGia;
	private String tenDG;
	private String ngaySinh;
	private String gioiTinh;
	private String email;
	private String soDT;
	private String diaChi;
	
	
	public DocGia() {
		
	}
	
	public DocGia(String id, String ten, String ns, String gt, String email,
			String sdt, String dc) {
		this.idDocGia = id;
		this.tenDG = ten;
		this.ngaySinh = ns;
		this.gioiTinh = gt;
		this.email = email;
		this.soDT = sdt;
		this.diaChi = dc;
	}

	public String getIdDocGia() {
		return idDocGia;
	}

	public void setIdDocGia(String idDocGia) {
		this.idDocGia = idDocGia;
	}

	public String getTenDG() {
		return tenDG;
	}

	public void setTenDG(String tenDG) {
		this.tenDG = tenDG;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
}

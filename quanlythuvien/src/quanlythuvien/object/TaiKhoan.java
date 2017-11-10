package quanlythuvien.object;

public class TaiKhoan {
	private String tenTK, matKhau, loaiTK;
	
	public TaiKhoan () {
		
	}
	
	public TaiKhoan(String tenTK, String matKhau, String loaiTK) {
		this.tenTK = tenTK;
		this.matKhau = matKhau;
		this.loaiTK = loaiTK;
	}

	public String getTenTK() {
		return tenTK;
	}

	public void setTenTK(String tenTK) {
		this.tenTK = tenTK;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getLoaiTK() {
		return loaiTK;
	}

	public void setLoaiTK(String loaiTK) {
		this.loaiTK = loaiTK;
	}
	
}

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
	
	public Sach(){
		
	}
	public Sach(String id, String ten, String nxb, String tg, String namxb,
			int gia, String tl, String nn) {
		idSach = id;
		tenSach = ten;
		nhaXB = nxb;
		tenTG = tg;
		namXB = namxb;
		giaSach = gia;
		theLoai = tl;
		ngonNgu = nn;
		
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
}

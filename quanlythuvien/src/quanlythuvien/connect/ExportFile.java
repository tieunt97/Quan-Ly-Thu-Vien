package quanlythuvien.connect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

public class ExportFile { 
	
	public void printHeader(String path, XWPFDocument doc, String tableName, String title) throws IOException, URISyntaxException, InvalidFormatException {
		 XWPFParagraph par = doc.createParagraph();
		 XWPFRun run = par.createRun();
		 
		 run.setText("TRƯỜNG ĐẠI HỌC BÁCH KHOA HÀ NỘI");
		 run.setText("        CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
		 run.setFontFamily("Cambria (Headings)");
		 run.setFontSize(11);
		 run.setBold(true);
		 run.addBreak();
		 run.setText("              Thư Viện Tạ Quang Bửu");
		 run.setText("                                               Độc lập - tự do - hạnh phúc");
		 run.addBreak();
		 run.setText("          Nguyễn Tài Tiêu - 20153752    ");
		 run.setText("                                      -----------------------------------------");
		 run.addBreak();
		 run.setText("                          ");
		 InputStream imgFile = this.getClass().getResourceAsStream("/bk.jpg");
		 run.addPicture(imgFile, XWPFDocument.PICTURE_TYPE_PNG, "3", Units.toEMU(45), Units.toEMU(68));
		 
		 String date[] = Check.getDate();
		 XWPFParagraph par1 = doc.createParagraph();
		 XWPFRun run1 = par1.createRun();
		 run1.setText("Ngày " + date[0] + " tháng " + date[1] + " năm " + date[2]);
		 run1.setFontFamily("Cambria (Headings)");
		 run1.setItalic(true);
		 run1.setItalic(true);
		 run1.addBreak();
		 run1.addBreak();
		 par1.setAlignment(ParagraphAlignment.RIGHT);
		 
		 XWPFParagraph par2 = doc.createParagraph();
		 XWPFRun run2 = par2.createRun();
		 run2.setText(tableName.toUpperCase());
		 run2.addBreak();
		 run2.setText(title.toUpperCase());
		 run2.addBreak();
		 run2.setFontFamily("Cambria (Headings)");
		 run2.setItalic(true);
		 run2.setFontSize(14);
		 par2.setAlignment(ParagraphAlignment.CENTER);
		 
		 FileOutputStream fout = new FileOutputStream(new File(path));
		 doc.write(fout);
		 fout.close();
	}
	
	public void printContentDG(String path, XWPFDocument doc, JTable table) throws IOException {
		int rows = 1;
		int cols = 1;
		if(table == null) {
			return;
		}else {
			rows = table.getModel().getRowCount() + 1;
			cols = table.getColumnCount() + 1;
		}
		if(cols > 4) {
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setWidth(500);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText("Mã Độc Giả");
						if(j == 2) cell.setText("Tên Độc Giả");
						if(j == 3) cell.setText("Ngày Sinh");
						if(j == 4) cell.setText("Giới Tính");
						if(j == 5) cell.setText("Email");
						if(j == 6) cell.setText("Số ĐT");
						if(j == 7) cell.setText("Địa Chỉ");
					}else {
						if(j == 0) cell.setText(Integer.toString(i));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 3) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 4) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 5) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 6) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 7) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
					}
					
				}
			}
		}else {
			cols -= 1;
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText(table.getModel().getColumnName(1));
						if(j == 2) cell.setText("Số Lượng");
					}else {
						if(j == 0) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j));
					}
					
				}
			}
		}
		
		
		FileOutputStream fout = new FileOutputStream(new File(path));
		doc.write(fout);
		fout.close();
	}
	
	public void printContentNV(String path, XWPFDocument doc, JTable table) throws IOException {
		int rows = 1;
		int cols = 1;
		if(table == null) {
			return;
		}else {
			rows = table.getModel().getRowCount() + 1;
			cols = table.getColumnCount() + 1;
		}
		if(cols > 4) {
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setWidth(500);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText("Mã Nhân Viên");
						if(j == 2) cell.setText("Tên Nhân Viên");
						if(j == 3) cell.setText("Ngày Sinh");
						if(j == 4) cell.setText("Giới Tính");
						if(j == 5) cell.setText("Địa Chỉ");
						if(j == 6) cell.setText("Số ĐT");
					}else {
						if(j == 0) cell.setText(Integer.toString(i));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 3) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 4) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 5) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 6) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
					}
					
				}
			}
		}else {
			cols -= 1;
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText(table.getModel().getColumnName(1));
						if(j == 2) cell.setText(table.getModel().getColumnName(2));
					}else {
						if(j == 0) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j));
					}
					
				}
			}
		}
		
		
		FileOutputStream fout = new FileOutputStream(new File(path));
		doc.write(fout);
		fout.close();
	}
	
	public void printContentS(String path, XWPFDocument doc, JTable table) throws IOException {
		int rows = 1;
		int cols = 1;
		if(table == null) {
			return;
		}else {
			rows = table.getModel().getRowCount() + 1;
			cols = table.getColumnCount() + 1;
		}
		if(cols > 4) {
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setWidth(500);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText("Mã Sách");
						if(j == 2) cell.setText("Tên Sách");
						if(j == 3) cell.setText("Nhà Xuất Bản");
						if(j == 4) cell.setText("Tên Tác Giả");
						if(j == 5) cell.setText("Năm Xuất Bản");
						if(j == 6) cell.setText("Giá Sách");
						if(j == 7) cell.setText("Thể Loại");
						if(j == 8) cell.setText("Ngôn Ngữ");
					}else {
						if(j == 0) cell.setText(Integer.toString(i));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 3) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 4) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 5) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 6) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 7) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 8) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
					}
					
				}
			}
		}else {
			cols -= 1;
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText(table.getModel().getColumnName(1));
						if(j == 2) cell.setText("Số Lượng");
					}else {
						if(j == 0) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j));
					}
					
				}
			}
		}
		
		
		FileOutputStream fout = new FileOutputStream(new File(path));
		doc.write(fout);
		fout.close();
	}
	public void printContentMT(String path, XWPFDocument doc, JTable table) throws IOException {
		int rows = 1;
		int cols = 1;
		if(table == null) {
			return;
		}else {
			rows = table.getModel().getRowCount() + 1;
			cols = table.getColumnCount() + 1;
		}
		if(cols > 4) {
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setWidth(500);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText("Mã Mượn Trả");
						if(j == 2) cell.setText("Mã Độc Giả");
						if(j == 3) cell.setText("Mã Nhân Viên");
						if(j == 4) cell.setText("Ngày Mượn");
						if(j == 5) cell.setText("Ngày Hẹn Trả");
						if(j == 6) cell.setText("Đặt Cọc");
					}else {
						if(j == 0) cell.setText(Integer.toString(i));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 3) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 4) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 5) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
						if(j == 6) cell.setText((String) table.getModel().getValueAt(i - 1, j - 1));
					}
					
				}
			}
		}else {
			cols -= 1;
			XWPFTable xtable = doc.createTable(rows, cols);
			setTableAlignment(xtable, STJc.CENTER);
			xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
			for(int i = 0; i < rows; i++) {
				XWPFTableRow row= xtable.getRow(i);
				for(int j = 0; j < cols; j++) {
					XWPFTableCell cell = row.getCell(j);
					if (i == 0) {
						if(j == 0) cell.setText("TT");
						if(j == 1) cell.setText(table.getModel().getColumnName(1));
						if(j == 2) cell.setText(table.getModel().getColumnName(2));
					}else {
						if(j == 0) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 1) cell.setText((String) table.getModel().getValueAt(i - 1, j));
						if(j == 2) cell.setText((String) table.getModel().getValueAt(i - 1, j));
					}
					
				}
			}
		}
		
		
		FileOutputStream fout = new FileOutputStream(new File(path));
		doc.write(fout);
		fout.close();
	}
	
	public void printContentPhieuMT(String path, XWPFDocument doc, String[] muonTra, MyConnectDB connDB) throws IOException, SQLException {
		if(muonTra == null) {
			return;
		}
		String idMuonTra = muonTra[0];
		String idDocGia = muonTra[1];
		String idNhanVien = muonTra[2];
		String ngayMuon = muonTra[3];
		String ngayHenTra = muonTra[4];
		String datCoc = muonTra[5];
		String tenDG = "", tenNV = "";
		
		XWPFParagraph par = doc.createParagraph();
		XWPFRun run = par.createRun();
		
		run.setText("                               Mã Mượn Trả:    " + idMuonTra);
		run.addBreak();
		ResultSet rs = connDB.getNames("DocGia", "tenDG", "idDocGia", idDocGia);
		while(rs.next()) {
			tenDG = rs.getString(1);
		}
		run.setText("                               Mã Độc Giả:        " + idDocGia + "                         Tên Độc Giả:       " + tenDG);
		run.addBreak();
		ResultSet rs1 = connDB.getNames("NhanVien", "tenNV", "idNhanVien", idNhanVien);
		while(rs1.next()) {
			tenNV = rs1.getString(1);
		}
		run.setText("                               Mã Nhân Viên:   " + idNhanVien + "                         Tên Nhân Viên:   " + tenNV);
		run.addBreak();
		run.setText("                               Ngày Mượn:       " + ngayMuon + "                 Ngày Hẹn Trả:     " + ngayHenTra);
		run.addBreak();
		run.setText("                               Tiền Cọc:             " + datCoc);
		run.addBreak();
//		par.setAlignment(ParagraphAlignment.CENTER);
		
		
		XWPFTable xtable = doc.createTable();
		setTableAlignment(xtable, STJc.CENTER);
		xtable.setWidth(500);
		xtable.setCellMargins(200, 200, 200, 200); //thiết lập kích cỡ từng ô trong bảng
				
		int tienPhat = 0;
		XWPFTableRow titleRow = xtable.getRow(0);
		titleRow.getCell(0).setText("TT");
		titleRow.addNewTableCell().setText("Mã Mượn Trả");
		titleRow.addNewTableCell().setText("Mã Sách");
		titleRow.addNewTableCell().setText("Tên Sách");
		titleRow.addNewTableCell().setText("Ngày Trả");
		titleRow.addNewTableCell().setText("Tiền Phạt");
		
		ResultSet rs2 = connDB.getContentTablePhieuMT(idMuonTra);
		int i = 1;
		while(rs2.next()) {
			XWPFTableRow nextRow = xtable.createRow();
			nextRow.getCell(0).setText(i++ + "");
			nextRow.getCell(1).setText(rs2.getString(1));
			nextRow.getCell(2).setText(rs2.getString(2));
			nextRow.getCell(3).setText(rs2.getString(3));
			nextRow.getCell(4).setText(rs2.getString(4));
			nextRow.getCell(5).setText(rs2.getString(5));
			tienPhat += Integer.parseInt(rs2.getString(5));
		}
			XWPFParagraph par1 = doc.createParagraph();
			XWPFRun run1 = par1.createRun();
			run1.addBreak();
			run1.setItalic(true);
			run1.setFontSize(12);
			run1.setText("Tổng tiền phạt:           " + tienPhat + " đ");
			run1.addBreak();
			run1.setText("Làm tròn:                 " + Math.round((double) tienPhat/1000)*1000 + " đ");
			par1.setAlignment(ParagraphAlignment.RIGHT);
			
		
		
		FileOutputStream fout = new FileOutputStream(new File(path));
		doc.write(fout);
		fout.close();
		
	}
	
	public void printEnd(String path, XWPFDocument doc) throws IOException {
		XWPFParagraph par = doc.createParagraph();
		XWPFRun run = par.createRun();
		run.addBreak();
		run.addBreak();
		run.setText("                            Người lập                 ");
		run.setText("                                                         Xác nhận của thủ thư");
		run.setFontFamily("CamBria (Headings)");
		run.setBold(true);
		run.setFontSize(11);
		
		XWPFParagraph par1 = doc.createParagraph();
		XWPFRun run1 = par1.createRun();
		run1.setText("                      (Ký ghi rõ họ tên)                  ");
		run1.setText("                                                      (Ký ghi rõ họ tên)");
		run1.setFontFamily("Cambria (Headings)");
		run1.setColor("A7A2A1");
		run1.setFontSize(11);
		run1.setItalic(true);
		run1.addBreak();
		
		
		FileOutputStream fout = new FileOutputStream(new File(path));
		doc.write(fout);
		fout.close();
	}
	
	//Can chinh table
	private void setTableAlignment(XWPFTable table, STJc.Enum justification) {
	    CTTblPr tblPr = table.getCTTbl().getTblPr();
	    CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
	    jc.setVal(justification);
	}
//	CTTbl cttblp = xtable.getCTTbl();
////			CTTblPr cttblpr = cttblp.getTblPr();
//	CTTblPr cttblpr;
//	cttblpr = (cttblp.getTblPr() == null ? cttblp.addNewTblPr() : cttblp.getTblPr());
//	
//	
////			//table direction
////			cttblpr.addNewBidiVisual().setVal(STOnOff.ON);
//	
//	//table alignment
//	CTJc ctjc = (cttblpr.isSetJc() ? cttblpr.getJc() : cttblpr.addNewJc());
//	ctjc.setVal(STJc.CENTER);
}

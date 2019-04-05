package quanlythuvien.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlythuvien.object.ChiTietMuonTra;
import quanlythuvien.object.DocGia;
import quanlythuvien.object.MuonTra;
import quanlythuvien.object.NhanVien;
import quanlythuvien.object.Sach;


public class ImportFile {
	
	public void importFileBook(String pathFile, MyConnectDB conn) throws IOException{
		int count = 0, count1 = 0;
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        Row nextRow;
        if(iterator.hasNext()) nextRow = iterator.next();
        
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            ArrayList<String> dataBook  = new ArrayList<String>();
            
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String data = "";
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        data = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        data = Integer.toString((int) cell.getNumericCellValue());
                        break;
                    default: 
                    	data = "";
                    	break;
                }
                dataBook.add(data);
                System.out.print(" - ");
            }
            System.out.println("độ rộng: " + dataBook.size());
            Sach s = new Sach(dataBook.get(0).toUpperCase(), dataBook.get(1), dataBook.get(2), dataBook.get(3),
            		dataBook.get(4), Integer.parseInt(dataBook.get(5)), dataBook.get(6), dataBook.get(7));
            if(conn.insert("sach", s, null, null, null, null)) count++;
            else count1++;
            System.out.println();
        }
        
        JOptionPane.showMessageDialog(null, "Có " + count + " bản ghi thêm thành công\n" + "Có " + count1 + " bản ghi lỗi");
         
        workbook.close();
        inputStream.close();
	}
	public void importFileDG(String pathFile, MyConnectDB conn) throws IOException{
		int count = 0, count1 = 0;
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) {
			nextRow = iterator.next();
		}
		
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataDG  = new ArrayList<String>();
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Integer.toString((int) cell.getNumericCellValue());
					break;
				default: 
					data = "";
					break;
				}
				dataDG.add(data);
				System.out.print(" - ");
			}
			System.out.println("độ rộng: " + dataDG.size());
			DocGia dg = null;
			try {
				dg = new DocGia(dataDG.get(0).toUpperCase(), dataDG.get(1), dataDG.get(2), dataDG.get(3), dataDG.get(4), dataDG.get(5), dataDG.get(6));
				if(conn.insert("docgia", null, dg, null, null, null)) count++;
				else count1++;
			}catch(Exception exc) {
				System.out.println("Error:" + exc);
			}
			System.out.println();
		}
		JOptionPane.showMessageDialog(null, "Có " + count + " bản ghi thêm thành công\n" + "Có " + count1 + " bản ghi lỗi");
		
		workbook.close();
		inputStream.close();
	}
	public void importFileNV(String pathFile, MyConnectDB conn) throws IOException{
		int count = 0, count1 = 0;
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) nextRow = iterator.next();
		
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataNV  = new ArrayList<String>();
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Integer.toString((int) cell.getNumericCellValue());
					break;
				default: 
					data = "";
					break;
				}
				dataNV.add(data);
				System.out.print(" - ");
			}
			System.out.println("độ rộng: " + dataNV.size());
			NhanVien nv = new NhanVien(dataNV.get(0), dataNV.get(1), dataNV.get(2), dataNV.get(3), dataNV.get(4), dataNV.get(5));
			if(conn.insert("nhanvien", null, null, nv, null, null)) count++;
			else count1++;
			System.out.println();
		}
		JOptionPane.showMessageDialog(null, "Có " + count + " bản ghi thêm thành công\n" + "Có " + count1 + " bản ghi lỗi");
		
		workbook.close();
		inputStream.close();
	}
	
	public void importFileMuonTra(String pathFile, MyConnectDB conn) throws IOException {
		int count = 0, count1 = 0;
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		Row nextRow;
		if(iterator.hasNext()) nextRow = iterator.next();
		
		while (iterator.hasNext()) {
			nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ArrayList<String> dataMT  = new ArrayList<String>();
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Integer.toString((int) cell.getNumericCellValue());
					break;
				default: 
					data = "";
					break;
				}
				dataMT.add(data);
				System.out.print(" - ");
			}
			System.out.println("độ rộng: " + dataMT.size());
			MuonTra mt = new MuonTra(dataMT.get(0), dataMT.get(1), dataMT.get(2), dataMT.get(3), dataMT.get(4), Integer.parseInt(dataMT.get(5)));
			if(conn.insert("muontra", null, null, null, mt, null)) count++;
			else count1++;
			System.out.println();
		}
		JOptionPane.showMessageDialog(null, "Có " + count + " bản ghi mượn trả thêm thành công\n" + "Có " + count1 + " bản ghi lỗi");
		
		count = 0; count1 = 0;
		Sheet secondSheet = workbook.getSheetAt(1);
		Iterator<Row> iterator1 = secondSheet.iterator();
		Row nextRow1;
		if(iterator1.hasNext()) nextRow1 = iterator1.next();
		
		while (iterator1.hasNext()) {
			nextRow1 = iterator1.next();
			Iterator<Cell> cellIterator = nextRow1.cellIterator();
			ArrayList<String> dataCTMT  = new ArrayList<String>();
			
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = "";
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					data = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					data = Integer.toString((int) cell.getNumericCellValue());
					break;
				default: 
					data = "";
					break;
				}
				dataCTMT.add(data);
				System.out.print(" - ");
			}
			System.out.println("độ rộng: " + dataCTMT.size());
			ChiTietMuonTra ctmt = new ChiTietMuonTra(dataCTMT.get(0), dataCTMT.get(1), dataCTMT.get(2), Integer.parseInt(dataCTMT.get(3)));
			if(conn.insert("chitietmuontra", null, null, null, null, ctmt)) count++;
			else count1++;
			System.out.println();
		}
		JOptionPane.showMessageDialog(null, "Có " + count + " bản ghi chi tiết mượn trả thêm thành công\n" + "Có " + count1 + " bản ghi lỗi");
		
		workbook.close();
		inputStream.close();
	}
	
}

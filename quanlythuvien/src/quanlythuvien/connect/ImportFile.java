package quanlythuvien.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import quanlythuvien.object.DocGia;
import quanlythuvien.object.NhanVien;
import quanlythuvien.object.Sach;


public class ImportFile {
	
	MyConnectDB conn = new MyConnectDB();
	
	public void importFileBook(String pathFile) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
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
            conn.insert("Sach", s, null, null, null, null);
            System.out.println();
        }
         
        workbook.close();
        inputStream.close();
	}
	public void importFileDG(String pathFile) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
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
			DocGia dg = new DocGia(dataDG.get(0).toUpperCase(), dataDG.get(1), dataDG.get(2), dataDG.get(3), dataDG.get(4), dataDG.get(5), dataDG.get(6));
			conn.insert("DocGia", null, dg, null, null, null);
			System.out.println();
		}
		
		workbook.close();
		inputStream.close();
	}
	public void importFileNV(String pathFile) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File(pathFile));
		
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
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
			conn.insert("NhanVien", null, null, nv, null, null);
			System.out.println();
		}
		
		workbook.close();
		inputStream.close();
	}
	
}

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
	
	public static void main(String[] args) {
		ImportFile imp = new ImportFile();
		try {
			imp.importFileBook("C:\\Users\\tieu_nt\\Desktop\\demoPOI\\Excel\\Book.xlsx");
			System.out.println("importFile success.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
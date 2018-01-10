package quanlythuvien.connect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Check {
	
	public static boolean checkDate(String checkDate) {
		if(checkDate == null){
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        
        try {
            Date date1 = sdf.parse(checkDate);
            String[] date = checkDate.split("-");
            if(date[0].length() != 2 || date[1].length() != 2 ||date[2].length() != 4) return false;
    		String[] dateNow = getDate();
    		int day = Integer.parseInt(date[0]);
    		int month = Integer.parseInt(date[1]);
    		int year = Integer.parseInt(date[2]);
    		int dayNow = Integer.parseInt(dateNow[0]);
    		int monthNow = Integer.parseInt(dateNow[1]);
    		int yearNow = Integer.parseInt(dateNow[2]);
    		if(yearNow < year || year < 1945) return false;
    		else if(yearNow == year && monthNow < month) return false;
    		else if(yearNow == year && monthNow == month && dayNow < day) return false;
    		else return true;
    		
        } 
        catch (ParseException e) {        
            return false;
        }
	}
	
	public static boolean checkDateMT(String ngayMuon, String ngayHenTra) {
		if(ngayHenTra == null){
            return false;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        
        try {
            Date date1 = sdf.parse(ngayHenTra);
            String[] dateHT = ngayHenTra.trim().split("-");
            if(dateHT[0].length() != 2 || dateHT[1].length() != 2 ||dateHT[2].length() != 4) return false;
    		int yearHT = Integer.parseInt(dateHT[2]);
    		String[] dateM = ngayMuon.trim().split("-");
    		int yearM = Integer.parseInt(dateM[2]);
    		int longDateHT = Integer.parseInt(dateHT[2].concat(dateHT[1]).concat(dateHT[0]));
    		int longDateM = Integer.parseInt(dateM[2].concat(dateM[1]).concat(dateM[0]));
    		if(longDateHT < longDateM) return false;
    		else if(yearHT > yearM + 1) return false;
    		else if(yearHT == yearM) {
    			int subDate = longDateHT - longDateM;
    			if(subDate <= 600) return true;
    		}
    		else if(yearHT == yearM + 1) {
    			int subDate = (longDateM % 10000) - (longDateHT% 10000);
    			if(subDate >= 600) return true;
    		}
    		
        } 
        catch (ParseException e) {        
            return false;
        }
        return false;
	}
	
	public static boolean checkPhone(String phone) {
		int length = phone.length();
		if(length < 9 || length > 12) return false;
		for(int i = 0; i < length; i++) {
			if(phone.charAt(i) < '0' || phone.charAt(i) > '9') return false;
		}
		return true;
	}
	
	public static boolean checkInt(String str) {
		int length = str.length();
		if(length < 1) return false;
		for(int i = 0; i< length; i++) {
			if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
		}
		return true;
	}
	
	public static int getTienPhat(String ngayMuon, String ngayHenTra, String ngayTra, int datCoc) {
		if(ngayTra.equals("")) return 0;
		else {
			try {
				
				String[] dateM = ngayMuon.trim().split("-");
				String[] dateHT = ngayHenTra.trim().split("-");
				String[] dateT = ngayTra.trim().split("-");
				if(dateT[0].length() != 2 || dateT[1].length() != 2 || dateT[2].length() != 4) return -1;
				
				int dayT = Integer.parseInt(dateT[0]);
				int monthT = Integer.parseInt(dateT[1]);
				int yearT = Integer.parseInt(dateT[2]);
				int dayHT = Integer.parseInt(dateHT[0]);
				int monthHT = Integer.parseInt(dateHT[1]);
				int yearHT = Integer.parseInt(dateHT[2]);
				
				if(yearT > yearHT + 1) return datCoc;
				
				int longDateM = Integer.parseInt(dateM[2].concat(dateM[1].concat(dateM[0])));
				int longDateHT = Integer.parseInt(dateHT[2].concat(dateHT[1].concat(dateHT[0])));
				int longDateT = Integer.parseInt(dateT[2].concat(dateT[1].concat(dateT[0])));
				
				if(longDateT < longDateM) return -1;
				else if(longDateT <= longDateHT) return 0;
				else if(longDateT > longDateHT) {
					if(yearHT == yearT) {
		    			int subDate = longDateT - longDateHT;
		    			int day = dayT > dayHT ? (dayT - dayHT):(31 - dayHT + dayT);
		    			int subMonth = monthT - monthHT;
		    			subMonth = (subMonth > 0 && dayT < dayHT)?(subMonth - 1): subMonth;
		    			if(subDate <= 300) return (int) (datCoc*(subMonth*0.15 + day*0.0048) + 1000);
		    			else return datCoc;
		    		}
		    		else if(yearHT == yearT - 1 ) {
		    			int subDate =  (longDateHT% 10000) - (longDateT % 10000);
		    			int Cmonth = 0;
		    			int day = dayT > dayHT ? (dayT - dayHT):(31 - dayHT + dayT);
		    			int sumMonth = monthHT + monthT;
		    			if(subDate < 900) return datCoc;
		    			switch(monthHT) {
		    			case 10: if(monthT == 12 || monthT == 10 || monthT == 11 || monthT == 1)
		    				Cmonth = (sumMonth >= 2*monthHT)?(monthT%monthHT):3;
		    				else return datCoc;
		    			break;
		    			case 11: if(monthT == 11 || monthT == 12 || monthT == 1 || monthT == 2) 
		    				Cmonth = (sumMonth >= 2*monthHT)?(monthT%monthHT):(monthT + 1);
		    				else return datCoc;
		    			break;
		    			case 12: if(monthT == 12 || monthT == 1 || monthT == 2 || monthT == 3) 
		    				Cmonth = (sumMonth >= 2*monthHT)?(monthT%monthHT):(monthT);
		    				else return datCoc;
		    			break;
		    			}
		    			
		    			Cmonth = (Cmonth > 0 && dayT < dayHT)?(Cmonth - 1): Cmonth;
//		    			System.out.println("subdate" +subDate);
		    			if(subDate >= 300) return (int) (datCoc*(Cmonth*0.15 + day*0.0048));
		    			else return -2;
		    		}
					
				}
			}catch(Exception exc) {
				System.out.println("Error: " + exc);
				return -1;
			}
			return -1;
		}
	}
	
	public static String[] getDate() {
		String date[] = new String[3];
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //định dạng ngày
	    Date today = new Date(); 
	    String stringDate = dateFormat.format(today); //lấy ngày hiện tại
	    date = stringDate.split("-");
	    
		return date;
	}
}


package com.xmledit;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import com.upload.dto.Reader;

public class ExcelRead {
	
	public  Map<String,Object> getParent(File excel) throws Exception {
		  Map<String, Object> readedExcelHash=new HashMap<>();
		try {
			FileInputStream file = new FileInputStream(excel);
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    List<Reader> readExcelList = new ArrayList<>();
		    Iterator <Row> rowIterator = sheet.iterator();
		    Reader model = null;
		    int rowNo=1;
		    while (rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		        model = new Reader();
		        Cell ledger=row.getCell(0);
	            Cell parent=row.getCell(1);
	            	if (ledger==null ) {
	            		readedExcelHash.put("Error","Ledger Name not Entered at record no. "+rowNo);
	            		workbook.close();
	            		  file.close();
	            		return readedExcelHash;
	            	}else if (parent==null) {
	            		readedExcelHash.put("Error","Parent Category not Entered at record no. "+rowNo);
	            		workbook.close();  
	            		file.close();
	            		return readedExcelHash;
	            	}
	            	else {
	            		model.setName(ledger.getStringCellValue());
	            		model.setParent(parent.getStringCellValue());
	            		readExcelList.add(model);
	            		rowNo++;
	            	}   	
		    }
		    readedExcelHash.put("List",readExcelList ) ;
		    readedExcelHash.put("Error", null);
		    workbook.close();
		    file.close();
		  return readedExcelHash;
		}  catch (Exception e) {
		    e.printStackTrace();
		 throw e;
		}
	}

}
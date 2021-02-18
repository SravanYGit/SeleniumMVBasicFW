package com.setty.automation.reUsableComponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataUtilities {
//interaction (reading or writing data) with data sheets like execution scheduler, regression or any other data sheet will be implemented in this class
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream  fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	List<String> expectedCarriers = new ArrayList<String>();
	
	public dataUtilities(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index ==-1) {
			return 0;
		}
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum();
			return number;
		}	
	}
	
	public int getColumnCount(String sheetName, int rowNum) {
		int index = workbook.getSheetIndex(sheetName);
		if (index ==-1) {
			return 0;
		}
		else {
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);
			int number = row.getLastCellNum();
			return number;
			
		}
	}
	public String getCellData(String SheetName, String colName, int rowNum ) {
		
		try {
		String returnvalue;
		if (rowNum<0) {
			return "";
		}
		int index = workbook.getSheetIndex(SheetName);
		int col_Num = -1;
		if (index==-1) {
			return "";
		}
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		for (int i =0;i < row.getLastCellNum();i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
				col_Num = i;
			}
			
		}
		if (col_Num == -1) {
			return "";
		}
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum);
		if (row == null) {
			return "";
		}
		cell = row.getCell(col_Num);
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == cell.getCellType().STRING) {
		return cell.getStringCellValue();	
		}
		else if(cell.getCellType() == cell.getCellType().NUMERIC || cell.getCellType() == cell.getCellType().FORMULA) {
			String cellText = String.valueOf(cell.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Calendar cal = Calendar.getInstance();
				cal.setTime(HSSFDateUtil.getJavaDate(d));
				cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
				cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
			}
			return cellText;
		}
		else if (cell.getCellType() == cell.getCellType().BLANK) {
			return "";
		}
		else {
			return String.valueOf(cell.getBooleanCellValue());
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or Column " + " does not exist";
		}
	}
	
public String getCellData(String SheetName, int colNum, int rowNum ) {
		
		try {
		String returnvalue;
		if (rowNum<0) {
			return "";
		}
		int index = workbook.getSheetIndex(SheetName);
		
		if (index==-1) {
			return "";
		}
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum);
		if (row == null) {
			return "";
		}
		cell = row.getCell(colNum);
		if (cell == null) {
			return "";
		}
		
		if (cell.getCellType() == cell.getCellType().STRING) {
		return cell.getStringCellValue();	
		}
		else if(cell.getCellType() == cell.getCellType().NUMERIC || cell.getCellType() == cell.getCellType().FORMULA) {
			String cellText = String.valueOf(cell.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Calendar cal = Calendar.getInstance();
				cal.setTime(HSSFDateUtil.getJavaDate(d));
				cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
				cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
			}
			return cellText;
		}
		else if (cell.getCellType() == cell.getCellType().BLANK) {
			return "";
		}
		else {
			return String.valueOf(cell.getBooleanCellValue());
		}
		}
		catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or Column " + " does not exist";
		}
	}

public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
	try {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		
		if (rowNum < 0) {
			return false;
		}
		int index = workbook.getSheetIndex(sheetName);
		int colNum = -1;
		 if (index == -1) {
			 return false;
		}
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		for (int i=0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
				colNum = i;
			}
		}
		if (colNum == -1) {
			return false;
		}
		sheet.autoSizeColumn(colNum);
		row = sheet.getRow(rowNum);
		
		if (row == null) {
			row = sheet.createRow(rowNum);
		}
		cell = row.getCell(colNum);
		if (cell == null) {
			cell = row.createCell(colNum);	
		}
		cell.setCellValue(data);
		
		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.close();
			
	}
	catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	return true;
}

public boolean addSheet(String sheetName) {
	FileOutputStream fileOut;
	
	try {
		workbook.createSheet(sheetName);
		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.close();
	}
	catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	return true;
}

public boolean removeSheet(String sheetName) {
	int index = workbook.getSheetIndex(sheetName);
	if (index == -1) {
		return false;
	}
	FileOutputStream fileOut;
	try {
		workbook.removeSheetAt(index);
		fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.close();
	}
	catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	return true;	
}

public boolean isSheetExists(String sheetName) {
	int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				index = workbook.getSheetIndex(sheetName.toUpperCase());
				if (index == -1) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
			}
}

public int getColumnCount(String sheetName) {
	
	if(!isSheetExists(sheetName)) {
		return -1;
	}
	sheet = workbook.getSheet(sheetName);
	row = sheet.getRow(0);
	if (row == null) {
		return -1;
	}
	return row.getLastCellNum();
}

public int getCellRowNum(String sheetName, String ColName, String cellValue) {
	for (int i=2; i<=getRowCount(sheetName);i++) {
		if (getCellData(sheetName, ColName, i).equalsIgnoreCase(cellValue)) {
			return i;
		}
	}
	return -1;
}

public List<String> readExcel(String filePath, String fileName, String sheetName) {
	try {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		}
		else if (fileExtensionName.equals(".xls")) {
			workbook = new XSSFWorkbook(inputStream);
		}
		else if (fileExtensionName.equals(".csv")) {
			workbook = new XSSFWorkbook(inputStream);
		}
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 0;i < rowCount + 1; i++) {
			row = sheet.getRow(i);
			expectedCarriers.add(row.getCell(0).getStringCellValue().trim());
		}
		System.out.println(expectedCarriers);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return expectedCarriers;
}

public Map<String, String> readDataToMap(String filePath, String fileName, String sheetName){
	Map<String, String> hashmap = new HashMap<String, String>();
	try {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		}
		else if (fileExtensionName.equals(".xls")) {
			workbook = new XSSFWorkbook(inputStream);
		}
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 0;i < rowCount + 1; i++) {
			row = sheet.getRow(i);
			hashmap.put(row.getCell(0).getStringCellValue().trim(), row.getCell(1).getStringCellValue().trim().replace(".0", ""));
		}
	}
	catch (Exception e) {
		e.getMessage();
	}
	return hashmap;
}
}


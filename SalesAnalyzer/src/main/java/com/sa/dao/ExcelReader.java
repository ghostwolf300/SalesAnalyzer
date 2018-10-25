package com.sa.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.sa.to.SurveyDataTO;

public class ExcelReader {
	
	public static final int COL_STORE_ID=0;
	public static final int COL_POSTCODE=1;
	public static final int COL_TRANSACTION=3;
	public static final int COL_AMOUNT=8;
	
	private HSSFWorkbook workBook=null;
	
	public ExcelReader(File f){
		this.setExcelFile(f);
	}
	
	public List<SurveyDataTO> getSurveyDataList(){
		List<SurveyDataTO> dataList=new ArrayList<SurveyDataTO>();
		int sheetCount=workBook.getNumberOfSheets();
		for(int i=0;i<sheetCount;i++){
			Sheet sheet=workBook.getSheetAt(i);
			System.out.println(sheetCount+" "+sheet.getSheetName());
			Iterator<Row> ri=sheet.rowIterator();
			//skip header row
			ri.next();
			int recCount=0;
			while(ri.hasNext()){
				recCount++;
				Row row=ri.next();
				Cell cell=row.getCell(0);
				if(cell!=null && cell.getCellType()!=CellType.BLANK){
					SurveyDataTO data=readRow(row);
					data.setResultId(recCount);
					dataList.add(data);	
				}
				else{
					break;
				}
			}
			
		}
		return dataList;
	}
	
	private SurveyDataTO readRow(Row row){
		SurveyDataTO data=new SurveyDataTO();
		Cell cell=null;
		try{
			cell=row.getCell(ExcelReader.COL_STORE_ID);
			data.setStoreId(getStringCellValue(cell));
			cell=row.getCell(ExcelReader.COL_POSTCODE);
			data.setPostcode(getStringCellValue(cell));
			cell=row.getCell(ExcelReader.COL_AMOUNT);
			data.setSales(getDoubleCellValue(cell));
		}
		catch(Exception e){
			e.printStackTrace();
			
			System.out.println(row.getSheet().getSheetName()+" row "+row.getRowNum()+" "+row.getCell(ExcelReader.COL_AMOUNT));
		}
		return data;
	}
	
	private String getStringCellValue(Cell cell){
		String value=null;
		if(cell.getCellType()==CellType.NUMERIC) {
			value=String.valueOf((int)cell.getNumericCellValue());
		}
		else if(cell.getCellType()==CellType.STRING) {
			value=cell.getStringCellValue();
		}
		else {
			
		}
		return value;
	}
	
	private double getDoubleCellValue(Cell cell){
		double value=-1;
		
		if(cell.getCellType()==CellType.NUMERIC) {
			value=cell.getNumericCellValue();
		}
		else if(cell.getCellType()==CellType.STRING) {
			value=Double.parseDouble(cell.getStringCellValue());
		}
		else {
			
		}
		return value;
	}
	
	
	public void setExcelFile(File f){
		
		POIFSFileSystem fileSystem=null;
		
		try {
			fileSystem=new POIFSFileSystem(new FileInputStream(f));
			workBook=new HSSFWorkbook(fileSystem);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

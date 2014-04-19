package com.theranos.test.theranosios.drivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

public class ExcelDriver {
	
	public static HSSFWorkbook workbook;
	public static HSSFSheet sheet;
	public static ArrayList rowvalues = new ArrayList();
	public static FileInputStream file;
	
	public ExcelDriver(String worksheetname, String sheetname) throws IOException
	{
		System.out.println("worksheet name : "+worksheetname+"\n  Sheet Name   : "+sheetname);
		file = new FileInputStream(new File(getTestSuiteLocation(),worksheetname));
				
		workbook = new HSSFWorkbook(file);   	//Worksheet object to access the worksheet	
		sheet = workbook.getSheet(sheetname);     //Sheet Object to access the sheet
	}
	
	public static String getCurrentLocation() 
	{
		return(System.getProperty("user.dir"));
	}
	
	public static void closeXLFile() throws IOException
	{
		file.close();
	}
	
	public static String getTestSuiteLocation() 
	{
		return(new File(getCurrentLocation(),"src/test/resources").toString());
	}
	
	 public ArrayList getExecutableRowValues(int rownumber, ArrayList<String> executabletestid) throws IOException  
	  {	    
		    ArrayList<String> rowvalues = new ArrayList<String>();
					
			if(isExecutableRow(rownumber, "Executable"))
			{
				rowvalues = getRowValues(rownumber);
				if (!Collections.disjoint(rowvalues, executabletestid))
				{
				  return rowvalues;
				}
			}
			return rowvalues;
	  }
	
	public static ArrayList getExecutableTestID(String testidcolumn, String isexecutablecolumn) throws IOException
	{
		Row row = null;
		Cell cell;
		ArrayList suiteid = new ArrayList();
		String suiteidvalue, executable;
		
		for (int iter = findCell(testidcolumn).getRowIndex() + 1;iter <= sheet.getLastRowNum();iter++)
		{
			row = sheet.getRow(iter);		
			
			//Get the value of executable column id
			cell = row.getCell(findCell(isexecutablecolumn).getColumnIndex());
			if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				executable = "None";
			else
			{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				executable = cell.getStringCellValue();
			}			
			if (executable.equalsIgnoreCase("yes"))
			{
				//get the value of the TestSuite Id
				cell = row.getCell(findCell(testidcolumn).getColumnIndex());
				if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
					executable = "None";
				else
				{
					cell.setCellType(Cell.CELL_TYPE_STRING);
					suiteid.add(cell.getStringCellValue());
				}
			}			
		}
		closeXLFile();
		return suiteid;		
	}
	
	public static Cell findCell(String text)
	{
		try
		{
		    for(Row row : sheet) 
		    {   
		        for(Cell cell : row) 
		        { 
		          cell.setCellType(Cell.CELL_TYPE_STRING);	         
	        	  if (text.equalsIgnoreCase(cell.getStringCellValue()))
	            	 {
	            		 return cell; 
	            	 }
	            }
		    }
		}
		catch(NullPointerException nl){
			System.out.println("Not able to get the data from excel, data return value will be null now");
			return null;
			}	    
	    return null;
	}
	
	public boolean isExecutableRow(int rownumber, String isexecutablecolumn)
	{
		Row row = sheet.getRow(rownumber);		
		String executable;
		//Get the value of executable column id
		Cell cell = row.getCell(findCell(isexecutablecolumn).getColumnIndex());
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
			return false;
		cell.setCellType(Cell.CELL_TYPE_STRING);
		executable = cell.getStringCellValue();					
		if (executable.equalsIgnoreCase("yes"))
			return true;
		return false;
		
	}
	
	public int getTotalRows()
	{
		return sheet.getLastRowNum();
	}
	
	public int getExcelRowIndex(String columnname)
	{
		return findCell(columnname).getRowIndex();
	}
	
	public int getExcelColumnIndex(String columnname)
	{
		return findCell(columnname).getColumnIndex();
	}
	
	public ArrayList<String> getRowValues(int rownumber)
	{
		Row row = sheet.getRow(rownumber);
		ArrayList rowvalues = new ArrayList();
		
		for (Cell cell : row)
		{
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			{
				if (DateUtil.isCellDateFormatted(cell)) 
				{
					// Get the date today using cell.getDateCellValue() 
			        Date datevalue = cell.getDateCellValue();				
				    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    
			       // Using DateFormat format method we can create a string 
			       // representation of a date with the defined format.
			       String reportdate = df.format(datevalue);
				   rowvalues.add(reportdate.toString());
			   }
			else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				rowvalues.add(cell.getStringCellValue().toString());
				}
			}
			else if (cell.getCellType() != Cell.CELL_TYPE_BLANK)
			{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				rowvalues.add(cell.getStringCellValue().toString());
			}
			else
				rowvalues.add("null");
		}
		return rowvalues;
	}

}





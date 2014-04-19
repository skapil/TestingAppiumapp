package com.theranos.test.theranosios.base;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.theranos.test.theranosios.drivers.ExcelDriver;


public class TestCaseReader {

	private static ExcelDriver testUtil;
	private static ArrayList testidvalues;
	
	public TestCaseReader(String suiteidvalue) throws IOException
	{
		testUtil = new ExcelDriver("testcases.xls",suiteidvalue);
	}
	
	public static ArrayList getExcutableTestID() throws IOException
	{
		testidvalues = ExcelDriver.getExecutableTestID("Test Case ID","Executable");
		for (int testitem = 0;testitem < testidvalues.size();testitem++)
		{
			testidvalues.get(testitem);
		}
		return testidvalues;
	}
	
 }



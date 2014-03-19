package com.theranos.test.theranosios.base;

//Local Test package
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.theranos.test.theranosios.drivers.ExcelDriver;

public class TestSuiteReader {
	
	private static ExcelDriver suiteUtil;
	TestRunner testRun;
	ArrayList testid = new ArrayList();
	private static Map<String,ArrayList<String>> allsuiteids = new HashMap();
	
	public static ArrayList functionalTestingSuite() throws IOException
	{
		suiteUtil = new ExcelDriver("theranosiostestsuite.xls","FunctionalTesting");
		ArrayList suiteid = ExcelDriver.getExecutableTestID("Suite ID","Executable");
		return suiteid;
	}
	
	public ArrayList<String> getSuiteTestId(Map<String,ArrayList<String>> allids,String testsuiteid)
	{
		ArrayList<String> values = new ArrayList<String>();
		allsuiteids = allids;
		
		System.out.println("Finding the executable test cases id for suite "+testsuiteid);
		
		for (Map.Entry<String, ArrayList<String>> entry : allids.entrySet())
		{
			String key = entry.getKey();
			values = entry.getValue();
			if (key.equalsIgnoreCase(testsuiteid))
				break;
		}
		System.out.println("Search completed for this suites test ids");
		return values;
	}

}

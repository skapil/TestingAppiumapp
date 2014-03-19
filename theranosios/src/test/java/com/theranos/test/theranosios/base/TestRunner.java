package com.theranos.test.theranosios.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class TestRunner {

	SetUpTest setUp = new SetUpTest();
	TestSuiteReader suiteReader = new TestSuiteReader();
	ArrayList suiteId = new ArrayList();
	ArrayList testId = new ArrayList();
	private static Map<String,ArrayList<String>> suite_test_id = new HashMap<String, ArrayList<String>>();
	TestCaseReader testReader;
	public static WebDriver driver;

	//Get Suite Id's
	@BeforeSuite
	public void readTestSuite() throws IOException
	{
		System.out.println("Getting the TestSuites ID to run");
		suiteId = TestSuiteReader.functionalTestingSuite();		
	}
	
	//Get Test Cases
	@BeforeSuite (dependsOnMethods = {"readTestSuite"})
	public void readAllTestCases() throws IOException
	{
		System.out.println("Getting the TestCase ids for Runnable Test Suite");
		for (int suiteitem = 0;suiteitem < suiteId.size();suiteitem++)
		{
			testReader = new TestCaseReader(suiteId.get(suiteitem).toString());
			testId = TestCaseReader.getExcutableTestID();
			    
			if (testId.size() <= 0)
			{
				System.out.println("No eligible test case found to run for "+suiteId.get(suiteitem).toString());
				continue;
			}			
				suite_test_id.put(suiteId.get(suiteitem).toString(), testId);
		}
		System.out.println("Got all the test cases from all the test suites");		
		for (Map.Entry<String, ArrayList<String>> entry : suite_test_id.entrySet())
		{
			ArrayList<String> values = new ArrayList<String>();
			String key = entry.getKey();
			values = entry.getValue();				
		}
	}
	
	@DataProvider(name = "testdata")
	public static Object[][] getTestId()
	{
		return new Object[][] { { suite_test_id } };	
	} 

}

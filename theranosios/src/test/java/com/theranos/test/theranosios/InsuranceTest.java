package com.theranos.test.theranosios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.theranos.test.theranosios.base.PerformOperationsTest;
import com.theranos.test.theranosios.base.SetUpTest;
import com.theranos.test.theranosios.base.TestDataID;
import com.theranos.test.theranosios.base.TestSuiteReader;
import com.theranos.test.theranosios.drivers.ExcelDriver;


public class InsuranceTest{
	
  private WebDriver driver;
  private static ExcelDriver xlUtil;
  HashMap<String,String> elementid = new HashMap<String,String>();
  HashMap<String,String> monthid = new HashMap<String,String>();
  TestDataID elements;
  private String os;
  
  //All the elementid of the box
  
  //This is only for testing  
  private static ArrayList<String> executabletestid= new ArrayList<String>(); 
  PerformOperationsTest operation = new PerformOperationsTest();
  
  public InsuranceTest()  {  }
  
  @Parameters("device")
  @BeforeClass(alwaysRun = true)
  public void getOS(String os)
  {
	  this.os = os;
	  elements = new TestDataID(os);
	  this.elementid = elements.basicInfoData(os);
	  this.monthid = elements.monthData();
  }

  @BeforeClass
  public void setEnv() throws Exception
  {
	   //Setting up the environment
  	   driver = SetUpTest.driver;
  	   System.out.println("\n\n All the setup has completed, Now starting \n"
  	   		+ "-------------------------INSURANCE TEST----------------------\n\n");
  }
  
  @Test (dataProviderClass = com.theranos.test.theranosios.base.TestRunner.class, dataProvider = "testdata")
  public void getTestData(Map<String,ArrayList<String>> suite_test_id) throws Exception
  {
	   System.out.println("Got all the test suite ids by test method");
  	   TestSuiteReader tsr = new TestSuiteReader();
  	   executabletestid = tsr.getSuiteTestId(suite_test_id,"TS3");
	   xlUtil = new ExcelDriver("testcasedata.xls","TestData-TS3");
		  for (int iter = 0; iter < executabletestid.size(); iter++)
			  System.out.println("values of test ids which will be run with this test suite :   "+executabletestid.get(iter));
  }
  
  //Open the Basic Info page
  @Test
  public void openInsurancePage() 
  {
	  operation.openPage("//window[1]/tableview[2]/cell[2]/text[1]", "Profile", driver);	  
  }

  
  @AfterClass
  public void afterTest()
  {
	  System.out.println("Getting back to profile page");
	  operation.backToProfile(driver);
	  System.out.println("Waiting for 5 seconds to return back to Profile page");
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  System.out.println("\n----------------------Insurance test has completed-----------------------\n\n");
  }
}

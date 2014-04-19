package com.theranos.test.theranosios;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.theranos.test.theranosios.base.PerformOperationsTest;
import com.theranos.test.theranosios.base.SetUpTest;
import com.theranos.test.theranosios.base.TestSuiteReader;
import com.theranos.test.theranosios.drivers.ExcelDriver;

public class PassCodeTest {	

	private WebDriver driver;
	private static ExcelDriver xlUtil;
	private boolean flag = false;
	PerformOperationsTest operation = new PerformOperationsTest();
	
	 //These data structure use to store testsuites and test ids 
	 private static ArrayList<String> executabletestid= new ArrayList<String>();    //All the executable test id store on this
  
	//Setup the environment for the test
	@BeforeClass
	public void setEnv() throws Exception
	{
	  //Setting up the environment
	   driver = SetUpTest.driver;
	   System.out.println("All the setup has completed for Passcode Test, Starting "
	   		+ "\n ----------------------PASSCODE PAGE TEST-----------------------------\n");
	 }
	
	  @Test (dataProviderClass = com.theranos.test.theranosios.base.TestRunner.class, dataProvider = "testdata")
	   public void getTestData(Map<String,ArrayList<String>> suite_test_id) throws Exception
	   {
		   System.out.println("Got all the test suite ids by test method");	
	   	   TestSuiteReader tsr = new TestSuiteReader();
	   	   executabletestid = tsr.getSuiteTestId(suite_test_id,"TS2");
		   xlUtil = new ExcelDriver("testcasedata.xls","TestData-TS2");
			  for (int iter = 0; iter < executabletestid.size(); iter++)
				  System.out.println("value of test ids which will be run with this test suite :   "+executabletestid.get(iter));
	   }
		 
	 // Verify if the Passcode screen display on app
	 public boolean verifyPasscodeScreen()
	 {
		 System.out.println("Looking for Passcode screen on application");
		 WebElement passcodevalue = driver.findElement(By.xpath("//window[1]/text[1]"));		 
		 
		  if (passcodevalue.getText().equalsIgnoreCase("Enter your new passcode") ||  (passcodevalue.getText().equalsIgnoreCase("Re-enter your new passcode")) ||
				  (passcodevalue.getText().contains("Your session timed out")))
			  return true;
		  else
			  return false;		 
	 }	 
	 
	 //Return the passcode from the xl sheet
	 public String getPasscode(int rownumber, String columnname) throws IOException
	 {
		 String newpasscode = null;
		 ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber, executabletestid);
		 
		 if (rowvalues.size() > 0)
		 {
			 String passcodevalue = rowvalues.get(xlUtil.getExcelColumnIndex(columnname)).toString();
			 if (passcodevalue.isEmpty())
			 	return "null";
					    
			for (int iter = 0; iter < passcodevalue.length(); iter++)
			{
				String value = Character.toString(passcodevalue.charAt(iter));				
			
				if (value.equals("-"))
					continue;
				
				if (iter == 0) 
					newpasscode = value;
				else
					newpasscode += value;	
		      }
		 }
		 return newpasscode;
	 }

	 //Provide passcode on Passcode Screen
	  @Test
	  public void providePasscodeTest(String passcodetimes) throws IOException{
		  String buttonlocation;
		  int count = 0;
		  
		  System.out.println("Waiting for 5 seconds before starting the test...");
		  driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		  for (int iter = xlUtil.getExcelRowIndex("Executable") + 1; iter < xlUtil.getTotalRows(); iter++)
			 {	  
				 String passcode = null;
				 passcode = getPasscode(iter,"Passcode"); 
				 System.out.println("Got this Passcode : "+passcode);
							 
				 //if passcode field in excel sheet is empty
				  if (passcode.equalsIgnoreCase("null"))
				  {
					  System.out.println("No Passcode found, trying for the next row ");
					  continue;	
				  }					  	          
				  
				  count = 0;
				  System.out.println("Providing this value as passcode : "+passcode);
				  while(count < passcode.length())
				  {
					  String value = Character.toString(passcode.charAt(count));
					  if (value.equalsIgnoreCase("0"))
					      buttonlocation = "10";
					  else
						  buttonlocation = value;
					  driver.findElement(By.xpath("//window[1]/button["+buttonlocation+"]")).click();
					  driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
					  count++;
				  }
				  
				  String confirmpasscode = getPasscode(iter,"ConfirmPasscode");
				  count = 0;
				  
				  //if Confirmpasscode field in excel sheet is empty			
				  if (confirmpasscode.equalsIgnoreCase("null"))
				  {
					  System.out.println("No Passcode found in this row ");
					  continue;	
				  }
				  
				  System.out.println("Providing this value as confirm passcode : "+confirmpasscode);
		    	  while(count < confirmpasscode.length())
				  {
					  String value = Character.toString(confirmpasscode.charAt(count));
					  if (value.equalsIgnoreCase("0"))
					      buttonlocation = "10";
					  else
						  buttonlocation = value;
					  driver.findElement(By.xpath("//window[1]/button["+buttonlocation+"]")).click();
					  driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
					  count++;
				  }
		    	  flag = operation.dimissAllAlert(driver);
			  
		     }
		     if (!verifyPasscodeScreen())
				  System.out.println("Passcode screen not found");
	  }
	  
	  //Reset the passcode test on passcode screen
	  @Test
	  public void resetPasscodeTest()
	  {
		  boolean screen = verifyPasscodeScreen();
		  if (screen)
		  {
			  System.out.println("Clicking on reset button on Reset Passcode screen to reset the passcode");
			  driver.findElement(By.xpath("//window[1]/button[11]")).click();
		  }
		  else
			  System.out.println("Passcode screen not found");
	  }
	  
	  //Clear the passcode in Passcode screen
	  @Test
	  public void clearPasscodeTest()
	  {
		  boolean screen = verifyPasscodeScreen();
		  if (screen)
		  {
			  System.out.println("Clicking on clear button on Reset Passcode screen to clear the passcode");
			  driver.findElement(By.xpath("//window[1]/button[12]")).click();
		  }
		  else
			  System.out.println("Passcode screen not found");
	  }
	  
	  @AfterClass
	  public void afterTest()
	  {
		  System.out.println("\n----------------------Passcode test has completed-----------------------\n\n");
	  }
}


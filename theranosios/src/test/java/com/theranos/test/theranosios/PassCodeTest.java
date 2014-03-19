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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.theranos.test.theranosios.base.SetUpTest;
import com.theranos.test.theranosios.base.TestSuiteReader;
import com.theranos.test.theranosios.drivers.ExcelDriver;

public class PassCodeTest {	

	private WebDriver driver;
	private static ExcelDriver xlUtil;
	
	 //These data structure use to store testsuites and test ids 
	 private static ArrayList<String> executabletestid= new ArrayList<String>();    //All the executable test id store on this
  
	//Setup the environment for the test
	@BeforeClass
	public void setEnv() throws Exception
	{
	  //Setting up the environment
	   driver = SetUpTest.driver;
	   System.out.println("All the setup has completed, Now starting -----PASSCODE PAGE TEST-----\n");
	 }
	
	  @Test (dataProviderClass = com.theranos.test.theranosios.base.TestRunner.class, dataProvider = "testdata")
	   public void getTestData(Map<String,ArrayList<String>> suite_test_id) throws Exception
	   {
		   System.out.println("Got all the test suite ids by test method");	
	   	   TestSuiteReader tsr = new TestSuiteReader();
	   	   executabletestid = tsr.getSuiteTestId(suite_test_id,"TS2");
		   xlUtil = new ExcelDriver("testcasedata.xls","TestData-TS2");
			  for (int iter = 0; iter < executabletestid.size(); iter++)
				  System.out.println("values of test ids which will be run with this test suite :   "+executabletestid.get(iter));
	   }
		 
	 // Verify if the Passcode screen display on app
	 public boolean verifyPasscodeScreen()
	 {
		 WebElement passcodevalue = driver.findElement(By.xpath("//window[1]/text[1]"));		 
		 
		  if (passcodevalue.getText().equalsIgnoreCase("Enter your new passcode") ||  (passcodevalue.getText().equalsIgnoreCase("Re-enter your new passcode")) ||
				  (passcodevalue.getText().contains("Your session timed out")))
			  return true;
		  else
			  return false;		 
	 }	 
	 
	 //Return the passcode from the xl sheet
	 public int[] getPasscode(int rownumber, String columnname) throws IOException
	 {
		 int[] passcodevalues = new int[5];
		 int count = 3;
		 ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber, executabletestid);
		 
		 if (rowvalues.size() > 0)
		 {
			 String passcodevalue = rowvalues.get(xlUtil.getColumnIndex(columnname)).toString();
			 if (passcodevalue.isEmpty())
			 {
				passcodevalues[4] = 9;
			 	return passcodevalues;
			 }
			 int passcodeintvalue = Integer.parseInt(passcodevalue);
			 
			 //Putting all digits in different index of an array			 
			 while (count >= 0)
			 {
				 passcodevalues[count] = (passcodeintvalue % 10);	
				 passcodeintvalue /= 10;
				 count--;
			 }
		 }
		 return passcodevalues;
	 }

	 //Provide passcode on Passcode Screen
	  @Test
	  @Parameters ("passcodetimes")
	  public void providePasscodeTest(String passcodetimes) throws IOException{
		  int[] passcodevalues = new int[4];
		  int[] confirmpasscode = new int[4];
		  int count = 0;
		  int buttonlocation;
		  
		  for (int iter = xlUtil.getRowIndex("Executable") + 1; iter < xlUtil.getTotalRows(); iter++)
			 {	  
				 ArrayList<String> rowvalues = xlUtil.getRowValues(iter);
				 
				  if (rowvalues.size() <= 0)
				  {			 
					  continue;
				  }		  
		          passcodevalues = getPasscode(iter,"Passcode"); 
		          System.out.println("Got this passcode : "+passcodevalues);
		          //if passcode field in excel sheet is empty
		          if (passcodevalues[4] == 9)
		        	  continue;
				  System.out.println("Providing the passcode");
				  count = 0;
				  while(count < 4)
				  {
					  if (passcodevalues[count] == 0)
					      buttonlocation = 11;
					  else
						  buttonlocation = passcodevalues[count]; 
					  driver.findElement(By.xpath("//window[1]/button["+buttonlocation+"]")).click();
					  driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
					  count++;
				  }
				  
				  confirmpasscode = getPasscode(iter,"ConfirmPasscode");
				  count = 0;
				  //if passcode field in excel sheet is empty
		          if (passcodevalues[4] == 9)
		        	  continue;
		          System.out.println("Providing the confirm passcode");
				  while(count < 4)
				  {
					  if (confirmpasscode[count] == 0)
					      buttonlocation = 11;
					  else
						  buttonlocation = passcodevalues[count]; 
					  driver.findElement(By.xpath("//window[1]/button["+buttonlocation+"]")).click();
					  driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS);
					  count++;
				  }
			  
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
	  
	  @AfterTest
	  public void tearDown() throws Exception {
		System.out.println("Test has been completed");
	    driver.quit();
	  }
}

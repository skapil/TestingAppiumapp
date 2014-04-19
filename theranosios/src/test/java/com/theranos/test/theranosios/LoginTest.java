package com.theranos.test.theranosios;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.theranos.test.theranosios.base.*;

import java.io.IOException;

//Testing purpose
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.theranos.test.theranosios.drivers.ExcelDriver;

public class LoginTest 
{	
 
  private static ExcelDriver xlUtil;
  private boolean flag = false;
  private WebDriver driver;
  PerformOperationsTest operation = new PerformOperationsTest();

  //This is only for testing  
   private static ArrayList<String> executabletestid= new ArrayList<String>(); 

   
   @BeforeClass
   public void setEnv() throws Exception
   {
	   //Setting up the environment
   	   driver = SetUpTest.driver;
   	   System.out.println("\n\n All the setup has completed, Now starting \n"
   	   		+ "-------------------------LOGIN TEST-----------------------\n\n");
   }
   

   @Test (dataProviderClass = com.theranos.test.theranosios.base.TestRunner.class, dataProvider = "testdata")
   public void getTestData(Map<String,ArrayList<String>> suite_test_id) throws Exception
   {
	   System.out.println("Got all the test suite ids by test method");
   	   TestSuiteReader tsr = new TestSuiteReader();
   	   executabletestid = tsr.getSuiteTestId(suite_test_id,"TS1");
	   xlUtil = new ExcelDriver("testcasedata.xls","TestData-TS1");
		  for (int iter = 0; iter < executabletestid.size(); iter++)
			  System.out.println("values of test ids which will be run with this test suite :   "+executabletestid.get(iter));
   }
   
  @Test 
  public void logInOption() throws Exception 
  {	 
	try
	  {
	    System.out.println("Login into the application");
	    flag = false;
	    flag = operation.dimissAllAlert(driver);
	  try {
	    System.out.println("Tapping on ios login button");
	  driver.findElement(By.xpath("//*[contains(@text,'log in')]")).click();
	  flag = false;
	  System.out.println("Verifying in case there is any pop up in login screen");
	  flag = operation.dimissAllAlert(driver);	  
	  }
	  catch(Exception e) {
	    System.out.println("Not able to tap on the Log in Button");
	  }
	  
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  }					
	  catch(Exception e)
	  {			  
	    System.out.println("Login button not found in Application Main Screen");
	    System.out.println("Expected Exception raised here \n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  }
  }
  
  public String getUserName(int rownumber) throws IOException
  {
	  ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber, executabletestid);
	  if (rowvalues.size() > 0)
		  return rowvalues.get(xlUtil.getExcelColumnIndex("Input-Email")).toString();
	  return "none";
  }
  
  public String getPassword(int rownumber) throws IOException 
  {
	  ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber,executabletestid);
	  if (rowvalues.size() > 0)
		  return rowvalues.get(xlUtil.getExcelColumnIndex("Input-Password")).toString();
	  return "none";
  }
  
  @Test (dependsOnMethods = {"logInOption","getTestData"})
  public void testLoginpage() throws Exception
  {	
	 WebElement elementid = null;
	 for (int iter = xlUtil.getExcelRowIndex("Executable") + 1; iter < xlUtil.getTotalRows(); iter++)
	 {	  
		 ArrayList<String> rowvalues = xlUtil.getRowValues(iter);		 
		 
		  if (rowvalues.size() <= 0)
		  {			 
			  continue;
		  }	
		  if (!getUserName(iter).equalsIgnoreCase("none"))
		  {
			  System.out.println("Providing the Email address  : "+getUserName(iter));
			  //WebElement username = driver.findElement(By.xpath("//window[1]/textfield[1]"));
			  try
			  {
				elementid = driver.findElement(By.xpath("//*[contains(@type,'textfield')]"));
			  }
			  catch(Exception e){
				elementid = driver.findElement(By.id("com.theranos.me:id/login_email_edit"));
			  }
			  elementid.clear();
		  
			  elementid.sendKeys(getUserName(iter));
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  }

		  if (!getPassword(iter).equalsIgnoreCase("none"))
		  {
			  System.out.println("Providing the Password  : "+getPassword(iter));
		      //WebElement password = driver.findElement(By.xpath("//window[1]/secure[1]"));
			  try
			  {
				elementid = driver.findElement(By.xpath("//*[contains(@type,'secure')]"));
			  }
			  catch(Exception e)
			  {
		        elementid = driver.findElement(By.id("com.theranos.me:id/login_password_edit"));
			  }
			  
		      elementid.sendKeys(getPassword(iter));
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  }
		  
		  if (!getUserName(iter).equalsIgnoreCase("none") && !getPassword(iter).equalsIgnoreCase("none"))
			  try{		  
			   driver.findElement(By.name("Done")).click();
			  }
		  	  catch(Exception e){
		  		driver.findElement(By.id("com.theranos.me:id/login_btn")).click();
			   }
		  else
			  continue;
		  
		  Thread.sleep(2000);
		  flag = false;
		
		flag = operation.dimissAllAlert(driver);		
	    		
		if (flag == false)
		{
			System.out.println("Looking for the login button");
			try
			{
				if ((driver.findElement(By.name("Log in")).isDisplayed()))
				  {
				      System.out.println("Login into account");
				      try{
				      driver.findElement(By.name("Log in")).click();
				      }
				      catch(Exception e){
				    	  driver.findElement(By.id("com.theranos.me:id/login_btn")).click();
				      }
				      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				      break;
				  }
			 }
			catch(Exception NoSuchElementException){
			    System.out.println("No Login Button Found");
				flag = true;
			   }
		   }		
		else
			System.out.println("All alert message has been dismissed");
      }
  } 
 
  
  @AfterClass
  public void afterTest() 
  {
	  System.out.println("\n-------------------Login Theranos Application test has completed-----------------------\n\n");
  }

}

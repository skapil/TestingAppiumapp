package com.theranos.test.theranosios;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
  private boolean flag;
  private WebDriver driver;

  //This is only for testing  
   private static ArrayList<String> executabletestid= new ArrayList<String>(); 

   
   @BeforeClass
   public void setEnv() throws Exception
   {
	   //Setting up the environment
   	   driver = SetUpTest.driver;
   	   System.out.println("All the setup has completed, Now starting -----LOGIN TEST-----");
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
	  System.out.println("Login into the application");
	  driver.findElement(By.xpath("//window[1]/button[2]")).click();
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }
  
  
  public String getUserName(int rownumber) throws IOException
  {
	  ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber, executabletestid);
	  if (rowvalues.size() > 0)
		  return rowvalues.get(xlUtil.getColumnIndex("Input-Email")).toString();
	  return "none";
  }
  
  public String getPassword(int rownumber) throws IOException 
  {
	  ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber,executabletestid);
	  if (rowvalues.size() > 0)
		  return rowvalues.get(xlUtil.getColumnIndex("Input-Password")).toString();
	  return "none";
  }
  
  @Test (dependsOnMethods = {"logInOption","getTestData"})
  public void testLoginpage() throws Exception
  {	
	 for (int iter = xlUtil.getRowIndex("Executable") + 1; iter < xlUtil.getTotalRows(); iter++)
	 {	  
		 ArrayList<String> rowvalues = xlUtil.getRowValues(iter);
		 
		  if (rowvalues.size() <= 0)
		  {			 
			  continue;
		  }		  
		  System.out.println("Providing the Email address  : "+getUserName(iter));
		  WebElement username = driver.findElement(By.xpath("//window[1]/textfield[1]"));
		  username.clear();
		  username.sendKeys(getUserName(iter));
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		  System.out.println("Providing the Password  : "+getPassword(iter));
	      WebElement password = driver.findElement(By.xpath("//window[1]/secure[1]"));
	      password.sendKeys(getPassword(iter));
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  
		  driver.findElement(By.name("Done")).click();
		  
		  Thread.sleep(2000);
		  flag = false;
		
		while (flag == false)
		{ 			
			try
			{
				System.out.print("pop-up message apears on device :  ");
				System.out.println(driver.findElement(By.name("OK")).isDisplayed());
				if(driver.findElement(By.name("OK")).isDisplayed())
				{
					System.out.println("Error Pop up displayed");
					driver.switchTo().alert().accept();
					Thread.sleep(2000);
					flag = false;
				}
			}
			catch(Exception NoSuchElementException){
				    System.out.println("No Alert Found");
					flag = true;
			}
		}	
	    
		System.out.println("Looking for the login button");
		if ((driver.findElement(By.name("Login")).isDisplayed()) && (flag == false ))
		  {
		      System.out.println("Login into account");
		      driver.findElement(By.name("Login")).click();
		      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		      break;
		  }	
      }
  } 
  
  @AfterClass
  public void afterTest() 
  {
	  System.out.println("------Login Theranos Application--- test has completed");
  }

}

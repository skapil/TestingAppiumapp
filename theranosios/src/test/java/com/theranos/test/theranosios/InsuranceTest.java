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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
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
  	   executabletestid = tsr.getSuiteTestId(suite_test_id,"TS4");
	   xlUtil = new ExcelDriver("testcasedata.xls","TestData-TS4");
		  for (int iter = 0; iter < executabletestid.size(); iter++)
			  System.out.println("values of test ids which will be run with this test suite :   "+executabletestid.get(iter));
  }
  
  //Open the Basic Info page
  @BeforeMethod
  public void openInsurancePage() 
  {
	  if (os.equalsIgnoreCase("ios"))
		  operation.openPage("Insurance", "Insurance", driver);	  
  }

  @Test
  public void addNewInsurance()
  {
	  operation.openPage("Add Insurance", "Add Insurance", driver); 
  }
  
  @Test(dependsOnMethods = "addNewInsurance")
  public void addInsuranceInfo()
  {
	  operation.openPage("Enter Insurance info", "Enter Insurance Info", driver);
	  
  }
  /*
  @Test(dependsOnMethods = "enterInsuranceInfo")
  public void takeInsuranceCardPhoto()
  {
	  operation.openPage("Take Insurance Card Photo", "Add New Insurance using Photo Id", driver);
  }
  
  @Test(dependsOnMethods = "enterInsuranceInfo")
  public void cancelAddInsurance()
  {
	  operation.openPage("Cancel", "Cancel Insurance Information", driver);
  }
  */
  @Test(dependsOnMethods = "addInsuranceInfo")
  public void enterInsuranceInfo()
  {
	  boolean flag = false;
	  System.out.println("Providing Insurance info of user :");
	  System.out.println("Value of the total records in excel sheet  ");
	  System.out.println(xlUtil.getTotalRows());
	  for (int iter = xlUtil.getExcelRowIndex("Executable") + 1; iter < xlUtil.getTotalRows(); iter++)
		 {	
		     if(!xlUtil.isExecutableRow(iter, "Executable"))
			  continue;
		     
		     System.out.println("Opening Basic info page");
		     addInsuranceInfo();
			 ArrayList<String> rowvalues = xlUtil.getRowValues(iter);
			 
			 System.out.println("========================Getting data to provide to application===============================");
			 for(int value = 0; value < rowvalues.size(); value++)
				 System.out.println(rowvalues.get(value));
			 System.out.println("=========================Got all the test data to process=================================");
			 
			  if (rowvalues.size() <= 0)	
				  continue;
			  
			  System.out.println("Providing insurance Information------------------------------------");
			  if(os.equalsIgnoreCase("ios"))
				  operation.inputValue("//window[1]/tableview[2]/cell[1]/textfield[1]", "Insurance Provider",elementid.get("InsuranceProvider"), driver);
			  
			  System.out.println("Providing First Name of Subscriber -------------------------------------------");
			  if(os.equalsIgnoreCase("ios"))
				  operation.inputValue("//window[1]/tableview[2]/cell[2]/textfield[1]", "First Name",elementid.get("FirstName"), driver);
			 
			  System.out.println("Providing Last Name of Subscriber -------------------------------------------");
			  if(os.equalsIgnoreCase("ios"))
				  operation.inputValue("//window[1]/tableview[2]/cell[3]/textfield[1]", "First Name",elementid.get("LastName"), driver);
			  
			  System.out.println("Providing Relationship of Subscriber -------------------------------------------");
			  operation.openPage("Relationship", "Relationship", driver);
			  if(os.equalsIgnoreCase("ios"))				  
				  operation.inputValue("//window[2]/picker[1]/pickerwheel[1]", "Relationship", elementid.get("Relationship"), driver);
			  
			  System.out.println("Providing Plan Type of Subscriber -------------------------------------------");
			  operation.openPage("Plan Type", "Plan Type", driver);
			  if(os.equalsIgnoreCase("ios"))				  
				  operation.inputValue("//window[2]/picker[1]/pickerwheel[1]", "Plan Type", elementid.get("PlanType"), driver);
			  
			  System.out.println("Providing Policy Id of Subscriber -------------------------------------------");
			  if(os.equalsIgnoreCase("ios"))
				  operation.inputValue("//window[2]/picker[1]/pickerwheel[1]", "Policy ID", elementid.get("Policy"), driver);
			  
			  System.out.println("Providing Group id of Subscriber -------------------------------------------");
			  if(os.equalsIgnoreCase("ios"))
				  operation.inputValue("//window[2]/picker[1]/pickerwheel[1]", "Group ID", elementid.get("Group"), driver);
			  
			  System.out.println("Saving information----------------------");
			  operation.openPage("Save", "Save", driver);
			  
			  if(operation.isAlertAppear(driver)){
				  System.out.println("Not able to save insurance information");
				  flag = true;
				  flag = operation.dimissAllAlert(driver);
				  System.out.println("Going back to previouse page");
				  operation.openPage("back button icon", "Back", driver);
			  }
			  
			  
		 }
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

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


public class BasicInfoTest{
	
  private WebDriver driver;
  private static ExcelDriver xlUtil;
  HashMap<String,String> elementid = new HashMap<String,String>();
  HashMap<String,String> monthid = new HashMap<String,String>();
  HashMap<String,String> stateid = new HashMap<String,String>();
  TestDataID elements;
  private String os;
  
  //All the elementid of the box
  
  //This is only for testing  
  private static ArrayList<String> executabletestid= new ArrayList<String>(); 
  PerformOperationsTest operation = new PerformOperationsTest();
  
  public BasicInfoTest() {  }   //Constructor of the class

  @BeforeClass
  public void setEnv() throws Exception
  {
	   //Setting up the environment
  	   driver = SetUpTest.driver;
  	   System.out.println("\n\n All the setup has completed, Now starting \n"
  	   		+ "-------------------------BASIC INFO TEST----------------------\n\n");
  }
  
  @Parameters("device")
  @BeforeClass
  public void getOs(String os)
  {
	 this.os = os;
	 elements = new TestDataID(os);
	 this.elementid = elements.basicInfoData(os);
     this.monthid = elements.monthData();
     this.stateid = elements.stateData();
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
  public void openBasicInfo() {
	  System.out.println("Opening basic info page------");
	  operation.openPage("Basic Info", "Profile", driver);	  
  }
  
  public void backScreen(){
	  
  }
  @Test(dependsOnMethods="openBasicInfo")
  public void provideBasicInfo() throws Exception
  {
	  boolean flag = false;
	  System.out.println("Providing Basic info of user :");
	  System.out.println("Value of the total records in excel sheet  ");
	  System.out.println(xlUtil.getTotalRows());
	  for (int iter = xlUtil.getExcelRowIndex("Executable") + 1; iter < xlUtil.getTotalRows(); iter++)
		 {	
		     if(!xlUtil.isExecutableRow(iter, "Executable"))
			  continue;
		     
		     System.out.println("Opening Basic info page");
		     openBasicInfo();
			 ArrayList<String> rowvalues = xlUtil.getRowValues(iter);
			 
			 System.out.println("========================Getting data to provide to application===============================");
			 for(int value = 0; value < rowvalues.size(); value++)
				 System.out.println(rowvalues.get(value));
			 System.out.println("=========================Got all the test data to process=================================");
			 
			  if (rowvalues.size() <= 0)	
				  continue;			  
			  
			  System.out.println("\n Tap on Edit option--------------");
			  editUserInfo();
			  System.out.println("Completed editing, now stating to provide data");
			  
			  //Providing the Basic Info
			  System.out.println("\n First Name of User: -----------------------");			  
			  provideInfo(iter,"FirstName",elementid.get("FirstName"),"write");
			  System.out.println("\n Middle Name of User:-----------------------");
			  provideInfo(iter,"MiddleName",elementid.get("MiddleName"),"write");
			  System.out.println("\n Last Name of User:--------------------------");
			  provideInfo(iter,"LastName",elementid.get("LastName"),"write");

			  //Providing the Other Info
			  System.out.println("\n Date of Birth of User:-------------------------");
        	  testDOB(rowvalues);
			  flag = operation.dimissAllAlert(driver);
	          
			  System.out.println("\nGender of User: ------"); 
			  genderSelection(rowvalues);
		
			  if(os.equalsIgnoreCase("ios"))
			  {
			    System.out.println("\nPhone Number of User: -----------");
			    provideInfo(iter,"Phone",elementid.get("Phone"),"write");
			  }
			  else if(os.equalsIgnoreCase("android"))
			  {				  
				System.out.println("\nMobile Number of User :----------");
				provideInfo(iter,"Mobile",elementid.get("Mobile"),"write");
				
				System.out.println("\n Home Number of User :------------");
				provideInfo(iter,"Home",elementid.get("Home"),"write");
				
				System.out.println("\n Work Number of User:------------");
				provideInfo(iter,"Work",elementid.get("Work"),"write");
			  }
			  
			  //Providing the Mailing address
			  System.out.println("\n Mailing Street Address 1 of the User: ------------------------------");
			  provideInfo(iter,"Mailing-StreetLine1",elementid.get("Mailing-StreetLine1"),"write");
			  System.out.println("\n Mailing Street Address 2 for the User: ----------------------------");
			  provideInfo(iter,"Mailing-StreetLine2",elementid.get("Mailing-StreetLine2"),"write");
			  System.out.println("\n Mailing City of the User: ---------------------------------");
			  provideInfo(iter,"Mailing-City",elementid.get("Mailing-City"),"write");
			  
			  System.out.println("\n Mailing State of the User: ---------------------------------");
			  provideStateInfo(rowvalues,"Mailing-State");
			  
			  System.out.println("\n Mailing Zip Code of the User: ------------------------------");
			  provideInfo(iter,"Mailing-Zipcode",elementid.get("Mailing-Zipcode"),"write");
	
			  System.out.println("\n Checking if user want to have the same billing address as mailing address------------------");
			  flag = checkForBillingAddress(rowvalues);
			  System.out.println("Does User need to provide the billing address   :  "+flag);
			  
			  //Providing the Billing Address
			  if (flag)
			  {
				  System.out.println("\n Billing Street Address 1 of the User: -------------------------------");			  
				  provideInfo(iter,"Billing-StreetLine1",elementid.get("Billing-StreetLine1"),"write");
				  System.out.println("\n Billing Street Address 2 of the User: ------------------------------");
				  provideInfo(iter,"Billing-StreetLine2",elementid.get("Billing-StreetLine2"),"write");
				  System.out.println("\n Billing City of the User: ----------------------------------");
				  provideInfo(iter,"Billing-City",elementid.get("Billing-City"),"write");
				  
				  System.out.println("\n Billing State of the User: ----------------------------------");
				  provideStateInfo(rowvalues, "Billing-State");
				  
				  System.out.println("\n Billing Zip Code of the User: --------------------------------");
				  provideInfo(iter,"Billing-Zipcode",elementid.get("Billing-Zipcode"),"write");
			  }
			  System.out.println("\n Saving the user provided information------------");
			  saveUserInfo();
			  if (operation.isAlertAppear(driver))
			  {
				  System.out.println("Error message appears on device, Dismissing the alert");
				  flag = operation.dimissAllAlert(driver);
				  System.out.println("Getting back to profile page");
				  operation.backToProfile(driver);
				  continue;				  
			  }				  
		 }
     }
  
  //Check the Cancel button on TopWheeler
  public void testCancel(String elementid)
  {
	  if(os.equalsIgnoreCase("ios"))
	  {
		  try   {
		  System.out.println("Verifying the Cancel button");
		  WebElement state_slider =  driver.findElement(By.xpath(elementid));
		  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		  System.out.println("Tapping on Cancel");
		  driver.findElement(By.name("Cancel")).click();
		  System.out.println("Waiting for 2 seconds before providing the data");
		  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); 
		  } catch(Exception e)
		  {
			  System.out.println("Not able to find the Cancel button in Wheeler");
		  }
	  }
  }
  
  //Tap on State
  public void provideStateInfo(ArrayList<String> rowvalues, String fieldid) throws Exception
  {
	  String result = rowvalues.get(xlUtil.getExcelColumnIndex(fieldid) + 1).toString();	
	  System.out.println("State of user :  "+result);
	  WebElement elementname = null;
	  try
	  {
		  if(!result.isEmpty() && !result.equals("null"))
		  {
			  //Calling Cancel to verify the cancel button
			  if(os.equalsIgnoreCase("ios")) {
			    driver.findElement(By.xpath(elementid.get(fieldid))).click();
			    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); 
			    testCancel("//window[2]/picker[1]/pickerwheel[1]");
			  }
			  
			  //Providing the State name			 
			  driver.findElement(By.xpath(elementid.get(fieldid))).click();			 
			  System.out.println("Providing the state name");
			  if(os.equalsIgnoreCase("ios"))
			    elementname =  driver.findElement(By.xpath("//window[2]/picker[1]/pickerwheel[1]"));
			  else if(os.equalsIgnoreCase("android"))
				System.out.println("Need to impliment");
			  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		      elementname.sendKeys(result);
		      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		      driver.findElement(By.name("Done")).click();
			}
		  else
			  System.out.println("No Date found for State, Skipping this field-------");
	   }
	  catch(Exception e)
	  {
		System.out.println("Not able to find the State element, skipping this operation");
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
	  }
  }
  
  //Tap on Edit button
  public void editUserInfo()
  {
	  try
	  {
		System.out.println("Trying first time");
		driver.findElement(By.name("Edit")).click();
	  }
	  catch(Exception e)
	  {
		try{
			System.out.println("Trying second time");
			driver.findElement(By.id("com.theranos.me:id/basic_info_edit")).click();
		   }
		catch(Exception ex) {
		  System.out.println("Not able to find the edit element, skipping this operation");
		  System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
		  }
	  }	  
  }  

  //Tap on Edit button
  public void saveUserInfo()
  {
	  try
	  {
		if(os.equalsIgnoreCase("ios"))
		  driver.findElement(By.name("Save")).click();
		else if(os.equalsIgnoreCase("android"))
		  driver.findElement(By.id("com.theranos.me:id/basic_info_save_item")).click();
	  }
	  catch(Exception e)
	  {
		System.out.println("Not able to find the Save element, skipping this operation");
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
	  }
}
  
  //Provide data into the text field
  public void writeInTextField(String element, String result)
  {
	  boolean flag = false;
	  WebElement elementname = null;
	  
	  try
	  {
		  System.out.println("Providing the data to field  : "+result);
		  //For Android device
		  if(os.equalsIgnoreCase("android"))
		  {			  
			if(!operation.verifyMenuButton(flag, element, driver))
    		  operation.flickScreen(driver);	
		  }
		  if(os.equalsIgnoreCase("ios")){
		    elementname = driver.findElement(By.xpath(element));
		  }
		  if(os.equalsIgnoreCase("android")){
			elementname = driver.findElement(By.id(element)); 
		  }
		  System.out.println("Clearing the test field");
		  System.out.println("Waiting for 3 seconds before clearing the data");
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  elementname.clear();
		  if(!result.equals("null"))
		  {	
			  System.out.println("waiting for 3 seconds");
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			  System.out.println("providing data on textfield");
			  elementname.sendKeys(result);
		  }		 
		  System.out.println("waiting for 3 seconds");
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	  }
	  catch(Exception e)
	  {
		  System.out.println("Got exception while writing the data in text field");
		  System.out.println("Ignoring this field");
		  System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
	  }
  }
  
  //Got Date Of Birth option 
  public void testDOB(ArrayList<String> rowvalues) throws Exception
  {	 
	  WebElement elementname = null;
	  String result = rowvalues.get(xlUtil.getExcelColumnIndex("DateOfBirth")).toString();	
	  System.out.println("Date of Birth of user :  "+result);
	  boolean flag = false;
	  //For Android device	
	  if (!result.isEmpty() && !result.equals("null"))
	  {
		try
		{
		if(os.equalsIgnoreCase("ios"))
		{
		  //Calling Cancel to verify the cancel button
		  driver.findElement(By.xpath(elementid.get("DateOfBirth"))).click();
		  testCancel("//window[2]/picker[1]/pickerwheel[1]");
		}
		
		//Providing the data in DOB
		String parts[] = result.split("/");
		System.out.println("Patient Date Of Birth of the Theranos user");
		System.out.println("Providing year of DOB :  "+parts[2]);
		if(os.equalsIgnoreCase("ios")) {
		  driver.findElement(By.xpath(elementid.get("DateOfBirth"))).click();
		}
		else if(os.equalsIgnoreCase("android")) {
			if(operation.verifyMenuButton(flag, elementid.get("DateOfBirth"), driver))
	    	  operation.flickScreen(driver);	
			driver.findElement(By.id(elementid.get("DateOfBirth"))).click();
		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		if(os.equalsIgnoreCase("ios"))
	      elementname =  driver.findElement(By.xpath("//window[2]/picker[1]/pickerwheel[3]"));
		else if(os.equalsIgnoreCase("android"))
		  elementname = driver.findElement(By.xpath("//window[1]/window[1]/linear[1]/window[1]/window[1]/datepicker[1]/linear[1]/linear[1]/numberpicker[3]"));
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    elementname.sendKeys(parts[2]);
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    if(os.equalsIgnoreCase("ios"))
	      elementname =  driver.findElement(By.xpath("//window[2]/picker[1]/pickerwheel[2]"));
	    else if(os.equalsIgnoreCase("android"))
	      elementname =  driver.findElement(By.xpath("//window[1]/window[1]/linear[1]/window[1]/window[1]/datepicker[1]/linear[1]/linear[1]/numberpicker[2]"));
	    System.out.println("Providing Day of DOB   :  "+parts[1]);
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    elementname.sendKeys(parts[1]);
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    if(os.equalsIgnoreCase("ios"))
	      elementname =  driver.findElement(By.xpath("//window[2]/picker[1]/pickerwheel[1]"));
	    else if(os.equalsIgnoreCase("android"))
	      elementname =  driver.findElement(By.xpath("//window[1]/window[1]/linear[1]/window[1]/window[1]/datepicker[1]/linear[1]/linear[1]/numberpicker[1]"));
	    System.out.println("Providing Month of DOB   :   "+parts[0]);
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    elementname.sendKeys(monthid.get(parts[0]));
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    System.out.println("DOB has been set");
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    System.out.println("Tappin on Done");
	    driver.findElement(By.name("Done")).click();
	    System.out.println("Waiting for 3 seconds");
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    if (operation.isAlertAppear(driver))
		  {
		      flag = operation.dimissAllAlert(driver);
		      System.out.println("DOB Alert has been dismissed and date will not be updated");
		  }				
	   }
		catch (Exception e)
		{
			System.out.println("Not able to locate the element using pickwheel and sendkey");
			System.out.println("Ignoring this field");	
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
		}		  
      }
    else
      System.out.println("No value found, skipping this Date of Birth");
  }
  
  //For Gender option
  public void genderSelection(ArrayList<String> rowvalues) throws IOException
  {
	  WebElement elementname = null;
	  String result = rowvalues.get(xlUtil.getExcelColumnIndex("Gender")).toString();
	  boolean flag = false;
	  
	  if (!result.isEmpty() && !result.equals("null"))
	  {
		  try
		  {
			  System.out.println("Waiting for 3 seconds");
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			  System.out.println("Gender of User  : "+result);
			  if(result.equalsIgnoreCase("Male"))
			  {
				if(os.equalsIgnoreCase("ios"))
				  elementname = driver.findElement(By.name("Male"));
				else if(os.equalsIgnoreCase("android"))
				{					
					if(!operation.verifyMenuButton(flag, elementid.get("Male"), driver))
					    operation.flickScreen(driver);	
					elementname = driver.findElement(By.xpath("//view[1]/window[2]/scroll[1]/linear[1]/linear[2]/radiogroup[1]/radio[1]"));
				}				  
			  }

			  if(result.equalsIgnoreCase("Female"))
			  {
			    if(os.equalsIgnoreCase("ios"))
				  elementname = driver.findElement(By.name("Female"));
				else if(os.equalsIgnoreCase("android"))
				{
				  if(!operation.verifyMenuButton(flag, elementid.get("Female"), driver))
					operation.flickScreen(driver);	
				  elementname = driver.findElement(By.xpath("//view[1]/window[2]/scroll[1]/linear[1]/linear[2]/radiogroup[1]/radio[2]"));
				}
			  }	
			  
			  System.out.println("Waiting for 3 seconds");
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			  System.out.println("Found the gender option to select");
	
			  elementname.click();
			  System.out.println("waiting for 3 seconds");
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  }
		  catch(Exception e)
		  {
			  System.out.println("Got exception while selecting gender in Gender field");
			  System.out.println("Ignoring this field");
			  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");		  
		  }
	   }
	  else
		  System.out.println("No data found");		  
  } 
  
  //perform operation on the field
  public void selectOption(String element, String result)
  {
	  boolean flag = true;
	  WebElement elementname = null;
	  try
	  {
		  System.out.println("Providing the Data  : "+result);
		  try{
		    elementname = driver.findElement(By.xpath(element));
		  }
		  catch(Exception e){
			  if(!operation.verifyMenuButton(flag, elementid.get("Male"), driver))
			    operation.flickScreen(driver);
			 elementname = driver.findElement(By.id(element)); 
		  }
		  System.out.println("Selecting perticular option");
		  System.out.println("waiting for 3 seconds");
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  elementname.click();
		  System.out.println("waiting for 3 seconds");
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	  }
	  catch(Exception e)
	  {
		  System.out.println("Got exception while writing the data in text field");
		  System.out.println("Ignoring this field");
		  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
	  }
  }    
  
  //Provide first name of the patient
  public void provideInfo(int rownumber, String column, String element, String operation) throws IOException
  {
	  ArrayList<String> rowvalues = xlUtil.getExecutableRowValues(rownumber, executabletestid);
	  String result = null;
	  
	  if (rowvalues.size() > 0)
		  result = rowvalues.get(xlUtil.getExcelColumnIndex(column)).toString();
	  else
		  result = "none";
	  
	  if (!result.equalsIgnoreCase("none") && operation.equals("write"))
	  {
		  writeInTextField(element,result);
	  }
	  //Check for Mailing as Billing address
	  else if (!result.equalsIgnoreCase("none") && operation.equals("click") && !result.equals("null"))
	  {
		  selectOption(element,result);
	  }
	  else
		  System.out.println("No data found");
    }
  
  
  public boolean checkForBillingAddress(ArrayList<String> rowvalues) throws IOException
  {
	  String result = rowvalues.get(xlUtil.getExcelColumnIndex("Same as mailing address") + 1).toString();
	  System.out.println("Option provided by user for Billing address  :   "+result);
	  boolean flag = false;
	  if (!result.isEmpty() && !result.equals("null"))
	  {
		  WebElement element = driver.findElement(By.name("Same as mailing address"));
		  //WebElement element = driver.findElement(By.xpath("//window[1]/tableview[1]/cell[17]/button[1]"));
		  try {
			  	if(os.equalsIgnoreCase("ios"))
			  		flag = driver.findElement(By.xpath(elementid.get("Billing-StreetLine1"))).isDisplayed();
			  	else if (os.equalsIgnoreCase("android"))
			  		flag = driver.findElement(By.xpath(elementid.get("//view[1]/window[2]/scroll[1]/linear[1]/linear[3]/linear[1]/textfield[1]"))).isDisplayed();
			    if(flag)
			      {
			    	  System.out.println("Same as mailing address option is not selected");
			    	  System.out.println("Checking, what option user has provided for billing address");
			    	  if (result.equalsIgnoreCase("no"))
					  {
						  return true;
					  } 
			    	  else if (result.equalsIgnoreCase("yes"))
			    	  {
			    		  driver.findElement(By.name("Same as mailing address")).click();
			    		  return false;
			    	  }
			      }
			      else
			      {
			    	  if (result.equalsIgnoreCase("no"))
					  {
			    		  driver.findElement(By.name("Same as mailing address")).click();
						  return true;
					  } 
			    	  else if (result.equalsIgnoreCase("yes"))
			    	  {	
			    		  return false;
			    	  } 
			      }
		    } catch (Exception e) {
		    	System.out.println("Got exception , not able to click on the field");  
		    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
		    	try
		    	{
		    		 if (result.equalsIgnoreCase("no"))
					  {
			    		  driver.findElement(By.name("Same as mailing address")).click();
						  return true;
					  } 
			    	  else if (result.equalsIgnoreCase("yes"))
			    	  {	
			    		  return false;
			    	  } 
		    	}
		    	catch(Exception e1) {
		    		System.out.println("Not able to select the option.. ignoring this field");
		    		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
		    		return false;
		    	}
		    }
	   }
	  else 
		  System.out.println("Not data found, Skipping this field");	  
	 return false;
  }
  
  @AfterClass
  public void afterTest()
  {
	  System.out.println("Getting back to profile page");
	  operation.backToProfile(driver);
	  System.out.println("Waiting for 5 seconds to return back to Profile page");
	  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	  System.out.println("\n----------------------Basic Info test has completed-----------------------\n\n");
  }


}

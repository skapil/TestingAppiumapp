package com.theranos.test.theranosios;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import com.theranos.test.theranosios.base.PerformOperationsTest;
import com.theranos.test.theranosios.base.SetUpTest;
import com.theranos.test.theranosios.drivers.ExcelDriver;

public class MenuTest {
	
  private WebDriver driver;
  private static ExcelDriver xlUtil;
  
  //These data structure use to store testsuites and test ids 
  private static ArrayList<String> executabletestid= new ArrayList<String>();    //All the executable test id store on this
  PerformOperationsTest operation = new PerformOperationsTest();	
 
  @BeforeClass
  public void setEnv() throws Exception
	{
	  //Setting up the environment
	   driver = SetUpTest.driver;
	   System.out.println("All the setup has completed for Menu Test, Starting "
	   		+ "\n ----------------------MENU TEST-----------------------------\n");
	 } 

  @BeforeMethod
  public void openMenu() 
  {
	  boolean flag = false;
	  System.out.println("Checking if Menu button is on screen");
	  flag = operation.dimissAllAlert(driver);
	  flag = false;
	  if (flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/navigationBar[2]/button[1]",driver);
	  if (flag == false) 
		flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[1]/view[1]/linear[1]", driver); 
	  
	  if (flag == true)
	  {
		System.out.println("Opening the Menu Screen");
		
		try {
	    driver.findElement(By.xpath("//window[1]/navigationBar[2]/button[1]")).click();
		}
		catch(Exception e){
			driver.findElement(By.xpath("//window[1]/view[1]/window[1]/view[1]/linear[1]/window[1]")).click();
		}
		
	    System.out.println("Waiting for 5 seconds to wait to open the menu screen");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	  }
	  else
	  {
	  	 System.out.println("No Menu button found on the current screen"); 
	  }		 
  }
  
  @Test
  public void openProfile()
  {
	  boolean flag = false;
	  System.out.println("Checking if Profile option is on screen");
	  if (flag == false) 
		 flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[1]/text[1]",driver);		  
	  if (flag == false) 
		 flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[2]/text[1]",driver);  
		  
	  try {		
		  if (flag == true)
		  {
		    System.out.println("Opening the profile page");
		    try {
		      driver.findElement(By.xpath("//window[1]/tableview[1]/cell[1]/text[1]")).click();
	        }
		    catch(Exception e){
			  driver.findElement(By.xpath("//window[1]/view[1]/window[2]/view[1]/list[1]/relative[2]/text[1]")).click();
		    }
		    System.out.println("Waiting for 5 seconds to open the profile page");
		    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
		    }
		  else
			  System.out.println("No Profile option found in Menu Option");
	  }
	  catch(Exception e){
		  System.out.println("Profile option not found ");}
  }
  
  @Test
  public void openLocations()
  {
	  boolean flag = false;
	  System.out.println("Checking if Location option is on screen");
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[6]/text[1]",driver);
	  if(flag == false)	  
		 flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[3]/text[1]",driver);  
	  
	  if (flag == true)
	  {
		  System.out.println("Opening the Locations page");
		  try { 
		    driver.findElement(By.name("Find Centers")).click();
		  }
		  catch(Exception e) {
			  driver.findElement(By.name("Locations")).click();
		  }
		  System.out.println("Waiting for 5 seconds to open the Location page");
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	  }
	  else
		  System.out.println("No Locations option found in Menu Option"); 
  }
  
  @Test
  public void openLabOrders()
  {
	  boolean flag = false;
	  System.out.println("Checking if Lab Orders option is on screen");
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[3]/text[1]",driver);
	  if(flag == false) 
		flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[4]/text[1]",driver);
	  
	  if (flag == true)
	  {
		  System.out.println("Opening the Lab Orders page");
		  driver.findElement(By.name("Lab Orders")).click();
		  System.out.println("Waiting for 5 seconds to open the Lab Orders page");
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	  }
	  else
		  System.out.println("No Lab Orders option found in Menu Option"); 
  }
  
  @Test
  public void openAppointments()
  {
	  boolean flag = false;
	  System.out.println("Checking if Appointment option is on screen");
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[4]/text[1]",driver);
	  if(flag == false)
		flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[5]/text[1]",driver);
	  
	  if (flag == true)
	  {
		  System.out.println("Opening the Appointment page");
		  driver.findElement(By.name("Appointments")).click();
		  System.out.println("Waiting for 5 seconds to open the Appointment page");
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	  }
	  else
		  System.out.println("No Appointment option found in Menu Option"); 
  }
  
  @Test
  public void openResults()
  {
	  boolean flag = false;
	  System.out.println("Checking if Results option is on screen");
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[5]/text[1]",driver);
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[6]/text[1]",driver);  
	  
	  if (flag == true)
	  {
		  System.out.println("Opening the Results page");
		  try{
		    driver.findElement(By.xpath("//window[1]/tableview[1]/cell[5]/text[1]")).click();
		  }
		  catch(Exception e) {
			driver.findElement(By.xpath("//window[1]/view[1]/window[2]/view[1]/list[1]/relative[6]")).click();
		  }
		  System.out.println("Waiting for 5 seconds to open the Result page");
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	   }
	  else
		  System.out.println("No Results option found in Menu Option"); 
  }
  
  @Test
  public void openSettings()
  {
	  boolean flag = false;
	  System.out.println("Checking if Settings option is on screen");
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[7]/text[1]",driver);
	  if(flag == false)
		flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[7]/text[1]",driver);  
	  
	  if (flag == true)
	  {
		  System.out.println("Opening the Settings page");
		  driver.findElement(By.xpath("//*[contains(@text,'settings')]")).click();
		  System.out.println("Waiting for 5 seconds to open the Appointment page");
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	  }
	  else
		  System.out.println("No Settings option found in Menu Option"); 
  }
  
  //This feature in only for Android device
  @Parameters("device")
  @Test
  public void openAbout(String device)
  {
	  if(device.equalsIgnoreCase("android"))
	  {
		  boolean flag = false;
		  System.out.println("Checking if About option is on screen");
		  if(flag == false)
		    flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[8]/text[1]",driver);
		  try 
		  {	  
		    if (flag == true)
		    {
			  System.out.println("Opening the About page");
			  driver.findElement(By.xpath("//*[contains(@text,'ABOUT')]")).click();
			  System.out.println("Waiting for 5 seconds to open the About page");
			  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
		    }
		    else
			  System.out.println("No About option found in Menu Option"); 
		   }
		  catch(Exception e)  {
		    System.out.println("No About option found in Menu Option"); 
		   }
	   }
    }
  
  
  @Test
  public void openHome()
  {
	  boolean flag = false;
	  System.out.println("Checking if Home option is on screen");
	  if(flag == false)
	    flag = operation.verifyMenuButton(flag, "//window[1]/tableview[1]/cell[2]/text[1]",driver);
	  if(flag == false)
		flag = operation.verifyMenuButton(flag, "//window[1]/view[1]/window[2]/view[1]/list[1]/relative[1]",driver); 
	  
	  if (flag == true)
	  {
		  System.out.println("Opening the Home page");
		  try {
		  driver.findElement(By.name("Dashboard")).click();
		  }
		  catch(Exception e) {
			  driver.findElement(By.name("Home")).click();
		  }
		  System.out.println("Waiting for 5 seconds to open the Home page");
		  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
	  }
	  else 
	  {
		  flag = operation.verifyMenuButton(flag, "//window[1]/image[2]",driver);
		  if (flag == true)
		  {
			  System.out.println("Opening the Home page by tapping into the screen");
			  driver.findElement(By.xpath("//window[1]/image[2]")).click();
			  System.out.println("Waiting for 5 seconds to open the home page");
			  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		  }
		  else
			  System.out.println("No Home option found"); 		  
	  }		  
  }
  
  @AfterClass
  public void afterTest()
  {
	  System.out.println("\n----------------------Menu test has completed-----------------------\n\n");
  }

}

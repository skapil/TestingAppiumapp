package com.theranos.test.theranosios.base;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class PerformOperationsTest {
	
    public boolean dimissAllAlert(WebDriver driver) {
    	boolean flag = false;
    	
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
		return flag;
	}
    
    //If Alert appears on device
    public boolean isAlertAppear(WebDriver driver)
    {
    	boolean flag = true;
    	try
    	{
    		System.out.print("pop-up message apears on device :  ");
			System.out.println(driver.findElement(By.name("OK")).isDisplayed());
			if(driver.findElement(By.name("OK")).isDisplayed())
			{
				System.out.println("Error Pop up displayed");
				flag = true;
				System.out.println("Taking the screenshot :   ");
				SetUpTest.takeScreenshot(driver, "alert");
			}			
    	}
    	catch(Exception NoSuchElementException)
    	{
    		System.out.println("No Alert message");
    		flag = false;
    	}
    	return flag;
    }
    
    //Verify if particular button is on current application
    public boolean verifyMenuButton(boolean flag, String element, WebDriver driver)
    {
  	  try
  		{
  		  System.out.println("Finding by xpath of the element");
  		  if ((driver.findElement(By.xpath(element)).isDisplayed()))	  {
  		      System.out.println("Button Found");			      
  		      flag = true;   }  
  		 }
  		catch(Exception NoSuchElementException)	{
  		  try{
  			  System.out.println("Finding by name of the element");
  			  if (driver.findElement(By.name(element)).isDisplayed())	{
  			  System.out.println("Button Found");			      
    		  flag = true;	 }
  		    }
  		  catch(Exception e) {
			try {
				System.out.println("Finding by id of the element");
				if(driver.findElement(By.id(element)).isDisplayed())  {
					System.out.println("Button Found");
					flag = true;   }
			     }
		    catch(Exception NoSuchElementException1)  {
					System.out.println("Not found the button");  
					flag = false;	} 	  			
    		} }  return flag;   
      } 
    
    //Tap on Profile button   
    public void backToProfile(WebDriver driver)
    {
  	  try
  	  {
  		driver.findElement(By.xpath("//window[1]/navigationBar[2]/button[2]")).click();
  	  }
  	  catch(Exception e)
  	  {
  		System.out.println("Not able to find the Profile element, skipping this operation");
  		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
  	  }
    }
    
    //Flick down to the page
    public void flickScreen(WebDriver driver)
	  {
		  JavascriptExecutor js = (JavascriptExecutor) driver;
	      HashMap<String, Double> flickObject = new HashMap<String, Double>();
	      try
        {
	        Dimension dimensions = driver.manage().window().getSize();
	        double screenWidth = dimensions.getWidth()/3;
	        double screenHeight = dimensions.getHeight()/3;
	        flickObject.put("endX", screenWidth);
	        flickObject.put("endY", screenHeight);
	        flickObject.put("touchCount", 2.0);
	        js.executeScript("mobile: flick", flickObject);
		    System.out.println("Waiting for 5 seconds");
		    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 		       
	      }
	      catch(Exception e){
	    	  System.out.println("Not able to scroll down");
	      }
	  }
    
    //Open the page based on the ElementId
    public void openPage(String elementid, String elementinfo, WebDriver driver) 
    {
  	  boolean flag = false;
  	  try
  	  {
  		  System.out.println("Checking if " + elementinfo + " page on screen");
  		  flag = verifyMenuButton(flag, elementid,driver);
  		  if (flag == true)
  		  {
  			System.out.println("Opening " + elementinfo + " page");
  			try {
  		      driver.findElement(By.xpath(elementid)).click();
  			}
  			catch(Exception e) {
  				try{
  					driver.findElement(By.name(elementid)).click();
  				}
  				catch(Exception e1){
  					System.out.println("Not able to found the element");
  				}
  			}
  		    System.out.println("Waiting for 8 seconds to open " + elementinfo + " Page");
  	        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS); 
  		  }
  		  else
  		  {
  		  	 System.out.println("No " + elementinfo + " Button found on the current screen"); 
  		  }
  	   }
  	  catch(Exception e)
  	  {
  		  System.out.println("Not able to find " + elementinfo + " button, skipping this option");
  		  System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
  	  }
    }
   
    
    
   //Send information to pickerWheel
    public void inputValue(String elementid,String elementinfo,String input,WebDriver driver)
    {
    	boolean flag = false;
    	try
    	{
    		System.out.println("Checking if " + elementinfo + "present");
    		flag = verifyMenuButton(flag, elementid,driver);
    		  if (flag == true)
    		  {
    			System.out.println("Opening " + elementinfo + " .");
    			try {
    		      driver.findElement(By.xpath(elementid)).sendKeys(input);
    			}
    			catch(Exception e) {
    				try{
    					driver.findElement(By.name(elementid)).sendKeys(input);
    				}
    				catch(Exception e1){
    					System.out.println("Not able to found the element");
    				}
    			}    		    
    	        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS); 
    		  }
    		  else
    		  {
    		  	 System.out.println("No " + elementinfo + " field on the current screen"); 
    		  }
    	   }
    	  catch(Exception e)
    	  {
    		  System.out.println("Not able to find " + elementinfo + " field, skipping this option");
    		  System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Hiding the Exception message here >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n");
    	  }
    	}   
    
  }


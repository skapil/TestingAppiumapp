package com.theranos.test.theranosios.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SetUpTest {

	   public static WebDriver driver;
	// private String appium_start = "appium -U 11c89c89810feed8cddd52303cb363492bd58268 --app com.theranos.appsuite.medassist -a 127.0.0.1 -p 4723 -l";
	   private Process appium;
	   private static String logfilename = null;
	   final DesiredCapabilities capabilities = new DesiredCapabilities();
	   private static String run_device = null;
	 
	  @Parameters("device")
	  @BeforeSuite (alwaysRun = true)
	  public void getDevice(String device)
	  {
		  run_device = device;
		  System.out.println("This is the value of the run_device inside getDevice() ----> "+run_device);
	  }
	  
	  @BeforeSuite (dependsOnMethods="getDevice")	  
	  public void environmentSetUp() throws Exception{
		//Setting up the environment for connected device
		System.out.println("This is the value of the run_device ----> "+run_device);
		if (run_device.equalsIgnoreCase("ios"))
		{
		    //Appium Setup for ios
		    System.out.println("<<<-------------------Setting up environment for ios device---------------->>>");
		    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		    capabilities.setCapability(CapabilityType.VERSION, "7.0");
		    capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
		    capabilities.setCapability("device", "iPhone");
		   capabilities.setCapability("app", "com.theranos.me");
		    driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		    System.out.println("<<<--------------------Setup has completed for iOS device-------------------------------->>>");
		  }
		else if(run_device.equalsIgnoreCase("android"))
		{
			//Appium setup for Android
		    System.out.println("<<<----------------------Setting up Envronment for Android device---------------------->>>");		     
		    capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
		    //capabilities.setCapability("device", "selendroid");
		    capabilities.setCapability(CapabilityType.VERSION, "4.2");
		    capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
		    capabilities.setCapability("device", "Android");
		    //capabilities.setCapability("app", "com.theranos.me");
		    capabilities.setCapability("app-package", "com.theranos.me");
		    capabilities.setCapability("app-activity", "landing.LandingPageActivity");
		    driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		    System.out.println("<<<--------------------Setup has completed for Android device-------------------------------->>>");
		}
		else
			System.out.println("Device name not mention in Configuration file textng.xml");		  
	  }	  


	  @AfterSuite
	  public void tearDown() throws Exception {
		  System.out.println("Test suite has completed, quiting the driver");		  
	      driver.quit();	      
	  }	  
	
	  @Parameters("log-folder")
	  @Test
	  public static void getLogLocation(String loglocation)
	  {
		  logfilename = loglocation;
	  }
	  
	  //Taking the Screenshot
	  public static void takeScreenshot(WebDriver driver, String imgname) throws Exception 
	  {
	     System.out.println("Taking Screenshot");
	     String dateStamp = new SimpleDateFormat("HH-mm-ss-MMddyy").format(Calendar.getInstance().getTime());
	     File logfile = new File("target/test-output/logs/" + dateStamp + "/" + logfilename + "/screenshots/");
	     if(!logfile.exists())
	     {
	    	 System.out.println("Making new directory to save screenshot");
	    	 System.out.println("Screenshot will be save here :   "+"target/test-output/logs/"+dateStamp+"/"+logfilename+"/screenshots/");
	    	 logfile.mkdirs();
	     }
	     else
	    	 System.out.println("Screenshot directory already exist, no new directory to make");
	     System.out.println("Getting screenshot folder from config file");
	     
	     String timeStamp = new SimpleDateFormat("HH-mm-ss-MMddyy").format(Calendar.getInstance().getTime());
	     String screenshotname = imgname+"_"+timeStamp;
	     String filename = "target/test-output/logs/" + dateStamp + "/" + logfilename + "/screenshots/" + screenshotname + ".jpg";

	     try {
	     	WebDriver augmentedDriver = new Augmenter().augment(driver);
	     	File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
	     	FileUtils.copyFile(scrFile, new File(filename), true);
	     	} catch (Exception e) {
	     		e.printStackTrace();
	 	        System.out.println("Error capturing screen shot of " + screenshotname + " test failure.");
	     	}
	     	System.out.println("Took Screenshot as :  "+screenshotname);
	   }
	}



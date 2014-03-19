package com.theranos.test.theranosios.base;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SetUpTest {

	 public static WebDriver driver;
	// private String appium_start = "appium -U 11c89c89810feed8cddd52303cb363492bd58268 --app com.theranos.appsuite.medassist -a 127.0.0.1 -p 4723 -l";
	// private Process appium;
	 
	  @BeforeSuite (alwaysRun = true)
	  public void environmentSetUp() throws Exception{
		    //Appium Setup for ios
		    System.out.println("Setting up the test to run");
		    final DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		    capabilities.setCapability(CapabilityType.VERSION, "7.0");
		    capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
		    capabilities.setCapability("device", "iPhone");
		    capabilities.setCapability("app", "theranos");
		    driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		    System.out.println("Test Setup completed ");
		    //return driver;
	  } 
	  
/*	  @BeforeSuite
	  public void setUp() throws Exception {
	      //closeSimulatorAndInstruments(); // also closes any appium servers
	      appium = Runtime.getRuntime().exec(appium_start);
	      Thread.sleep(1000); // wait for appium to start up, not sure how to check the status
	      
	  }

	  @AfterSuite
	  public void tearDown() throws Exception {
	      //captureScreenshot(testName.getMethodName());
	      driver.quit();
	      appium.destroy(); // kills the appium server so it wont collide with the next run
	  }
	  */
	}



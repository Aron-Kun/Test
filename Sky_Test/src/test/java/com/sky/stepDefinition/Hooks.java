package com.sky.stepDefinition;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.sky.stepDefinition.Methods;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class Hooks {
	public static WebDriver driver;
	public static boolean wantsToQuit = false;

	@Before
	public void beforeScenario(Scenario  s) 
	{		
		System.out.println("Start test.");
		
		try {
			chromeDesktop();	
			//chromeMobile();
			System.out.println("Webdriver has been initialized as testmode: " + Variables.testMode );
			Thread.sleep(5000);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (wantsToQuit) {
		      throw new RuntimeException("Cucumber has quit.");
		  }		
		
		driver.get(Variables.baseUrl);

	}	
	
	@After
	public void afterScenario(Scenario s)
	{
		System.out.println("End of tests.");
		wantsToQuit = false;
		//wantsToQuit = true == s.isFailed();
		if(wantsToQuit) {
			System.out.println("An important test scenario has failed so Cucumber quits");
			Methods.captureScreenshot("GeneralFailure");
			driver.quit();			
		}
		if(Variables.testMode == "mobile") {
			driver.quit();
		}
		else {
		//driver.close();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
	
	public void chromeDesktop()
	{
		Variables.testMode = "desktop";
		System.setProperty("webdriver.chrome.driver", "./_ChromeDriver/chromedriver.exe");	
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		Methods.switchLatestTab();
	}	
	
	public void chromeMobile()
	{
		Variables.testMode = "mobile";
		System.setProperty("webdriver.chrome.driver", "./_ChromeDriver/chromedriver.exe");
		Map<String, String> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", "iPhone X");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("w3c", true);
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);		
		driver = new ChromeDriver(chromeOptions);
		
		Methods.switchLatestTab();
	}	
	
}
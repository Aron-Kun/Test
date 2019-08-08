package com.sky.stepDefinition;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import cucumber.api.java.en.Given;

public class Methods {
	
	static JavascriptExecutor js = ((JavascriptExecutor) Hooks.driver);
	

	public static String testTimeStamp() {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		String testTimeStamp = dateTime.format(formatter);
		return testTimeStamp;
		}
	

	public static void captureScreenshot(String stepName) {

		try {
			TakesScreenshot capture = (TakesScreenshot) Hooks.driver;
			String fileName = stepName + "_" + testTimeStamp();
			File source = capture.getScreenshotAs(OutputType.FILE);
			Files.copy(source, new File("./Screenshots/" + fileName + ".png"));
			System.out.println("Screenshot taken as " + fileName);
		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}
	}

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) Hooks.driver).executeScript("return document.readyState")
						.equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(Hooks.driver, 30);
		wait.until(pageLoadCondition);
	}
	
	public static void srollToElement(WebElement element) throws Throwable {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	static WebDriverWait wait = new WebDriverWait(Hooks.driver, 5);


	public static void urlOpeninNewTab(String url) {
		// Hooks.driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		js.executeScript("window.open('" + url + "','_blank');");
		ArrayList<String> windowHandles = new ArrayList<String>(Hooks.driver.getWindowHandles());
		int i = (int) (windowHandles.stream().count() - 1);
		Hooks.driver.switchTo().window(windowHandles.get(i));
	}

	// open an existing link on the pagee in new tab
	public static void linkOpeninNewTab(String urlLink) {
		String selectedLinkOpeninNewTab = Keys.chord(Keys.CONTROL, "t");
		Hooks.driver.findElement(By.linkText("urlLink")).sendKeys(selectedLinkOpeninNewTab);
	}

	// switch to the provided tab
	public static void switchToTabByNumber(int pageNumber) {
		// numbering starts from 0 (windowHandles the list of opened tabs
		ArrayList<String> windowHandles = new ArrayList<String>(Hooks.driver.getWindowHandles());
		Hooks.driver.switchTo().window(windowHandles.get(pageNumber));
	}

	public static void switchLatestTab() {
		// numbering starts from 0 (windowHandles the list of opened tabs
		ArrayList<String> windowHandles = new ArrayList<String>(Hooks.driver.getWindowHandles());
		Hooks.driver.switchTo().window(windowHandles.get(windowHandles.size() - 1));
	}

	/// Find Elements
	

	public static List<WebElement> findChildElements(By parentLocator, By childLocator) {
		WebElement parentElement = Hooks.driver.findElement(parentLocator);
		List<WebElement> elements = parentElement.findElements(childLocator);
		return elements;
	}

	public static WebElement findElementbyTagnameAndText(String tagname, String text) {
		WebElement element = Hooks.driver
				.findElement(By.xpath("//" + tagname + "[text()[contains(.,'" + text + "')]]"));
		return element;
	}
}

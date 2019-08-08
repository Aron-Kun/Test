package com.sky.stepDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sky.stepDefinition.Hooks;
import com.sky.stepDefinition.Methods;
import com.sky.stepDefinition.Variables;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SkyNews_test_steps {

	// Set driver by test mode
	public static WebDriver driver = Hooks.driver;

	///////// Variables /////////
	static WebDriverWait wait = new WebDriverWait(Hooks.driver, 10);
	int loadingTime = Variables.loadingTime;

	// Background Steps
	/// Locators///
	By logoLocator = By.className("sdc-site-header__logo");

	public void waitForPageToLoad() throws Throwable {
		wait.until(ExpectedConditions.presenceOfElementLocated(logoLocator));
	}

	@Given("^Accept Privacy Policy$")
	public void accept_Privacy_Policy() throws Throwable {
		WebElement acceptButon = Methods.findElementbyTagnameAndText("button", "Accept");
		acceptButon.click();
	}

	@When("^User visit the page: \"([^\"]*)\"$")
	public void user_visit_the_page(String page) throws Throwable {
		String url = Variables.baseUrl + page;
		Methods.urlOpeninNewTab(url);
	}

	/// Title steps///

	@When("^User clicks on \"([^\"]*)\"$")
	public void user_clicks_on(String article) throws Throwable {
		waitForPageToLoad();
		if (article.length() > 2) {
			WebElement articleLink = driver.findElement(By.linkText(article));
			Methods.srollToElement(articleLink);
			Thread.sleep(1000);
			articleLink.click();
			Thread.sleep(500);
		}
	}

	@Then("^Verify \"([^\"]*)\" as tabs title displayed correctly$")
	public void verify_as_tabs_title_displayed_correctly(String expectedTitle) throws Throwable {
		waitForPageToLoad();
		try {
			String displayedTitle = driver.getTitle();
			if (displayedTitle.contains(expectedTitle)) {
				System.out.println("Title displayed correctly");
			} else
				System.out.println(
						"!!! Title does NOT match - expected: " + expectedTitle + " - displayed: " + displayedTitle);
		} catch (Exception e) {
			String stepName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Methods.captureScreenshot(stepName);
			System.out.println("!!! Title NOT found");
		}
	}

	@Then("^Verify \"([^\"]*)\" article's title displayed as \"([^\"]*)\" correctly$")
	public void verify_article_s_title_displayed_as_correctly(String article, String title) throws Throwable {
		if (article.length() > 2) {
			waitForPageToLoad();
			try {
				String expectedTitle = title;
				String displayedTitle = driver.findElement(By.tagName("h1")).getText();
				if (displayedTitle.contains(expectedTitle)) {
					System.out.println("H1 displayed correctly");
				} else
					System.out.println(
							"!!! H1 does NOT match - expected: " + expectedTitle + " - displayed: " + displayedTitle);
			} catch (Exception e) {
				String stepName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				Methods.captureScreenshot(stepName);
				System.out.println("!!! H1 NOT found");
			}
		}
	}

	/// Categories steps ///
	// Locators
	By navigationLocator = By.className("sdc-site-header__nav");
	By categoriesLocator = By.className("sdc-site-header__menu-item");
	By moreBtnLocator = By.className("sdc-site-header__menu-item-link-icon");
	By moreListLocator = By.id("sdc-site-header-overflow-nav");

	@When("^User navigate to \"([^\"]*)\" page$")
	public void user_navigate_to_page(String category) throws Throwable {
		wait.until(ExpectedConditions.elementToBeClickable(navigationLocator));
		if (category.length() > 2) {
			WebElement categoryNav = driver.findElement(By.linkText(category));
			categoryNav.click();
		}
		Thread.sleep(500);
	}

	@Given("^User clicks on more category button$")
	public void user_click_on_more_button() throws Throwable {
		// show more categories
		try {
			wait.until(ExpectedConditions.elementToBeClickable(moreBtnLocator));
			Thread.sleep(1000);
			WebElement moreBtn = driver.findElement(moreBtnLocator);
			moreBtn.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(moreListLocator));
		} catch (Exception e) {
			String stepName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Methods.captureScreenshot(stepName);
		}
	}

	@Then("^Verify \"([^\"]*)\" as categories are displayed correctly$")
	public void verify_as_categories_are_displayed_correctly(String categories) throws Throwable {
		List<String> expectedCategories = Arrays.asList(categories.split("\\s*,\\s*"));
		int expCategoriesNum = expectedCategories.size();
		List<String> displayedCategories = new ArrayList<String>();
		int dispCategoriesNum = 0;

		// Iterate categories in navigation
		List<WebElement> navCategories = Methods.findChildElements(navigationLocator, categoriesLocator);
		for (int i = 0; i <= navCategories.size(); i++) {
			try {
				if (navCategories.get(i).isDisplayed()) {
					dispCategoriesNum++;
					String element = navCategories.get(i).getText();
					displayedCategories.add(element);
				}
			} catch (Exception e) {
				break;
			}
		}
		if (displayedCategories.equals(expectedCategories))
			System.out.println("Categories matching with the expected");
		else {
			System.out.println("!!!Categories NOT matching");
			System.out.println("Number of expected categories:" + expCategoriesNum
					+ " - Number of displayed categories: " + dispCategoriesNum);
			// For debug purpose
			for (int i = 0; i <= expectedCategories.size(); i++) {
				try {
					System.out.println(i + ". |" + displayedCategories.get(i) + "|");
					System.out.println(i + ". |" + expectedCategories.get(i) + "|");

				} catch (Exception e) {

					String stepName = new Object() {
					}.getClass().getEnclosingMethod().getName();
					Methods.captureScreenshot(stepName);
					break;
				}
			}
		}
	}

	public String highlighted_category() {
		List<WebElement> navCategoryLinks = Methods.findChildElements(navigationLocator, By.tagName("a"));
		List<String> highlightedCategory = new ArrayList<String>();
		String[] links = new String[navCategoryLinks.size()];
		int i = 0;

		for (WebElement e : navCategoryLinks) {
			links[i] = e.getText();
			if ( e.isDisplayed() && e.getAttribute("aria-current") != null) {
				if ((e.getAttribute("aria-current").contains("true"))) {
					String element = e.getText();
					// System.out.println("Highlighted category: " + element);
					highlightedCategory.add(element);
				}
			}
			i++;
		}
		if (highlightedCategory.size() == 1) {
			return highlightedCategory.get(0).toString();
		} else if (highlightedCategory.size() > 1) {
			System.out.println("!!! More than one category in focus");
			return null;
		} else {
			System.out.println("!!! NO category in focus");
			return null;
		}
	}

	@Then("^Verify \"([^\"]*)\" as default category are highlighted correctly$")
	public void verify_as_default_category_are_highlighted_correctly(String category) throws Throwable {
		verify_as_category_are_highlighted_correctly(category);
	}

	@Then("^Verify \"([^\"]*)\" as category are highlighted correctly$")
	public void verify_as_category_are_highlighted_correctly(String category) throws Throwable {
		try { 
		if (category.contains(highlighted_category())) {

			System.out.println("Selected category: " + category + " is in focus");
		} else {
			System.out.println("!!! Selected category: " + category + " is NOT in focus");
			String stepName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			Methods.captureScreenshot(stepName);
		}
		
		 } catch (Exception e) {		  
		 String stepName = new Object() { }.getClass().getEnclosingMethod().getName();
		 Methods.captureScreenshot(stepName);		 
		 }
		 
	}

}

package com.setty.automation.reUsableComponents;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.setty.automation.driver.Driver;

public class reusableComponentsLib extends Driver {
//reusable components such as reporting methods, generic web action methods will be implemented here
	/**
	 * @methodName: compareString
	 * @description: method for comparing two string and returning boolean value
	 * @param value1
	 * @param value2
	 * @return :boolean value true or false based on the comparison result
	 * @author Mohammed sujadh khan
	 */
	public boolean compareString(String value1, String value2) {
		boolean comparisonResult = false;
		
		if (value1.equalsIgnoreCase(value2)) {
			comparisonResult = true;
		}
		else {
			comparisonResult = false;
		}
		return comparisonResult;
	}
	/**
	 * name :comparePartialString
	 * description : method for comparing partial string
	 * @param value1
	 * @param value2
	 * @author Mohammed sujadh khan
	 * @return :boolean value
	 */
	
	public boolean comparePartialString(String value1, String value2) {
		boolean comparisonResult = false;
		
		if (value1.equalsIgnoreCase(value2)) {
			comparisonResult = true;
		}
		else {
			comparisonResult = false;
		}
		return comparisonResult;
		
	}
	/**
	 * @method name :clickELement
	 * @description :method for clicking on a element on a web page
	 * @param element
	 * @author Mohammed sujadh khan
	 */
	public void clickElement(WebElement element) {
		
		try {
			if(element.isDisplayed()) {
				element.click();
			}
			else {
				System.out.println("Element is not displayed " +element);
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * @method name :clickELement
	 * @description :method for sending text to a webelement on a page 
	 * @param none
	 * @author Mohammed sujadh khan
	 */
	public void sendTextToELement(WebElement element, String textToSend) {
		try {
			if(element.isDisplayed()) {
				element.sendKeys(textToSend);
			}
			else {
				System.out.println("Element is not displayed");
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * method name : selectWebListByVisibleText
	 * description: method for selecting the value in weblist by visible text
	 * @param element
	 * @param valueToSelect
	 * @author Mohammed sujadh khan
	 * @return boolean value
	 */
	public boolean selectWebListByVisibleText(WebElement element, String valueToSelect) {
		boolean selectedVal = false;
		try {
			if(element.isDisplayed()) {
				Select sel = new Select(element);
				sel.selectByVisibleText(valueToSelect);
				String firstSelectedOpt = sel.getFirstSelectedOption().getText();
				if (firstSelectedOpt.equalsIgnoreCase(valueToSelect)) {
					selectedVal= true;
					
				}
				else {
					selectedVal = false;
				}
			}
			else {
				selectedVal=false;
				System.out.println("Element is not displayed");
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return selectedVal;
	}
	
	/**
	 * method name: takesScreenShot
	 * description: Method for taking screenshot with reporter statement
	 * @param: file Name
	 * @return: screenshot path
	 * @author Mohammed sujadh khan
	 */
	public String takesScreenShot(String fileName) {
		
		String presentTime = new SimpleDateFormat("HH_mm_ss").format(new Date());
		String screenShotPath = strReportDirPath+File.separator+fileName + "_" + presentTime + ".png";
		
		try {
			File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenShotFile, new File(screenShotPath));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return screenShotPath;
	}
	/**
	 * method name : waitForPageToLoad
	 * description: method for wating for page to load completely after performing an operation on a web page
	 * @param: NA
	 * @author Mohammed sujadh khan
	 * @return boolean value
	 */
public boolean waitForPageToLoad() {
	boolean isPageLoadComplete = false;
	try {
		new WebDriverWait(driver,10).until(new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver driverObject) {
        	return (boolean) ((String) ((JavascriptExecutor) driverObject)
        			.executeScript("return document.readyState")).equals("complete");
        }
		});
		isPageLoadComplete = (Boolean) ((String) ((JavascriptExecutor) driver)
				.executeScript("return document.readyState")).equals("complete");
	}
	catch (Exception e) {
		System.err.println("Unable to load page");
	}
	return isPageLoadComplete;
}
/**
 * method name : isElementAvailable
 * description: method for cheking for availability of the element with fluent wait
 * @param eleXpath
 * @author Mohammed sujadh khan
 * @return : fetched element
 */
public WebElement isElementAvailable(String eleXpath, int TimeOutInSecs) {
	WebElement element = null;
	try {
	//Wait<WebDriver> wait = new FluentWait<WebDri,ver>(driver);
		FluentWait<WebDriver> waiting = new FluentWait<WebDriver>(driver)							
			.withTimeout(TimeOutInSecs,TimeUnit.SECONDS)
			.pollingEvery(20,TimeUnit.MILLISECONDS)
			.ignoring(NoSuchElementException.class);
		waiting.until(ExpectedConditions.presenceOfElementLocated(By.xpath((eleXpath))));
	element = driver.findElement(By.xpath(eleXpath));
	
	}
	catch (Exception e){
		System.out.println(e.getMessage());
	}
	
	 return element;
}
/**
 * method name : areElementsAvailable
 * description: method for cheking for availability of the element with fluent wait
 * @param eleXpath
 * @author Mohammed sujadh khan
 * @return : fetched elements in a list
 */
public List<WebElement> aresElementsAvailable(String eleXpath, int TimeOutInSecs) {
	List<WebElement> elements = null;
	try {
	//Wait<WebDriver> wait = new FluentWait<WebDri,ver>(driver);
		FluentWait<WebDriver> waiting = new FluentWait<WebDriver>(driver)							
			.withTimeout(TimeOutInSecs,TimeUnit.SECONDS)
			.pollingEvery(20,TimeUnit.MILLISECONDS)
			.ignoring(NoSuchElementException.class);
		waiting.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath((eleXpath))));
	elements = driver.findElements(By.xpath(eleXpath));
	
	}
	catch (Exception e){
		System.out.println(e.getMessage());
	}
	
	 return elements;
}
	
public void javaScriptClick(WebElement element) {
	try {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}
	catch (NoSuchElementException e) {
		e.printStackTrace();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
}
}

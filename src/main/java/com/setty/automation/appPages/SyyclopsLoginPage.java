package com.setty.automation.appPages;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.setty.automation.driver.Driver;
import com.setty.automation.reUsableComponents.reusableComponentsLib;


public class SyyclopsLoginPage extends Driver {

	//sample code, complete implementation will be available in milestone 2 delivery
	WebElement element = null;
	
	//Application page properties
	private static final String syyclopsMainPageLoginLnk_Xpath = "//a[text()='Log In']";
	private static final String syyclopsMainPageUserNameTxtBx_Xpath = "//input[@name='username']";
	private static final String syyclopsMainPagePasswordTxtBx_Xpath = "//input[@name='password']";
	private static final String syyclopsMainPageLoginBtn_Xpath = "//button[@type='submit' and text()='Login']";
	private static final String syyclopsLogoutLink_Xpath = "//a[text()='Log Out']";
	private static final String logoutFrmDetailsPg_Xpath = "//a[text()='Log Out']";
	private static final String rightPaneMenuIcon_Xpath = "//span[@class='dark-amber-text']/I";
	//creating an object of reusable class for accessing reusable methods such as fluent wait or other methods
	reusableComponentsLib reuseComp = new reusableComponentsLib();
	
	//Methods for returning webelements
	/**
	 * @methodName:syyclopsMainPgUserNameTxtBxEle
	 * @description:method for identifying and returning username webelement on login page
	 * @param:N/A
	 * @return:WebElement
	 */
	public List<WebElement> logoutLink(){
		List<WebElement> elements = reuseComp.aresElementsAvailable(syyclopsLogoutLink_Xpath, 20);
		return elements;
	}
	public WebElement detailsPgRightPaneMenuIcon() {
		WebElement fetchedElement = reuseComp.isElementAvailable(rightPaneMenuIcon_Xpath, 10);
		return fetchedElement;
	}
	public WebElement logoutfromdetailsPgEle() {
		WebElement fetchedElement = reuseComp.isElementAvailable(logoutFrmDetailsPg_Xpath, 10);
		return fetchedElement;
	}
	public WebElement syyclopsUsername_WbEdt() {
		WebElement fetchedElement = reuseComp.isElementAvailable(syyclopsMainPageUserNameTxtBx_Xpath, 10);
		return fetchedElement;
	}
	public WebElement syyclopsPassword_WbEdt() {
		WebElement fetchedElement = reuseComp.isElementAvailable(syyclopsMainPagePasswordTxtBx_Xpath, 10);
		return fetchedElement;
	}
	public WebElement syyclopsLogin_Btn() {
		WebElement fetchedElement = reuseComp.isElementAvailable(syyclopsMainPageLoginBtn_Xpath, 10);
		return fetchedElement;
	}
	public List<WebElement> loginLink(){
		List<WebElement> elements = reuseComp.aresElementsAvailable(syyclopsMainPageLoginLnk_Xpath, 20);
		return elements;
	}
	
}

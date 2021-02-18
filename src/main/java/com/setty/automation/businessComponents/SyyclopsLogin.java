package com.setty.automation.businessComponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import com.relevantcodes.extentreports.LogStatus;
import com.setty.automation.appPages.SyyclopsLoginPage;
import com.setty.automation.driver.Driver;
import com.setty.automation.reUsableComponents.genericActionsLib;
import com.setty.automation.reUsableComponents.reusableComponentsLib;

public class SyyclopsLogin extends Driver {
//objects for page classes
	
	//syyclops login method - sample code, complete implementation will be available in milestone 2 delivery
	SyyclopsLoginPage loginPage = new SyyclopsLoginPage();
	genericActionsLib genActionsLib = new genericActionsLib();
	reusableComponentsLib reuseComp = new reusableComponentsLib();
	/**
	 * @method name : syyclopsLogin
	 * @Description: Method for logging into syyclops application
	 * @param : N/A
	 * @author Mohammed sujadh khan
	 * @return: N/A
	 */
	public void syyclopsLogin() {
		try {
			String URL = prop.getProperty("ApplicationURL");
			driver.get(URL);
			reuseComp.waitForPageToLoad();
			logger.log(LogStatus.PASS, "URL launched successfully");
			if (loginPage.loginLink().size()>0) {
				reuseComp.waitForPageToLoad();
				logger.log(LogStatus.PASS, "Login link is displayed successfully", logger.addScreenCapture(reuseComp.takesScreenShot("syyclopsLoginScreen")));
				loginPage.loginLink().get(0).click();
				
				if (loginPage.syyclopsUsername_WbEdt().isEnabled()) {
					reuseComp.waitForPageToLoad();
					Thread.sleep(3000);
					reuseComp.clickElement(loginPage.syyclopsUsername_WbEdt());
					reuseComp.sendTextToELement(loginPage.syyclopsUsername_WbEdt(), "test@test.com");
					reuseComp.waitForPageToLoad();
					reuseComp.sendTextToELement(loginPage.syyclopsPassword_WbEdt(), "Testing@512");
					
					loginPage.syyclopsLogin_Btn().click();
				}
				else {
					logger.log(LogStatus.FAIL, "Username Element not interactable ");
				}
				
			}
			else {
				logger.log(LogStatus.FAIL, "Login link is not displayed");
			}
		}
		catch (NoSuchElementException e){
			logger.log(LogStatus.FAIL, "Error observed in syyclopslogin method" + e.getMessage());
			System.out.println(e.getMessage());
		}
		catch (TimeoutException e) {
			//report.log(Log.FAIL, "Username field is not visible")
			logger.log(LogStatus.FAIL, "Error observed in syyclopslogin method" + e.getMessage());
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			//report.log(Log.FAIL, ")e.getMessage());
			logger.log(LogStatus.FAIL, "Error observed in syyclopslogin method" + e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public void verifyHomeScreen() {
		try {
		reuseComp.waitForPageToLoad();
		if (loginPage.logoutLink().size()>0) {
			logger.log(LogStatus.PASS, "Logout link is displayed", logger.addScreenCapture(reuseComp.takesScreenShot("syyclopsHomePage")));
			
		}
		else {
			logger.log(LogStatus.FAIL, "Logout link is not displayed");
		}
		}
		catch (NoSuchElementException e){
			logger.log(LogStatus.FAIL, "Error observed in verifyHomePage method" + e.getMessage());
			System.out.println(e.getMessage());
		}
		catch (TimeoutException e) {
			//report.log(Log.FAIL, "Username field is not visible")
			logger.log(LogStatus.FAIL, "Error observed in verifyHomePage method" + e.getMessage());
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			//report.log(Log.FAIL, ")e.getMessage());
			logger.log(LogStatus.FAIL, "Error observed in verifyHomePage method" + e.getMessage());
			System.out.println(e.getMessage());
		}
		
	}
	
	public void logout() throws Exception {
		try {
		reuseComp.waitForPageToLoad();
		if (loginPage.logoutLink().size()>0) {
			logger.log(LogStatus.PASS, "Logout link is displayed", logger.addScreenCapture(reuseComp.takesScreenShot("syyclopsHomePage")));
			loginPage.logoutLink().get(0);
			logger.log(LogStatus.PASS, "Logout link is clicked successfully ", logger.addScreenCapture(reuseComp.takesScreenShot("LogoutScreenshot")));
			
		}
		else {
			logger.log(LogStatus.FAIL, "Logout link is not displayed");
		}
		}
		catch (NoSuchElementException e) {
			logger.log(LogStatus.FAIL, "Observed error in logout method " + e.getMessage());
			throw new Exception("Observed error in logout method " + e.getMessage());
		}
		catch (Exception e) {
			logger.log(LogStatus.FAIL, "Observed error in logout method " + e.getMessage());
			throw new Exception("Observed error in logout method " + e.getMessage());
		}
	}
	
	public void logoutfromDetailsPg() throws Exception {
		try {
			reuseComp.waitForPageToLoad();
			if (loginPage.detailsPgRightPaneMenuIcon().isDisplayed()) {
				logger.log(LogStatus.PASS, "Right Pane Menu is displayed", logger.addScreenCapture(reuseComp.takesScreenShot("syyclopsdetailspage")));
				reuseComp.javaScriptClick(loginPage.detailsPgRightPaneMenuIcon());
				reuseComp.waitForPageToLoad();
				reuseComp.javaScriptClick(loginPage.logoutfromdetailsPgEle());
				logger.log(LogStatus.PASS, "Logout link is clicked successfully ",logger.addScreenCapture(reuseComp.takesScreenShot("LogoutScreen")));
				
			}
			else {
				logger.log(LogStatus.FAIL, "Logout link is not displayed");
			}
		}
		catch (NoSuchElementException e) {
			logger.log(LogStatus.FAIL, "Observed error in logout method " + e.getMessage());
			throw new Exception("Observed error in logout method " + e.getMessage());
		}
		catch (Exception e) {
			logger.log(LogStatus.FAIL, "Observed error in logout method " + e.getMessage());
			throw new Exception("Observed error in logout method " + e.getMessage());
		}
	}
	
}

package com.setty.automation.businessComponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import com.relevantcodes.extentreports.LogStatus;
import com.setty.automation.appPages.HavTechDetailsPage;
import com.setty.automation.driver.Driver;
import com.setty.automation.reUsableComponents.genericActionsLib;
import com.setty.automation.reUsableComponents.reusableComponentsLib;

public class HavTechDetailsVal extends Driver {

	HavTechDetailsPage havTechDetailsPg = new HavTechDetailsPage();
	reusableComponentsLib reuseComp = new reusableComponentsLib();
	genericActionsLib genActions = new genericActionsLib();
	
	public void navigateAndValHavTechDetailsPage() throws Exception {
		
		String planningorSoftCost = genActions.getData("Planning_SoftCost");
		String buildingControls = genActions.getData("Building_Controls");
		String accessControl = genActions.getData("Access_Control");
		String groundsKeeping = genActions.getData("GroundsKeeping");
		String electricity = genActions.getData("Electricity");
		String gas = genActions.getData("Gas");
		
		try {
			if (havTechDetailsPg.havTechBuildingIcon().size()>0) {
				logger.log(LogStatus.PASS, "HaveTechbuilding icon is displayed");
				int elementToClick = havTechDetailsPg.havTechBuildingIcon().size();
				//System.out.println(elementToClick);
				reuseComp.javaScriptClick(havTechDetailsPg.havTechBuildingIcon().get(elementToClick-1));
				reuseComp.waitForPageToLoad();
				if (havTechDetailsPg.havTechDetailsPgValEle("Planning/Soft Cost").isDisplayed()) {
					logger.log(LogStatus.PASS, "Navigated to haveTech building details page");
					//validating planning and soft cost
					String planningOrSoftCost_Fetched = havTechDetailsPg.havTechDetailsPgValEle("Planning/Soft Cost").getText();
					if (planningOrSoftCost_Fetched.equalsIgnoreCase(planningorSoftCost)) {
						logger.log(LogStatus.PASS, "Planning or soft cost is as expected "+planningOrSoftCost_Fetched);
					}
					else {
						logger.log(LogStatus.FAIL, "Planning or soft cost is not as expected "+planningOrSoftCost_Fetched);
					}
					//validating building controls
					String buildingControls_fetched = havTechDetailsPg.havTechDetailsPgValEle("Building Controls").getText();
					if (buildingControls_fetched.equalsIgnoreCase(buildingControls)) {
						logger.log(LogStatus.PASS, "Building controls value is as expected "+buildingControls_fetched);
					}
					else {
						logger.log(LogStatus.FAIL, "Building Controls is not as expected" +buildingControls_fetched);
					}
					//validating access control
					String accessControls_fetched = havTechDetailsPg.havTechDetailsPgValEle("Access Control").getText();
					if (accessControls_fetched.equalsIgnoreCase(accessControl)) {
						logger.log(LogStatus.PASS, "Access controls value is as expected "+accessControls_fetched);
					}
					else {
						logger.log(LogStatus.FAIL, "Access controls is not as expected" +accessControls_fetched);
					}
					//validating grounds keeping
					String groundKeeping_fetched = havTechDetailsPg.havTechDetailsPgValEle("Groundskeeping").getText();
					if (groundKeeping_fetched.equalsIgnoreCase(groundsKeeping)) {
						logger.log(LogStatus.PASS, "Grounds Keeping value is as expected "+groundKeeping_fetched);
					}
					else {
						logger.log(LogStatus.FAIL, "Grounds Keeping not as expected" +groundKeeping_fetched);
					}
					//Electricity
					String Electricity_fetched = havTechDetailsPg.havTechDetailsPgValElePreceding("Electricity").getText();
					if (Electricity_fetched.equalsIgnoreCase(electricity)) {
						logger.log(LogStatus.PASS, "Electricity value is as expected "+groundKeeping_fetched);
					}
					else {
						logger.log(LogStatus.FAIL, "Electricity not as expected" +groundKeeping_fetched);
					}
					//Gas
					String Gas_fetched = havTechDetailsPg.havTechDetailsPgValElePreceding("Gas").getText();
					if (Gas_fetched.equalsIgnoreCase(gas)) {
						logger.log(LogStatus.PASS, "Gas value is as expected "+groundKeeping_fetched);
					}
					else {
						logger.log(LogStatus.FAIL, "Gas not as expected" +groundKeeping_fetched);
					}
				}
				else {
					logger.log(LogStatus.FAIL, "Could not navigate to haveTech building details page");
				}
				
			}
			else {
				logger.log(LogStatus.FAIL, "HaveTechbuilding icon is not displayed");
			}
		}
		catch (NoSuchElementException e) {
			logger.log(LogStatus.FAIL, "Element not found" + e.getMessage());
			throw new Exception("Element could not be found while navigating to HavTech details page" +e.getMessage());
		}
		catch (TimeoutException e) {
			logger.log(LogStatus.FAIL, "Timeout Exception while navigating to HavTech details page" + e.getMessage());
			throw new Exception("Element could not be found while navigating to HavTech details page" +e.getMessage());
		}
		catch (Exception e) {
			logger.log(LogStatus.FAIL, "Generic exception" + e.getMessage());
			throw new Exception("Generic exception while navigating to HavTech details page" +e.getMessage());
		}
	}
}

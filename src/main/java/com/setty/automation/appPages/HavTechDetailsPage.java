package com.setty.automation.appPages;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.setty.automation.driver.Driver;
import com.setty.automation.reUsableComponents.reusableComponentsLib;

public class HavTechDetailsPage extends Driver {

	WebElement element = null;
	List<WebElement> elements = null;
	//creating an object of reusable class for accessing reusable methods such as fluent wait or other methods
	reusableComponentsLib reuseComp = new reusableComponentsLib();
	
	//storing object properties in private static variables so that the maintenance becomes easy at later point as all the page related properties will reside at one place
	private static final String havTechBuildingLink_xpath = "//p[text()=' Havtech ']/preceding::img[@src=' assets/img/building.png']";
	private static final String havtechBuildingDetails_xpath_1 = "//div[text()='";
	private static final String havtechBuildingDetails_xpath_2 = "']/following::span[1]";
	private static final String havtechDetailsPg_Xpath_1 = "//div[text()='";
	private static final String havTechdetailsPg_Xpath_2 = "']/preceding::span[1]";
	
	
	public List<WebElement> havTechBuildingIcon() {
		elements = reuseComp.aresElementsAvailable(havTechBuildingLink_xpath, 10);
		return elements;
	}
	
	public WebElement havTechDetailsPgValEle(String innerText) {
		element = reuseComp.isElementAvailable(havtechBuildingDetails_xpath_1+innerText+havtechBuildingDetails_xpath_2, 20);
		return element;
	}
	
	public WebElement havTechDetailsPgValElePreceding(String innerText) {
		element = reuseComp.isElementAvailable(havtechDetailsPg_Xpath_1+innerText+havTechdetailsPg_Xpath_2, 20);
		return element;
	}
}

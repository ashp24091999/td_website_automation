package com.fdmgroup.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationFormPage {
	
	WebDriver driver;
	public RegistrationFormPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath = "//h1[contains(text(),'Digital Banking Registration')]")
	private WebElement registrationHeader;


	public boolean isLoaded() { return registrationHeader.isDisplayed(); }
	public String getPageTitle() {
		return registrationHeader.getText();
	}
	
	
}



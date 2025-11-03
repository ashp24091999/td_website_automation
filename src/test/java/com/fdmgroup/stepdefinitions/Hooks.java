package com.fdmgroup.stepdefinitions;

import org.openqa.selenium.WebDriver;

import com.fdmgroup.pages.ChequingPage;
import com.fdmgroup.pages.ContactUsPage;
import com.fdmgroup.pages.EasyWebLoginPage;
import com.fdmgroup.pages.HomePage;
import com.fdmgroup.pages.RegistrationFormPage;
import com.fdmgroup.pages.RegistrationIntroPage;
import com.fdmgroup.utilities.DriverUtilities_old;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	static DriverUtilities_old driverUtilities;
	static WebDriver driver;
	static HomePage homePage;
	static EasyWebLoginPage loginPage;
	static RegistrationFormPage registrationFormPage;
	static RegistrationIntroPage introPage;
	static ChequingPage chequingPage;
	static ContactUsPage contactUsPage;
	
	@Before
	public void init() {
		
		driverUtilities = DriverUtilities_old.getInstance();
		driver = driverUtilities.getDriver();
		
		//Page initialization
		homePage = new HomePage(driver);
		driver.manage().window().maximize();
	}
	
	@After
	public void tearDown() {
		//driver.quit();
		DriverUtilities_old.resetDriver();
	}
		
}

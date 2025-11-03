package com.fdmgroup.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationIntroPage {
	
	WebDriver driver;
	public RegistrationIntroPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="(//span[contains(text(),'Register')])[3]")
	private WebElement registerBtn;
	
	// Accept cookies button
	@FindBy(id = "onetrust-accept-btn-handler")
	private WebElement acceptCookiesButton;
	
	public void clickRegisterAgain() { 
		registerBtn.click(); 
	}

	// Method to click if present
		public void acceptCookiesIfPresent() {
		    try {
		        if (acceptCookiesButton.isDisplayed()) {
		            ((JavascriptExecutor) driver)
		                .executeScript("arguments[0].scrollIntoView(true);", acceptCookiesButton);
		            acceptCookiesButton.click();
		            System.out.println("Accepted cookie banner.");
		        }
		    } catch (Exception e) {
		        System.out.println("No cookie banner found or already dismissed.");
		    }
		}
		
		public void scrollToAndClickRegister() {
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    // scroll smoothly to the element
		    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", registerBtn);
		    // click the element
		    registerBtn.click();
		}
		
		public String getRegisterButtonText() {
			return registerBtn.getText().trim();
		}
}

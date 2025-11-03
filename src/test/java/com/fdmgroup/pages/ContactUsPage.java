package com.fdmgroup.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ContactUsPage {
    WebDriver driver;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Dropdown element
    @FindBy(xpath ="//select[@name='selectregion']")
    private WebElement contactUsDropdown;

    // Method to select an option
    public void selectFromDropdown(String visibleText) {
        Select dropdown = new Select(contactUsDropdown);
        dropdown.selectByVisibleText(visibleText);
        System.out.println("Selected option: " + visibleText);
    }

	public String getSelectedOption() {
		Select dropdown = new Select(contactUsDropdown);
	    return dropdown.getFirstSelectedOption().getText().trim();
	}	
}

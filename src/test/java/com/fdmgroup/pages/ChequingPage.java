package com.fdmgroup.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChequingPage {
	
	WebDriver driver;
	public ChequingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[contains(@class,'aem-GridColumn aem-GridColumn--phone--12 aem-GridColumn--offset--phone--0 aem-GridColumn--tabletportrait--6  aem-GridColumn--tabletlandscape--4 aem-GridColumn--default--4 border-right cmp-container-gutter-none compareProduct')]")
	private List<WebElement> ChequingAccountList;

	public List<String> getBankingPlanLinkText(String accountName) {
	    List<String> accounts = new ArrayList<>();
	    for (WebElement account : ChequingAccountList) {
	        String name = account.getAttribute("data-cardname");
	        if (name != null) {
	            // Clean up any tags, entities, and whitespace
	            name = name.replaceAll("<[^>]+>", " ")
	                       .replace("&nbsp;", " ")
	                       .replace("&amp;", "&")
	                       .replaceAll("\\s+", " ")
	                       .trim();
	            accounts.add(name);
	        }
	    }
	    return accounts;
	}

}

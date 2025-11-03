package com.fdmgroup.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    
    // Driver
   
    WebDriver driver;
    
    // TD Logo
    @FindBy(xpath = "//header//img[contains(@alt,'TD') or contains(@src,'logo')]")
    private WebElement tdLogo;

    // Top menu links (Banking, Investing, etc.)
    @FindBy(xpath = "(//ul[contains(@class,'cmp-header-site-navigation-links__menu')])[1]/li/a")
    private List<WebElement> topMenuLinks;

   
    // Language Selector
    
    // Language dropdown button (the one showing "English" label)
    @FindBy(css = "li.cmp-site-utility-nav__language > a[role='button']")
    private WebElement languageSelector;

    @FindBy(xpath = "//div[@aria-label='Language' or @aria-label='Langue']"
                  + "//a[contains(@class,'cmp-site-utility-nav__sub-menu-link')]")
    private List<WebElement> languageOptions;

    // Dropdown container when opened
    @FindBy(css = "div[aria-label='Language']")
    private WebElement languageDropdown;

    // Français option inside that dropdown
    @FindBy(xpath = "//div[@aria-label='Language']//a[normalize-space()='Français']")
    private WebElement francaisOption;

    
    // Trigger button in header (Canada ▼ / United States ▼)
    @FindBy(css = "li.cmp-site-utility-nav__country > a[role='button']")
    private WebElement countrySelectorBtn;

    // US link (stable id in TD header)
    @FindBy(id = "country2")
    private WebElement unitedStatesLink;

    // Canada link (stable id in TD header)
    @FindBy(id = "country1")
    private WebElement canadaLink;

    
    @FindBy(css = "li.cmp-site-utility-nav__login > a[role='button']")
    private WebElement loginDropdown;

    @FindBy(xpath = "//a[normalize-space()='EasyWeb']")
    private WebElement easyWebLink;

    
    @FindBy(xpath = "(//a[contains(text(),'Chequing')])[1]")
    private WebElement chequingSubMenuItem;

    
    // Contact Us element at the bottom
    @FindBy(xpath = "//a[contains(text(),'Call us')]")
    private WebElement contactUsLink;

    // Accept cookies button
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

   
    // Constructor
   
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

   
    // check if TD logo is displayed
    public boolean isTdLogoDisplayed() {
        return tdLogo.isDisplayed();
    }

    // Check if all top menu links are displayed
    public boolean areTopMenuLinksDisplayed() {
        for (WebElement link : topMenuLinks) {
            if (!link.isDisplayed()) {
                System.out.println("Top menu link not displayed: " + link.getText());
                return false;
            }
        }
        return true;
    }

    // prints all menu names
    public List<WebElement> getTopMenuLinks() {
        for (WebElement link : topMenuLinks) {
            System.out.println(link.getText());
        }
        return topMenuLinks;
    }

    public void clickMenuByText(String menuName) {
        for (WebElement link : topMenuLinks) {
            String text = link.getText().trim();
            if (text.equalsIgnoreCase(menuName)) {
                System.out.println("Clicking menu: " + text);
                link.click();
                return;
            }
        }
        System.out.println("Menu not found: " + menuName);
    }

    
    // Prints all visible languages
    public void printAllLanguages() {
        System.out.println("---- Available Languages ----");
        for (WebElement lang : languageOptions) {
            System.out.println(lang.getText().trim());
        }
        System.out.println("------------------------------");
    }

    public void openLanguageDropdown() {
        languageSelector.click();
    }

    public void selectFrancaisOption() {
        francaisOption.click();
    }

    public boolean isFrenchPageLoaded() {
        return driver.getCurrentUrl().contains("/ca/fr/services-bancaires-personnels");
    }

    // Click a language by its visible text
    public void clickLanguage(String languageText) {
        for (WebElement opt : languageOptions) {
            if (opt.getText().trim().equals(languageText)) {
                // Skip already-active “English” that has href="#"
                String href = opt.getAttribute("href");
                if (href == null || "#".equals(href.trim())) return;
                opt.click();
                return;
            }
        }
        System.out.println("Language not found in dropdown: " + languageText);
    }

    public String getCurrentLanguage() {
        return francaisOption.getText().trim();
    }

    
    public void openCountryDropdown() {
        countrySelectorBtn.click();
    }

    
    public void clickCountry(String country) {
        if (country == null) {
            System.out.println("Country is null");
            return;
        }
        String c = country.trim().toLowerCase();
        if (c.contains("united") || c.contains("us")) {
            unitedStatesLink.click();
        } else if (c.contains("canada") || c.contains("ca")) {
            canadaLink.click();
        } else {
            System.out.println("Unknown country: " + country);
        }
    }

    
    public void openLoginDropdown() {
        loginDropdown.click();
    }

    public void clickEasyWeb() {
        easyWebLink.click();
    }

    public String getEasyWebButtontext() {
        return easyWebLink.getText();
    }

    
    public void clickSubMenuItem(String submenuItem) {
        chequingSubMenuItem.click();
    }

   
    public void scrollToAndClickContactUs() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // scroll smoothly to the element
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", contactUsLink);
        // click the element
        contactUsLink.click();
    }

    public String getContactUsLinkText() {
        return contactUsLink.getText().trim();
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
}

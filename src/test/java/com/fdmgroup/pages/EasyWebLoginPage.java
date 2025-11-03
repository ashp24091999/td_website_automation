package com.fdmgroup.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EasyWebLoginPage {

    // ---------------------------
    // Driver
    // ---------------------------
    private WebDriver driver;

    // ---------------------------
    // Web Elements
    // ---------------------------

    // Username field
    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;

    // Password field
    @FindBy(xpath = "//input[@id='uapPassword']")
    private WebElement passwordInput;

    // Login button
    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement loginButton;

    // Register button (link)
    @FindBy(xpath = "(//a[contains(text(),'Register online now')])[1]")
    private WebElement registerBtn;

    // Accept cookies button
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    // Inline error
    @FindBy(css = "#errorMsg #error")
    private WebElement inlineError;

    // ---------------------------
    // Constructor
    // ---------------------------
    public EasyWebLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------------------------
    // Actions
    // ---------------------------

    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void clickRegister() {
        registerBtn.click();
    }

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

    // ---------------------------
    // Getters / Validations
    // ---------------------------

    public String getInlineErrorText() {
        return inlineError.getText().trim();
    }
}

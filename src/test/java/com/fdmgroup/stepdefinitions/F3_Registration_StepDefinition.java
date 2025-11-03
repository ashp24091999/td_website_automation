package com.fdmgroup.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fdmgroup.pages.HomePage;
import com.fdmgroup.pages.EasyWebLoginPage;
import com.fdmgroup.pages.RegistrationIntroPage;
import com.fdmgroup.pages.RegistrationFormPage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class F3_Registration_StepDefinition {


    @When("The user opens the Login dropdown in the header")
    public void the_user_opens_the_login_dropdown_in_the_header() throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.openLoginDropdown();
        Thread.sleep(2000);
    }

    @When("The user clicks {string} under Login")
    public void the_user_clicks_under_login(String item) throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        String home = homePage.getEasyWebButtontext();
        if (home.equals(item)) {
            homePage.clickEasyWeb();
        }
        Thread.sleep(2000);
    }

    @When("The user accepts the cookie consent when it is displayed")
    public void the_user_accepts_the_cookie_consent_when_it_is_displayed() throws InterruptedException {
        EasyWebLoginPage easyLoginPage = new EasyWebLoginPage(Hooks.driver);
        easyLoginPage.acceptCookiesIfPresent();
        Thread.sleep(2000);
    }

   

    @When("The user types username {string} on EasyWeb")
    public void the_user_types_username_on_easy_web(String username) throws InterruptedException {
        EasyWebLoginPage loginPage = new EasyWebLoginPage(Hooks.driver);
        loginPage.enterUsername(username);
        Thread.sleep(1000);
    }

    @When("The user types password {string} on EasyWeb")
    public void the_user_types_password_on_easy_web(String password) throws InterruptedException {
        EasyWebLoginPage loginPage = new EasyWebLoginPage(Hooks.driver);
        loginPage.enterPassword(password);
        Thread.sleep(1000);
    }

    @When("The user clicks the EasyWeb {string} button")
    public void the_user_clicks_the_easy_web_button(String btn) throws InterruptedException {
        EasyWebLoginPage loginPage = new EasyWebLoginPage(Hooks.driver);
        if ("Login".equalsIgnoreCase(btn)) {
            loginPage.clickLoginButton();
        }
        Thread.sleep(2000);
    }

    @Then("The EasyWeb inline error should contain {string}")
    public void the_easy_web_inline_error_should_contain(String expectedPart) throws InterruptedException {
        EasyWebLoginPage page = new EasyWebLoginPage(Hooks.driver);
        String actual = page.getInlineErrorText();
        System.out.println("Inline error: " + actual);
        assertTrue("Expected inline error to contain: " + expectedPart + " but was: " + actual,
                   actual.contains(expectedPart));
        Thread.sleep(500);
    }

    

    @When("The user clicks the {string} button on the EasyWeb page")
    public void the_user_clicks_the_button_on_the_auth_page(String buttonTxt) throws InterruptedException {
        EasyWebLoginPage loginPage = new EasyWebLoginPage(Hooks.driver);
        if (buttonTxt.equals("Register Online Now")) {
            loginPage.clickRegister();
        }
        Thread.sleep(2000);
    }

    @When("The user accepts the cookie consent when displayed")
    public void the_user_accepts_the_cookie_consent_when_displayed() throws InterruptedException {
        new RegistrationIntroPage(Hooks.driver).acceptCookiesIfPresent();
        Thread.sleep(3000);
    }

    @When("The user scrolls to and clicks {string} button")
    public void the_user_scrolls_to_and_clicks_at_the_bottom(String linkText) throws InterruptedException {
        RegistrationIntroPage registerPage = new RegistrationIntroPage(Hooks.driver);
        String scrolllink = registerPage.getRegisterButtonText();
        if (scrolllink.equals(linkText)) {
            registerPage.scrollToAndClickRegister();
        }
        Thread.sleep(2000);
    }

    @Then("The registration header should display {string}")
    public void the_registration_header_should_display(String expectedHeader) throws InterruptedException {
        RegistrationFormPage formPage = new RegistrationFormPage(Hooks.driver);
        String actualHeader = formPage.getPageTitle();
        System.out.println("Header text found: " + actualHeader);
        assertEquals("Header text does not match!", expectedHeader, actualHeader);
        Thread.sleep(2000);
    }

   
}

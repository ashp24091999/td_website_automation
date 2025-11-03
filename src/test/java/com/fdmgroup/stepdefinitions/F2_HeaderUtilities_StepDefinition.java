package com.fdmgroup.stepdefinitions;

import static org.junit.Assert.assertTrue;

import com.fdmgroup.pages.ContactUsPage;
import com.fdmgroup.pages.HomePage;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class F2_HeaderUtilities_StepDefinition {

    

    @When("The User opens the Language selector in the header")
    public void the_user_opens_the_language_selector_in_the_header() throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.openLanguageDropdown();
        Thread.sleep(2000);
    }

    @When("The User clicks {string} in the Language options")
    public void the_user_clicks_in_the_language_options(String language) throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        String currentLanguage = homePage.getCurrentLanguage();
        if (currentLanguage.equals(language)) {
            homePage.selectFrancaisOption();
        }
        Thread.sleep(3000);
    }

    @When("The User prints all available languages")
    public void the_user_prints_all_available_languages() {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.openLanguageDropdown();
        homePage.printAllLanguages();
    }

    @Then("The page URL should contain {string}")
    public void the_page_url_should_contain(String expectedUrlPart) {
        String actualUrl = Hooks.driver.getCurrentUrl();
        assertTrue("Expected URL part not found. Actual: " + actualUrl,
                   actualUrl.contains(expectedUrlPart));
    }

   

    @When("The User opens the Country selector in the header")
    public void the_user_opens_the_country_selector_in_the_header() throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.openCountryDropdown();
        Thread.sleep(2000);
    }

    @When("The User clicks {string} in the Country options")
    public void the_user_clicks_in_the_country_options(String string) throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.clickCountry(string);
        Thread.sleep(3000);
    }

    

    @When("The User accepts the cookie consent if displayed")
    public void the_user_accepts_the_cookie_consent_if_displayed() throws InterruptedException {
        new HomePage(Hooks.driver).acceptCookiesIfPresent();
        Thread.sleep(3000);
    }

    
    @When("The User scrolls to and clicks {string} at the bottom")
    public void the_user_scrolls_to_and_clicks_at_the_bottom(String linkText) throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        String scrolllink = homePage.getContactUsLinkText();
        if (scrolllink.equals(linkText)) {
            homePage.scrollToAndClickContactUs();
        }
        Thread.sleep(2000);
    }

    @Then("The User selects {string} from the Contact Us dropdown")
    public void the_user_selects_from_the_contact_us_dropdown(String optionText) throws InterruptedException {
        ContactUsPage contactPage = new ContactUsPage(Hooks.driver);
        contactPage.selectFromDropdown(optionText);
        Thread.sleep(2000);
    }

    @Then("The selected option should be displayed as {string}")
    public void the_selected_option_should_be_displayed_as(String expectedOption) throws InterruptedException {
        ContactUsPage contactPage = new ContactUsPage(Hooks.driver);
        String actualSelected = contactPage.getSelectedOption();
        System.out.println("Selected option is: " + actualSelected);
        assertTrue("Expected '" + expectedOption + "' but found '" + actualSelected + "'",
                   actualSelected.equalsIgnoreCase(expectedOption));
        Thread.sleep(2000);
    }

 
}

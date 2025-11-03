package com.fdmgroup.stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.fdmgroup.data.DataFile;
import com.fdmgroup.pages.ChequingPage;
import com.fdmgroup.pages.HomePage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class F1_NavigationTest_StepDefinition {


    @Given("User launches the TD Bank website")
    public void user_launches_the_td_bank_website() throws InterruptedException {
        // Navigation
        Hooks.driver.navigate().to(DataFile.loginURL);
        Thread.sleep(3000);
    }

    @Given("TD logo should be visible in the header")
    public void td_logo_should_be_visible_in_the_header() throws InterruptedException {
        // Create the HomePage object using the current driver
        HomePage homePage = new HomePage(Hooks.driver);
        // Verify the TD logo is visible on the header
        assertTrue("TD logo is not visible in the header.", homePage.isTdLogoDisplayed());
        Thread.sleep(3000);
    }

    @Given("User should see all the top menu links displayed")
    public void user_should_see_all_the_top_menu_links_displayed() throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        assertTrue("One or more top menu links are not visible!", homePage.areTopMenuLinksDisplayed());
        Thread.sleep(3000);
    }

   

    @When("User clicks on {string} menu from top navigation")
    public void user_clicks_on_menu_from_top_navigation(String menuName) throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.clickMenuByText(menuName);
        Thread.sleep(3000);
        
    }

    @When("User clicks {string} from the Accounts submenu")
    public void user_clicks_from_the_accounts_submenu(String submenuItem) throws InterruptedException {
        HomePage homePage = new HomePage(Hooks.driver);
        homePage.clickSubMenuItem(submenuItem);
        Thread.sleep(3000);
        
    }

    
    
    
    @Then("The Chequing products should include:")
    public void the_chequing_products_should_include(DataTable dataTable) {
        // Expected list from feature file
        List<String> expectedProducts = dataTable.asList(String.class);

        // Actual list from page
        ChequingPage chequingPage = new ChequingPage(Hooks.driver);
        List<String> actualProducts = chequingPage.getBankingPlanLinkText("");

        System.out.println("Expected Products: " + expectedProducts);
        System.out.println("Actual Products:   " + actualProducts);

        // Assert that the lists match exactly
        boolean listsMatch = expectedProducts.equals(actualProducts);
        assertTrue("Chequing products mismatch!\nExpected: " + expectedProducts + "\nActual: " + actualProducts,listsMatch);

        System.out.println("All expected Chequing products are displayed correctly.");
    }
}

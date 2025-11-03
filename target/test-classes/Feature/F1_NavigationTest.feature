# Author : Aiswarya
# Date   : 23 Oct, 2025

@Navigation @RegressionTest
Feature: As a user I want to verify navigation links in the TD website 

  Background:
    Given User launches the TD Bank website
    And TD logo should be visible in the header
    And User should see all the top menu links displayed

  @LaunchSite
  Scenario: User launches the TD Bank website
    When User clicks on "Accounts" menu from top navigation
    


  @MultipleNavigation
  Scenario Outline: Launch site and click a top navigation menu item
    When User clicks on "<menu>" menu from top navigation

    Examples:
      | menu               |
      | Credit Cards       |
      | Mortgages          |
      | Borrowing          |
      | Personal Investing |
      | Insurance          |
      | Advice             |


  @ChequingProducts
  Scenario: Navigate to Chequing Accounts and verify product list
    When User clicks on "Accounts" menu from top navigation
    And User clicks "Chequing" from the Accounts submenu
    Then The page URL should contain "chequing-accounts"
    And The Chequing products should include:
      | TD All-Inclusive Banking Plan |
      | TD Unlimited Chequing Account |
      | TD Every Day Chequing Account |
      | TD Minimum Chequing Account   |
      | TD Student Chequing Account   |

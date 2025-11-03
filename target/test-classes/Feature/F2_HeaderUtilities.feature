# Author : Aiswarya
# Date   : 23 Oct, 2025

@HeaderUtils @RegressionTest
Feature: As a user I want to verify that the language, country, and contact options in the TD website header work correctly

  Background:
    Given User launches the TD Bank website

  @LanguageSwitcher
  Scenario: Switch site language to Français from the header
    When The User opens the Language selector in the header
    And The User clicks "Français" in the Language options
    Then The page URL should contain "/ca/fr/services-bancaires-personnels"


  @CountrySwitcher
  Scenario: Switch site country to United States from the header
    When The User opens the Country selector in the header
    And The User clicks "United States" in the Country options
    Then The page URL should contain "/us/en/personal-banking"


  @ContactUs
  Scenario: From TD homepage, scroll down and use Contact Us dropdown
    When The User accepts the cookie consent if displayed
    And The User scrolls to and clicks "Call us" at the bottom
    And The User selects "Credit Cards" from the Contact Us dropdown
    Then The selected option should be displayed as "Credit Cards"

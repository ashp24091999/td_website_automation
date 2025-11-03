# Author : Aiswarya
# Date   : 23 Oct, 2025

@Registration @RegressionTest
Feature: As a user I want to verify the EasyWeb login and registration process on the TD website

  Background:
    Given User launches the TD Bank website
    When The user opens the Login dropdown in the header
    And The user clicks "EasyWeb" under Login
    Then The page URL should contain "authentication.td.com/uap-ui"

  @EasyWebLoginError
  Scenario: EasyWeb shows an error for invalid credentials
    When The user accepts the cookie consent when it is displayed
    When The user types username "fakeuser123" on EasyWeb
    And The user types password "badPass!123" on EasyWeb
    And The user clicks the EasyWeb "Login" button
    Then The EasyWeb inline error should contain "doesn't match our records"


  @AccessRegistrationPage
  Scenario: Navigate from Login → EasyWeb → Registration form
    When The user clicks the "Register Online Now" button on the EasyWeb page
    When The user accepts the cookie consent when displayed
    And The user scrolls to and clicks "Register" button
    Then The registration header should display "Digital Banking Registration: Personal Information"

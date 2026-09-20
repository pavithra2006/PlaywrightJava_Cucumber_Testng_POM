Feature: Banking customer login

  @smoke @ui
  Scenario: Customer can log in to the banking application
    Given the banking login page is open
    When I log in as customer "Harry Potter"
    Then the customer dashboard is displayed

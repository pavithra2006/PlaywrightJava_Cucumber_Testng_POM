@ui @smoke
Feature: Bank manager customer management

  Scenario: Manager login with valid access
    Given I open the banking application
    When I log in as bank manager
    Then the manager dashboard is displayed

  @regression
  Scenario Outline: Manager adds a customer
    Given I open the banking application
    When I log in as bank manager
    And I add a customer with first name "<firstName>", last name "<lastName>", and post code "<postCode>"
    Then the customer "<firstName>" "<lastName>" is listed

    Examples:
      | firstName | lastName | postCode |
      | Ada       | Lovelace | 10001    |
      | Grace     | Hopper   | 10002    |

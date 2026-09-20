package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.driver.PlaywrightManager;
import org.example.pages.CustomerPage;
import org.example.pages.LoginPage;
import org.testng.Assert;

public class BankingSteps {
    private LoginPage loginPage;
    private CustomerPage customerPage;

    @Given("the banking login page is open")
    public void openLoginPage() {
        loginPage = new LoginPage(PlaywrightManager.page()).open();
        Assert.assertTrue(loginPage.isDisplayed(), "Banking login page is not displayed");
    }

    @When("I log in as customer {string}")
    public void loginAsCustomer(String customer) {
        customerPage = loginPage.loginAsCustomer(customer);
    }

    @Then("the customer dashboard is displayed")
    public void verifyDashboard() {
        Assert.assertTrue(customerPage.isLoggedIn(), "Customer dashboard is not displayed");
    }
}

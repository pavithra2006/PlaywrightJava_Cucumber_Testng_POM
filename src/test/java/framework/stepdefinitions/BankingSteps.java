package framework.stepdefinitions;

import framework.factory.PlaywrightFactory;
import framework.pages.AddCustomerPage;
import framework.pages.CustomersPage;
import framework.pages.LoginPage;
import framework.pages.ManagerHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class BankingSteps {
    private LoginPage loginPage;
    private ManagerHomePage managerHome;
    private AddCustomerPage addCustomer;
    private CustomersPage customers;

    @Given("I open the banking application")
    public void openApplication() {
        loginPage = new LoginPage(PlaywrightFactory.page()).open();
        Assert.assertTrue(loginPage.isDisplayed(), "Banking home login page is not visible");
    }

    @When("I log in as bank manager")
    public void managerLogin() {
        managerHome = loginPage.loginAsManager();
    }

    @Then("the manager dashboard is displayed")
    public void managerDashboard() {
        Assert.assertTrue(managerHome.isDisplayed(), "Manager dashboard controls are unavailable");
    }

    @When("I add a customer with first name {string}, last name {string}, and post code {string}")
    public void addCustomer(String first, String last, String postCode) {
        addCustomer = managerHome.addCustomer();
        String message = addCustomer.enterCustomer(first, last, postCode).submit();
        Assert.assertTrue(message.toLowerCase().contains("customer added"),
                "Unexpected add-customer confirmation: " + message);
        customers = managerHome.customers();
    }

    @Then("the customer {string} {string} is listed")
    public void customerIsListed(String first, String last) {
        Assert.assertTrue(customers.search(first).containsCustomer(first, last),
                "Customer was not listed: " + first + " " + last);
    }
}

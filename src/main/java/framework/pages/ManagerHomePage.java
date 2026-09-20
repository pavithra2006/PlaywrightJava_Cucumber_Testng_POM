package framework.pages;

import com.microsoft.playwright.Page;

public class ManagerHomePage {
    private final Page page;

    public ManagerHomePage(Page page) { this.page = page; }

    public boolean isDisplayed() {
        com.microsoft.playwright.Locator addCustomerButton = page.locator("button[ng-click='addCust()']");
        addCustomerButton.waitFor();
        return addCustomerButton.isVisible();
    }

    public AddCustomerPage addCustomer() {
        page.locator("button[ng-click='addCust()']").click();
        return new AddCustomerPage(page);
    }

    public OpenAccountPage openAccount() {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Open Account")).click();
        return new OpenAccountPage(page);
    }

    public CustomersPage customers() {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Customers")).click();
        return new CustomersPage(page);
    }
}

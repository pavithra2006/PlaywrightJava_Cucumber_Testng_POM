package framework.pages;

import com.microsoft.playwright.Page;
import framework.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public LoginPage open() {
        LOG.info("Opening banking login page");
        page.navigate(ConfigReader.get("base.url"));
        return this;
    }

    public ManagerHomePage loginAsManager() {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Bank Manager Login")).click();
        LOG.info("Logged in as bank manager");
        return new ManagerHomePage(page);
    }

    public CustomerPage selectCustomer(String customerName) {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Customer Login")).click();
        page.locator("#userSelect").selectOption(
                new com.microsoft.playwright.options.SelectOption().setLabel(customerName));
        return new CustomerPage(page);
    }

    public boolean isDisplayed() {
        com.microsoft.playwright.Locator customerLogin = page.getByRole(
                com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Customer Login"));
        customerLogin.waitFor();
        return customerLogin.isVisible();
    }
}

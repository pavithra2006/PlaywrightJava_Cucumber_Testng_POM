package framework.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class CustomerPage {
    private final Page page;

    public CustomerPage(Page page) { this.page = page; }

    public CustomerAccountPage login() {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login")).click();
        return new CustomerAccountPage(page);
    }

    public CustomerPage selectCustomer(String name) {
        page.locator("#userSelect").selectOption(new SelectOption().setLabel(name));
        return this;
    }
}

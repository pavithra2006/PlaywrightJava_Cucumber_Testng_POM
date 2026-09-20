package org.example.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import org.example.config.Config;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public LoginPage open() {
        page.navigate(Config.baseUrl());
        return this;
    }

    public boolean isDisplayed() {
        return page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Customer Login")).isVisible();
    }

    public CustomerPage loginAsCustomer(String customerName) {
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Customer Login")).click();
        page.locator("select[ng-model='custId']").selectOption(
                new SelectOption().setLabel(customerName));
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login")).click();
        return new CustomerPage(page);
    }
}
